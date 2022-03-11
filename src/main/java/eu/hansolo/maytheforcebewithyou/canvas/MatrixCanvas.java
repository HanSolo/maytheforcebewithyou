package eu.hansolo.maytheforcebewithyou.canvas;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;


public class MatrixCanvas extends Region {
    private static final double                    PREFERRED_WIDTH    = 400;
    private static final double                    PREFERRED_HEIGHT   = 300;
    private static final Color                     BACKGROUND_COLOR   = Color.rgb(30, 28, 26);
    private static final Color                     BOX_COLOR          = Color.color(0.01, 0.6, 0.93);
    private static final Color                     SELECTED_BOX_COLOR = Color.color(0.94, 0.11, 0.13);
    private              double                    size               = PREFERRED_HEIGHT;
    private              double                    width              = PREFERRED_WIDTH;
    private              double                    height             = PREFERRED_HEIGHT;
    private              double                    boxWidth           = width / 8;
    private              double                    boxHeight          = height / 6;
    private              double                    boxRadius          = size * 0.05;
    private              double                    boxOffset          = size * 0.01;
    private              double                    boxCenterX         = boxWidth * 0.5;
    private              double                    boxCenterY         = boxHeight * 0.5;
    private              double                    doubleBoxOffset    = 2 * boxOffset;
    private              Map<LocalDate, Box>       boxes              = new HashMap<>();
    private              LocalDate                 selectedDate       = null;
    private              Canvas                    canvas;
    private              GraphicsContext           ctx;

    private              record Box(double x, double y, double width, double height) {
        public boolean contains(final double x, final double y) {
            return (Double.compare(x, this.x) >= 0 &&
                    Double.compare(y, this.y) >= 0 &&
                    Double.compare(x, (this.x + this.width)) <= 0 &&
                    Double.compare(y, (this.y + this.height)) <= 0);
        }
    }


    // ******************** Constructors **************************************
    public MatrixCanvas() {
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        canvas = new Canvas(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        ctx    = canvas.getGraphicsContext2D();

        getChildren().setAll(canvas);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            Optional<Entry<LocalDate, Box>> optEntry = boxes.entrySet().stream().filter(entry -> entry.getValue().contains(e.getX(), e.getY())).findFirst();
            selectedDate = optEntry.isPresent() ? optEntry.get().getKey() : null;
            redraw();
        });
    }


    // ******************** Layout *******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

        if (width > 0 && height > 0) {
            canvas.setWidth(width);
            canvas.setHeight(height);
            canvas.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            boxWidth        = width / 8;
            boxHeight       = height / 6;
            boxCenterX      = boxWidth * 0.5;
            boxCenterY      = boxHeight * 0.5;
            boxRadius       = size * 0.05;
            boxOffset       = size * 0.01;
            doubleBoxOffset = 2 * boxOffset;

            redraw();
        }
    }

    private void redraw() {
        boxes.clear();

        LocalDate currentDate      = LocalDate.now();
        LocalDate startDate        = currentDate.minusDays(30);
        int       startWeek        = startDate.get(WeekFields.ISO.weekOfYear());
        Color     foregroundColor  = Color.WHITE;

        ctx.clearRect(0, 0, width, height);
        ctx.setFill(BACKGROUND_COLOR);
        ctx.fillRect(0, 0, width, height);
        ctx.setStroke(foregroundColor);
        ctx.setFill(foregroundColor);
        ctx.setFont(Font.font(size * 0.065));
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.setTextBaseline(VPos.CENTER);
        for (int y = 0 ; y < 7 ; y++) {
            for (int x = 0 ; x < 9 ; x++) {
                double posX = x * boxWidth;
                double posY = y * boxHeight;
                if (y == 0 && x > 0 && x < 8) {
                    ctx.fillText(DayOfWeek.values()[x - 1].getDisplayName(TextStyle.NARROW_STANDALONE, Locale.getDefault()), posX + boxCenterX, posY + boxCenterY);
                }
                if (x == 0 && y > 0) {
                    ctx.fillText(Integer.toString(startWeek + y), posX + boxCenterX, posY + boxCenterY);
                }
            }
        }

        int indexX = currentDate.getDayOfWeek().getValue() - 1;
        int indexY = 4;
        for (int i = 0 ; i < 30 ; i++) {
            LocalDate date = currentDate.minusDays(i);
            double  posX = boxWidth + indexX * boxWidth;
            double  posY = boxHeight + indexY * boxHeight;

            boolean boxSelected  = null != selectedDate && date.isEqual(selectedDate);

            boxes.put(date, new Box(posX + boxOffset, posY + boxOffset, boxWidth - doubleBoxOffset, boxHeight - doubleBoxOffset));

            ctx.setFill(boxSelected ? SELECTED_BOX_COLOR : BOX_COLOR);
            ctx.fillRoundRect(posX + boxOffset, posY + boxOffset, boxWidth - doubleBoxOffset, boxHeight - doubleBoxOffset, boxRadius, boxRadius);
            ctx.setFill(foregroundColor);
            ctx.fillText(Integer.toString(date.getDayOfMonth()), posX + boxCenterX, posY + boxCenterY);

            indexX = indexX - 1;
            if (indexX == -1) {
                indexX = 6;
                indexY--;
            }
        }
    }
}