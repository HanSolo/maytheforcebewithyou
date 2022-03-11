package eu.hansolo.maytheforcebewithyou.nativeui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class StageDemo extends Application {
    public enum Mode { BRIGHT, DARK }
    private static final Color     BKG_COLOR      = Color.WHITESMOKE.deriveColor(0, 1, 1, 0.08);
    private static final Color     DRAG_BKG       = Color.WHITESMOKE.deriveColor(0, 1, 1, 0.08);
    private static final Color     BKG_COLOR_DARK = Color.BLACK.deriveColor(0, 1, 1, 0.85);
    private static final Color     DRAG_BKG_DARK  = Color.rgb(0, 0, 0, 0.3);
    private static final Effect    BLUR           = new BoxBlur(10, 10, 3);
    private static final ImageView BLURRED_BKG    = new ImageView();
    private              Mode      mode           = Mode.DARK;
    private              Rectangle bkg            = new Rectangle(400, 400, BKG_COLOR);
    private              Rectangle darkBkg        = new Rectangle(400, 400, BKG_COLOR_DARK);
    private              Label     label;
    private              StackPane pane;


    @Override public void init() {
        label = new Label("Translucent stage");
        label.setTextFill(Mode.BRIGHT == mode ? Color.BLACK : Color.WHITE);
    }

    @Override public void start(final Stage stage) {
        pane = new StackPane(BLURRED_BKG, Mode.BRIGHT == mode ? bkg : darkBkg, label);
        pane.setPrefSize(400, 400);
        pane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane, Color.rgb(0, 0, 0, 0.15));

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Translucent Stage");
        stage.show();
        stage.centerOnScreen();

        BLURRED_BKG.setImage(createBkgSnapshot(stage));
        BLURRED_BKG.setEffect(BLUR);

        makeDraggable(stage, pane);
    }

    @Override public void stop() {
        Platform.exit();
    }

    public void makeDraggable(final Stage stage, final Node node) {
        final AtomicReference<Double> deltaX = new AtomicReference<>(0.0);
        final AtomicReference<Double> deltaY = new AtomicReference<>(0.0);
        final AtomicBoolean dragging  = new AtomicBoolean(false);
        node.setOnMousePressed(e -> {
            node.setCursor(Cursor.CLOSED_HAND);
            deltaX.set(stage.getX() - e.getScreenX());
            deltaY.set(stage.getY() - e.getScreenY());
        });
        node.setOnMouseReleased(mouseEvent -> {
            node.setCursor(Cursor.HAND);
            if (dragging.get()) {
                stage.setOpacity(0);
                Timeline pause = new Timeline(new KeyFrame(Duration.millis(50), e -> {
                    BLURRED_BKG.setImage(createBkgSnapshot(stage));
                    pane.getChildren().set(0, BLURRED_BKG);
                    stage.setOpacity(1);
                }));
                pause.play();
            }
            dragging.set(false);
        });
        node.setOnMouseDragged(e -> {
            node.setCursor(Cursor.CLOSED_HAND);
            stage.setX(e.getScreenX() + deltaX.get());
            stage.setY(e.getScreenY() + deltaY.get());
            pane.getChildren().set(0, new Rectangle(stage.getWidth(), stage.getHeight(), Mode.BRIGHT == mode ? DRAG_BKG : DRAG_BKG_DARK));
            dragging.set(true);
        });
        node.setOnMouseEntered(e -> node.setCursor(e.isPrimaryButtonDown() ? Cursor.DEFAULT : Cursor.HAND));
        node.setOnMouseExited(e  -> node.setCursor(e.isPrimaryButtonDown() ? Cursor.HAND : Cursor.DEFAULT));
    }

    private Image createBkgSnapshot(final Stage stage) {
        final int x = (int) stage.getX();
        final int y = (int) stage.getY();
        final int w = (int) stage.getWidth();
        final int h = (int) stage.getHeight();
        WritableImage snapshot = new WritableImage(w, h);
        Robot robot = new Robot();
        robot.getScreenCapture(snapshot, x, y, w, h);
        return snapshot;
    }

    public static void main(String[] args) { launch(args); }
}
