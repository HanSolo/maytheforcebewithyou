package eu.hansolo.maytheforcebewithyou.css;

import eu.hansolo.maytheforcebewithyou.css.CircleButton.Type;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.List;


/**
 * User: hansolo
 * Date: 24.01.22
 * Time: 04:49
 */
@DefaultProperty("children")
public class Header extends AnchorPane {
    public enum HeaderHeight {
        STANDARD(26.25),
        DOUBLE(52.5);

        private final double height;


        HeaderHeight(final double height) {
            this.height = height;
        }


        public double getHeight() { return height; }
    }

    private static final PseudoClass                      DARK_PSEUDO_CLASS     = PseudoClass.getPseudoClass("dark");
    private static final PseudoClass                      INACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("inactive");
    private static final StyleablePropertyFactory<Header> FACTORY               = new StyleablePropertyFactory<>(AnchorPane.getClassCssMetaData());
    private static final CssMetaData                      HEADER_HEIGHT         = FACTORY.createSizeCssMetaData("-header-height", s -> s.headerHeight, HeaderHeight.STANDARD.getHeight(), false);
    private              BooleanProperty                  dark;
    private              BooleanProperty                  inactive;
    private              StyleableProperty<Number>        headerHeight;
    private              CircleButton                     closeButton;
    private              CircleButton                     minimizeButton;
    private              CircleButton                     maximizeButton;
    private              HBox                             buttonBox;


    // ******************** Constructors **************************************
    public Header() {
        super();
        init();
    }
    public Header(final Node... nodes) {
        super(nodes);
        init();
    }


    // ******************** Initialization ************************************
    private void init() {
        this.dark           = new BooleanPropertyBase(false) {
            @Override protected void invalidated() { pseudoClassStateChanged(DARK_PSEUDO_CLASS, get()); }
            @Override public Object getBean() { return Header.this; }
            @Override public String getName() { return "dark"; }
        };
        this.inactive       = new BooleanPropertyBase(false) {
            @Override protected void invalidated() {
                pseudoClassStateChanged(INACTIVE_PSEUDO_CLASS, get());
                closeButton.setDisable(get());
                minimizeButton.setDisable(get());
                maximizeButton.setDisable(get());
            }
            @Override public Object getBean() { return Header.this; }
            @Override public String getName() { return "inactive"; }
        };
        this.headerHeight   = new StyleableObjectProperty<>() {
            @Override protected void invalidated() { setStyle("-header-height: " + get() + ";"); }
            @Override public Object getBean() { return Header.this; }
            @Override public String getName() { return "headerHeight"; }
            @Override public CssMetaData<? extends Styleable, Number> getCssMetaData() { return HEADER_HEIGHT; }
        };
        this.closeButton    = new CircleButton(Type.CLOSE);
        this.minimizeButton = new CircleButton(Type.MINIMIZE);
        this.maximizeButton = new CircleButton(Type.MAXIMIZE);
        this.buttonBox      = new HBox(8, closeButton, minimizeButton, maximizeButton);
        this.buttonBox.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(buttonBox, 0d);
        AnchorPane.setLeftAnchor(buttonBox, 11d);
        AnchorPane.setBottomAnchor(buttonBox, 0d);
        getChildren().add(buttonBox);
        getStyleClass().add("header");
    }


    // ******************** Methods *******************************************
    public final boolean isDark() { return dark.get(); }
    public final void setDark(final boolean dark) { this.dark.set(dark); }
    public final BooleanProperty darkProperty() { return dark; }

    public final boolean isInactive() { return inactive.get(); }
    public final void setInactive(final boolean inactive) { this.inactive.set(inactive); }
    public final BooleanProperty inactiveProperty() { return inactive; }

    public Double getHeaderHeight() { return headerHeight.getValue().doubleValue(); }
    public void setHeaderHeight(final HeaderHeight headerHeight)  {
        setHeaderHeight(headerHeight.getHeight());
        AnchorPane.setLeftAnchor(buttonBox, HeaderHeight.STANDARD == headerHeight ? 11d : 22d);
    }
    public void setHeaderHeight(final double headerHeight) { this.headerHeight.setValue(headerHeight); }
    public StyleableProperty<Number> headerHeightProperty() { return headerHeight; }


    // ******************** Style related *************************************
    @Override public String getUserAgentStylesheet() { return Header.class.getResource("styles.css").toExternalForm(); }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() { return FACTORY.getCssMetaData(); }
    @Override public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() { return FACTORY.getCssMetaData(); }
}