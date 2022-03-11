module eu.hansolo.mayTheforcebewithyou {
    // Java
    requires java.base;

    // Java-FX
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires transitive javafx.media;
    requires transitive javafx.swing;

    // 3rd Party
    //requires transitive eu.hansolo.jdktools;
    //requires transitive eu.hansolo.toolbox;
    //requires transitive eu.hansolo.toolboxfx;
    //requires transitive eu.hansolo.applefx;

    exports eu.hansolo.maytheforcebewithyou.css;
    exports eu.hansolo.maytheforcebewithyou.css.controls;
    exports eu.hansolo.maytheforcebewithyou.nativeui;
    exports eu.hansolo.maytheforcebewithyou.canvas;
}