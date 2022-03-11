package eu.hansolo.maytheforcebewithyou.css.controls;

import eu.hansolo.maytheforcebewithyou.css.Demo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;


public class DemoButton extends Button {
    private static final PseudoClass     DARK_PSEUDO_CLASS = PseudoClass.getPseudoClass("dark");
    private          boolean         _dark;
    private          BooleanProperty dark;


    // ******************** Constructors **************************************
    public DemoButton() {
        super();
        init();
    }
    public DemoButton(final String text) {
        super(text);
        init();
    }
    public DemoButton(final String text, final Node graphic) {
        super(text, graphic);
        init();
    }


    // ******************** Methods *******************************************
    private void init() {
        getStyleClass().add("demo-button");
        _dark = false;
    }

    public final boolean isDark() {
        return null == dark ? _dark : dark.get();
    }
    public final void setDark(final boolean dark) {
        if (null == this.dark) {
            _dark = dark;
            pseudoClassStateChanged(DARK_PSEUDO_CLASS, dark);
        } else {
            darkProperty().set(dark);
        }
    }
    public final BooleanProperty darkProperty() {
        if (null == dark) {
            dark = new BooleanPropertyBase() {
                @Override protected void invalidated() {
                    pseudoClassStateChanged(DARK_PSEUDO_CLASS, get());
                }
                @Override public Object getBean() { return DemoButton.this; }
                @Override public String getName() { return "dark"; }
            };
        }
        return dark;
    }


    // ******************** Style related *************************************
    @Override public String getUserAgentStylesheet() { return Demo.class.getResource("styles.css").toExternalForm(); }
}
