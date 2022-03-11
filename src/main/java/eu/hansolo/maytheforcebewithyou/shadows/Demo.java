package eu.hansolo.maytheforcebewithyou.shadows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Demo extends Application {
    private BorderPane upperLeftPane;
    private BorderPane upperRightPane;
    private AnchorPane lowerLeftPane;
    private AnchorPane lowerRightPane;


    @Override public void init() {
        // UPPER LEFT
        StackPane upperLeftHeader  = createHeader("Shadow hidden");
        StackPane upperLeftContent = createContent("BorderPane");

        upperLeftPane = new BorderPane();
        upperLeftPane.setTop(upperLeftHeader);
        upperLeftPane.setCenter(upperLeftContent);

        // UPPER RIGHT
        StackPane upperRightHeader  = createHeader("Shadow visible");
        StackPane upperRightContent = createContent("BorderPane");

        upperRightPane = new BorderPane();
        upperRightPane.setCenter(upperRightContent);
        upperRightPane.setTop(upperRightHeader);

        // LOWER LEFT
        StackPane lowerLeftHeader  = createHeader("Shadow hidden");
        StackPane lowerLeftContent = createContent("AnchorPane");

        lowerLeftPane = new AnchorPane(lowerLeftHeader, lowerLeftContent);
        AnchorPane.setTopAnchor(lowerLeftHeader, 0d);
        AnchorPane.setRightAnchor(lowerLeftHeader, 0d);
        AnchorPane.setLeftAnchor(lowerLeftHeader, 0d);
        AnchorPane.setTopAnchor(lowerLeftContent, 50d);
        AnchorPane.setRightAnchor(lowerLeftContent, 0d);
        AnchorPane.setLeftAnchor(lowerLeftContent, 0d);

        // LOWER RIGHT
        StackPane lowerRightHeader  = createHeader("Shadow visible");
        StackPane lowerRightContent = createContent("AnchorPane");

        lowerRightPane = new AnchorPane(lowerRightContent, lowerRightHeader);
        AnchorPane.setTopAnchor(lowerRightHeader, 0d);
        AnchorPane.setRightAnchor(lowerRightHeader, 0d);
        AnchorPane.setLeftAnchor(lowerRightHeader, 0d);
        AnchorPane.setTopAnchor(lowerRightContent, 50d);
        AnchorPane.setRightAnchor(lowerRightContent, 0d);
        AnchorPane.setLeftAnchor(lowerRightContent, 0d);
    }

    private StackPane createHeader(final String title) {
        StackPane header = new StackPane(new Label(title));
        header.setPrefSize(280, 50);
        header.setBackground(new Background(new BackgroundFill(Color.rgb(180, 180, 180), CornerRadii.EMPTY, Insets.EMPTY)));
        header.setEffect(new DropShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.75), 1, 0.0, 0, 1));
        return header;
    }

    private StackPane createContent(final String text) {
        StackPane content = new StackPane(new Label(text));
        content.setPrefSize(280, 350);
        content.setBackground(new Background(new BackgroundFill(Color.rgb(220, 220, 220), CornerRadii.EMPTY, Insets.EMPTY)));
        return content;
    }

    @Override public void start(final Stage stage) {
        HBox upperHBox = new HBox(20, upperLeftPane, upperRightPane);
        upperHBox.setPadding(new Insets(20));

        HBox lowerHBox = new HBox(20, lowerLeftPane, lowerRightPane);
        lowerHBox.setPadding(new Insets(20));

        VBox pane = new VBox(20, upperHBox, lowerHBox);
        pane.setPadding(new Insets(20));

        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle("Shadow Demo");
        stage.show();
        stage.centerOnScreen();
    }

    @Override public void stop() {
        Platform.exit();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
