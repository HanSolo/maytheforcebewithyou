package eu.hansolo.maytheforcebewithyou.canvas;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.List;


public class CanvasControl extends Region {
    private static final String                                  CSS_FILE        = "canvas-control.css";
    private static final String                                  STYLE_CLASS     = "canvas-control";
    private static final StyleablePropertyFactory<CanvasControl> FACTORY         = new StyleablePropertyFactory<>(Region.getClassCssMetaData());
    private static final CssMetaData<CanvasControl, Color>       COLOR_ONE       = FACTORY.createColorCssMetaData("-color-one", s -> s.colorOne, Color.RED, false);
    private static final CssMetaData<CanvasControl, Color>       COLOR_TWO       = FACTORY.createColorCssMetaData("-color-two", s -> s.colorTwo, Color.BLUE, false);
    private static final PseudoClass                             ON_PSEUDO_CLASS = PseudoClass.getPseudoClass("on");
    private static final double                                  PREF_WIDTH      = 250;
    private static final double                                  PREF_HEIGHT     = 250;
    private        final StyleableProperty<Color>                colorOne;
    private        final StyleableProperty<Color>                colorTwo;
    private              BooleanProperty                         on;
    private              double                                  size;
    private              double                                  inset;
    private              Canvas                                  canvas;
    private              GraphicsContext                         ctx;


    // ******************** Constructors **************************************
    public CanvasControl() {
        colorOne = new StyleableObjectProperty<>(COLOR_ONE.getInitialValue(CanvasControl.this)) {
            @Override protected void invalidated() { draw(); }
            @Override public Object getBean() { return CanvasControl.this; }
            @Override public String getName() { return "colorOne"; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return COLOR_ONE; }
        };
        colorTwo = new StyleableObjectProperty<>(COLOR_TWO.getInitialValue(CanvasControl.this)) {
            @Override protected void invalidated() { draw(); }
            @Override public Object getBean() { return CanvasControl.this; }
            @Override public String getName() { return "colorTwo"; }
            @Override public CssMetaData<? extends Styleable, Color> getCssMetaData() { return COLOR_TWO; }
        };
        on       = new BooleanPropertyBase(false) {
            @Override protected void invalidated() {
                pseudoClassStateChanged(ON_PSEUDO_CLASS, get());
                draw();
            }
            @Override public Object getBean() { return CanvasControl.this; }
            @Override public String getName() { return "on"; }
        };
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        getStyleClass().add(STYLE_CLASS);

        canvas = new Canvas(PREF_WIDTH, PREF_HEIGHT);
        ctx    = canvas.getGraphicsContext2D();

        getChildren().setAll(canvas);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    public Color getColorOne() { return colorOne.getValue(); }
    public void setColorOne(final Color color) { colorOne.setValue(color); }
    public ObjectProperty<Color> colorOneProperty() { return (ObjectProperty<Color>) colorOne; }

    public Color getColorTwo() { return colorTwo.getValue(); }
    public void setColorTwo(final Color color) { colorTwo.setValue(color); }
    public ObjectProperty<Color> colorTwoProperty() { return (ObjectProperty<Color>) colorTwo; }

    public boolean isOn() { return on.get(); }
    public void setOn(final boolean on) { this.on.set(on); }
    public BooleanProperty onProperty() { return on; }


    // ******************** Style related *************************************
    @Override public String getUserAgentStylesheet() {
        return CanvasControl.class.getResource(CSS_FILE).toExternalForm();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() { return FACTORY.getCssMetaData(); }
    @Override public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() { return FACTORY.getCssMetaData(); }


    // ******************** Resize/Redraw *************************************
    private void resize() {
        double width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        double height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size  = width < height ? width : height;
        inset = size * 0.1;

        if (width > 0 && height > 0) {
            setPrefSize(width, height);

            canvas.setWidth(size);
            canvas.setHeight(size);
            canvas.relocate((getWidth() - size) * 0.5, (getHeight() - size) * 0.5);

            draw();
        }
    }

    private void draw() {
        ctx.clearRect(0, 0, size, size);

        ctx.setFill(getColorOne());
        ctx.fillOval(0, 0, size, size);

        ctx.setFill(getColorTwo());
        ctx.fillOval(inset, inset, size - 2 * inset, size - 2 * inset);
    }
}
