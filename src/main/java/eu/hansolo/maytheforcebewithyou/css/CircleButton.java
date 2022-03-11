package eu.hansolo.maytheforcebewithyou.css;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;


public class CircleButton extends StackPane {
    public enum Type {
        CLOSE("close"),
        MINIMIZE("minimize"),
        MAXIMIZE("maximize");

        public final String style;

        Type(final String style) {
            this.style = style;
        }
    }

    private final Region icon;

    public CircleButton(final Type type) {
        super();
        icon = new Region();
        icon.getStyleClass().add("icon");
        getChildren().setAll(icon);
        getStyleClass().add(type.style);
    }
}
