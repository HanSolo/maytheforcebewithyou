package eu.hansolo.maytheforcebewithyou.css;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;


public class Body extends StackPane {
    private static final PseudoClass     DARK_PSEUDO_CLASS     = PseudoClass.getPseudoClass("dark");
    private static final PseudoClass     INACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("inactive");
    private              BooleanProperty dark;
    private              BooleanProperty inactive;


    // ******************** Constructors **************************************
    public Body() {
        super();
        init();
    }
    public Body(final Node... nodes) {
        super(nodes);
        init();
    }


    // ******************** Initialization ************************************
    private void init() {
        this.dark     = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(DARK_PSEUDO_CLASS, get()); }
            @Override public Object getBean() { return Body.this; }
            @Override public String getName() { return "dark"; }
        };
        this.inactive = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(INACTIVE_PSEUDO_CLASS, get()); }
            @Override public Object getBean() { return Body.this; }
            @Override public String getName() { return "inactive"; }
        };
        getStyleClass().add("body");
    }


    // ******************** Methods *******************************************
    public final boolean isDark() { return dark.get(); }
    public final void setDark(final boolean dark) { this.dark.set(dark); }
    public final BooleanProperty darkProperty() { return dark; }

    public final boolean isInactive() { return inactive.get(); }
    public final void setInactive(final boolean inactive) { this.inactive.set(inactive); }
    public final BooleanProperty inactiveProperty() { return inactive; }


    // ******************** Style related *************************************
    @Override public String getUserAgentStylesheet() { return Header.class.getResource("styles.css").toExternalForm(); }
}
