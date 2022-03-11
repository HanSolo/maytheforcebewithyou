package eu.hansolo.maytheforcebewithyou.canvas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Demo extends Application {
    private CanvasControl control;

    @Override public void init() {
        control = new CanvasControl();
        control.setOnMousePressed(e -> control.setOn(!control.isOn()));
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(control);
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);

        stage.setTitle("CSS styled Canvas");
        stage.setScene(scene);
        stage.show();

        // Set color directly
        control.setColorTwo(Color.CRIMSON);

        // Test CSS pseudo class
        //control.setOn(true);

        // Test loading custom css file to overwrite styles by css
        //scene.getStylesheets().add(Demo.class.getResource("custom.css").toExternalForm());
    }

    @Override public void stop() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
