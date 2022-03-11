package eu.hansolo.maytheforcebewithyou.css;

import eu.hansolo.maytheforcebewithyou.css.CircleButton.Type;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class SimpleHeaderDemo extends Application {
    private static final String     STYLE = "simple-styles.css";
    private              HBox       buttonBox;
    private              AnchorPane header;
    private              Region     body;


    @Override public void init() {
        CircleButton close    = new CircleButton(Type.CLOSE);
        CircleButton minimize = new CircleButton(Type.MINIMIZE);
        CircleButton maximize = new CircleButton(Type.MAXIMIZE);

        buttonBox = new HBox(8, close, minimize, maximize);
        buttonBox.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(buttonBox, 0d);
        AnchorPane.setBottomAnchor(buttonBox, 0d);
        AnchorPane.setLeftAnchor(buttonBox, 22d);

        header = new AnchorPane(buttonBox);
        header.getStyleClass().add("header");

        body = new Region();
        body.getStyleClass().add("body");
    }

    @Override public void start(final Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(404, 400);
        pane.setPadding(new Insets(20));
        pane.setTop(header);
        pane.setCenter(body);

        Scene scene = new Scene(pane);
        scene.getStylesheets()
             .add(SimpleHeaderDemo.class.getResource(STYLE)
                                        .toExternalForm());

        stage.setTitle("Header Demo");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

}
