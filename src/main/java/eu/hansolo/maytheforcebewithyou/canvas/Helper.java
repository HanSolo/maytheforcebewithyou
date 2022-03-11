package eu.hansolo.maytheforcebewithyou.canvas;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.Optional;


public class Helper {
    private Helper() {}

    public static final double clamp(final double min, final double max, final double value) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public static final Optional<Node> getCellAt(final GridPane grid, final int x, final int y) {
        return grid.getChildren().stream().filter(child -> grid.getColumnIndex(child) == x && grid.getRowIndex(child) == y).findFirst();
    }
}
