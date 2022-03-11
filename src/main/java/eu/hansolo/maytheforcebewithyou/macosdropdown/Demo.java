package eu.hansolo.maytheforcebewithyou.macosdropdown;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Demo extends Application {
    private Region     arrows;
    private StackPane  arrowButton;
    private Label      label;
    private HBox       buttonBox;


    @Override public void init() {
        arrows = new Region();
        arrows.getStyleClass().add("arrows");

        arrowButton = new StackPane(arrows);
        arrowButton.getStyleClass().add("arrow-button");
        HBox.setHgrow(arrowButton, Priority.NEVER);
        HBox.setMargin(arrowButton, new Insets(0, 2, 0, 0));

        label = new Label("JavaFX");
        label.getStyleClass().add("box-label");
        label.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(label, Priority.ALWAYS);

        buttonBox = new HBox(5, label, arrowButton);
        buttonBox.getStyleClass().add("button-box");
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
    }

    @Override public void start(final Stage stage) {
        StackPane pane = new StackPane(buttonBox);
        pane.setPadding(new Insets(10));
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(39, 34, 35), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Demo.class.getResource("arrow.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("CSS Demo");
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
