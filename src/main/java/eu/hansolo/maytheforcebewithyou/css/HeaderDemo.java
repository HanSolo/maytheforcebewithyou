package eu.hansolo.maytheforcebewithyou.css;

import eu.hansolo.maytheforcebewithyou.css.Header.HeaderHeight;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class HeaderDemo extends Application {
    private Header     header;
    private Body       body;
    private CheckBox   modeCheckBox;


    @Override public void init() {
        header = new Header();
        header.setDark(true);
        header.setHeaderHeight(HeaderHeight.DOUBLE);

        modeCheckBox = new CheckBox("Dark Mode");
        modeCheckBox.setTextFill(Color.WHITE);
        modeCheckBox.setSelected(true);
        modeCheckBox.selectedProperty().addListener((o, ov, nv) -> {
            modeCheckBox.setText(nv ? "Dark Mode" : "Light Mode");
            modeCheckBox.setTextFill(nv ? Color.WHITE : Color.BLACK);
            header.setDark(nv);
            body.setDark(nv);
        });

        body = new Body(modeCheckBox);
        body.setDark(true);
    }

    @Override public void start(final Stage stage) {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(600, 400);
        pane.setPadding(new Insets(20));
        pane.setCenter(body);
        pane.setTop(header);

        Scene scene = new Scene(pane);

        stage.setTitle("Header Demo");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

        header.inactiveProperty().bind(stage.focusedProperty().not());
        body.inactiveProperty().bind(stage.focusedProperty().not());
    }

    @Override public void stop() {
        Platform.exit();
    }

    public static void main(String[] args) { launch(args); }
}
