package eu.hansolo.maytheforcebewithyou.canvas;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;


public class MatrixNodes extends Region {
    private static final double                   PREFERRED_WIDTH    = 400;
    private static final double                   PREFERRED_HEIGHT   = 300;
    private static final Color                    BACKGROUND_COLOR   = Color.rgb(30, 28, 26);
    private static final Color                    BOX_COLOR          = Color.color(0.01, 0.6, 0.93);
    private static final Color                    SELECTED_BOX_COLOR = Color.color(0.94, 0.11, 0.13);
    private              double                   size               = PREFERRED_HEIGHT;
    private              double                   width              = PREFERRED_WIDTH;
    private              double                   height             = PREFERRED_HEIGHT;
    private              double                   boxWidth           = width / 8;
    private              double                   boxHeight          = height / 6;
    private              double                   boxRadius          = size * 0.05;
    private              double                   boxOffset          = size * 0.01;
    private              Label                    selectedLabel      = null;
    private              EventHandler<MouseEvent> mouseHandler;
    private              GridPane                 grid;


    // ******************** Constructors **************************************
    public MatrixNodes() {
        mouseHandler = e -> {
            if (e.getSource() instanceof Label) {
                selectedLabel = (Label) e.getSource();
                for (int y = 1 ; y < 7 ; y++) {
                    for (int x = 1 ; x < 9 ; x++) {
                        Optional<Node> optLabel = Helper.getCellAt(grid, x, y);
                        if (optLabel.isPresent()) {
                            Label label = (Label) optLabel.get();
                            boolean isSelected = label.equals(selectedLabel);
                            label.setBackground(new Background(new BackgroundFill(isSelected ? SELECTED_BOX_COLOR : BOX_COLOR, new CornerRadii(boxRadius), new Insets(boxOffset))));
                        }
                    }
                }
            }
        };
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);

        grid = new GridPane();
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, new Insets(0))));
        for (int x = 0 ; x < 9 ; x++) { grid.getColumnConstraints().add(new ColumnConstraints(boxWidth, boxWidth, boxWidth)); }
        for (int y = 0 ; y < 7 ; y++) { grid.getRowConstraints().add(new RowConstraints(boxHeight, boxHeight, boxHeight)); }

        LocalDate currentDate     = LocalDate.now();
        LocalDate startDate       = currentDate.minusDays(30);
        int       startWeek       = startDate.get(WeekFields.ISO.weekOfYear());

        for (int y = 0 ; y < 7 ; y++) {
            for (int x = 0 ; x < 9 ; x++) {
                if (y == 0 && x > 0 && x < 8) {
                    Label label = createLabel(DayOfWeek.values()[x - 1].getDisplayName(TextStyle.NARROW_STANDALONE, Locale.getDefault()), new BackgroundFill(Color.TRANSPARENT, new CornerRadii(boxRadius), new Insets(boxOffset)));
                    grid.add(label, x, y);
                }
                if (x == 0 && y > 0) {
                    Label label = createLabel(Integer.toString(startWeek + y), new BackgroundFill(Color.TRANSPARENT, new CornerRadii(boxRadius), new Insets(boxOffset)));
                    grid.add(label, x, y);
                }
            }
        }

        int indexX = currentDate.getDayOfWeek().getValue() - 1;
        int indexY = 5;
        for (int i = 0 ; i < 30 ; i++) {
            LocalDate date = currentDate.minusDays(i);
            Label label = createLabel(Integer.toString(date.getDayOfMonth()), new BackgroundFill(BOX_COLOR, new CornerRadii(boxRadius), new Insets(boxOffset)));
            grid.add(label, indexX + 1, indexY);

            indexX = indexX - 1;
            if (indexX == -1) {
                indexX = 6;
                indexY--;
            }
        }

        getChildren().setAll(grid);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    private Label createLabel(final String text, final BackgroundFill backgroundFill) {
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.CENTER);
        label.setBackground(new Background(backgroundFill));
        label.setFont(Font.font(size * 0.065));
        label.setPrefWidth(Double.MAX_VALUE);
        label.setPrefHeight(Double.MAX_VALUE);
        label.setOnMousePressed(mouseHandler);
        GridPane.setFillHeight(label, true);
        GridPane.setFillHeight(label, true);
        return label;
    }


    // ******************** Layout *******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

        if (width > 0 && height > 0) {
            Font font = Font.font(size * 0.065);
            boxWidth  = width  / 8;
            boxHeight = height / 6;
            boxRadius = size * 0.025;
            boxOffset = size * 0.01;

            grid.setMaxSize(width, height);
            grid.setPrefSize(width, height);
            grid.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);
            grid.getColumnConstraints().forEach(columnConstraints -> {
                columnConstraints.setMinWidth(boxWidth);
                columnConstraints.setMaxWidth(boxWidth);
                columnConstraints.setPrefWidth(boxWidth);
            });
            grid.getRowConstraints().forEach(rowConstraints -> {
                rowConstraints.setMinHeight(boxHeight);
                rowConstraints.setMaxHeight(boxHeight);
                rowConstraints.setPrefHeight(boxHeight);
            });
            for (int y = 1 ; y < 7 ; y++) {
                Optional<Node> optLeftNode = Helper.getCellAt(grid, 0, y);
                if (optLeftNode.isPresent()) {
                    Label label = (Label) optLeftNode.get();
                    label.setFont(font);
                }
                for (int x = 1 ; x < 9 ; x++) {
                    Optional<Node> optTopNode = Helper.getCellAt(grid, x, 0);
                    if (optTopNode.isPresent()) {
                        Label label = (Label) optTopNode.get();
                        label.setFont(font);
                    }
                    Optional<Node> optLabel = Helper.getCellAt(grid, x, y);
                    if (optLabel.isPresent()) {
                        Label   label      = (Label) optLabel.get();
                        boolean isSelected = label.equals(selectedLabel);
                        label.setBackground(new Background(new BackgroundFill(isSelected ? SELECTED_BOX_COLOR : BOX_COLOR, new CornerRadii(boxRadius), new Insets(boxOffset))));
                        label.setFont(font);
                    }
                }
            }
        }
    }
}