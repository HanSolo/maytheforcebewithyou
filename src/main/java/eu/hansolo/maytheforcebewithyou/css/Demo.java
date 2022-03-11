package eu.hansolo.maytheforcebewithyou.css;

import eu.hansolo.maytheforcebewithyou.css.controls.DemoButton;
import eu.hansolo.maytheforcebewithyou.css.controls.DemoCheckBox;
import eu.hansolo.maytheforcebewithyou.css.controls.DemoTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Demo extends Application {
    private static final PseudoClass     DARK_PSEUDO_CLASS = PseudoClass.getPseudoClass("dark");
    private              BooleanProperty dark;
    private              DemoCheckBox    demoCheckBox;
    private              DemoButton      demoButton1;
    private              DemoButton      demoButton2;
    private              DemoTextField   demoTextField;
    private              StackPane       pane;


    @Override public void init() {
        dark          = new BooleanPropertyBase() {
            @Override protected void invalidated() { pane.pseudoClassStateChanged(DARK_PSEUDO_CLASS, get()); }
            @Override public Object getBean() { return pane; }
            @Override public String getName() { return "dark"; }
        };
        demoCheckBox  = new DemoCheckBox("Dark Mode");
        demoButton1   = new DemoButton("Click me");
        demoButton2   = new DemoButton("Click me");
        demoTextField = new DemoTextField();

        demoCheckBox.setIndeterminate(true);

        setupBindings();
        registerListeners();
    }

    private void setupBindings() {
        demoCheckBox.darkProperty().bind(dark);
        demoButton1.darkProperty().bind(dark);
        demoButton2.darkProperty().bind(dark);
        demoTextField.darkProperty().bind(dark);
    }

    private void registerListeners() {
        demoCheckBox.selectedProperty().addListener((o, ov, nv) -> dark.set(nv));
    }

    @Override public void start(final Stage stage) {
        VBox buttonBox = new VBox(20, demoCheckBox, demoButton1, demoButton2, demoTextField);

        pane = new StackPane(buttonBox);
        pane.setPadding(new Insets(20));
        pane.getStyleClass().setAll("stack-pane");

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Demo.class.getResource("styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("May the force be with you");
        stage.show();
        stage.centerOnScreen();

        demoButton1.setStyle("-accent-color: -PINK-AQUA; -accent-color-dark: -PINK-DARK;");
    }

    @Override public void stop() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
