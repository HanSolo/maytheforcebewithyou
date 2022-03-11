package eu.hansolo.maytheforcebewithyou.canvas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class DemoMatrix extends Application {
    private static int          noOfNodes;
    private        Label        titleCanvas;
    private        Label        titleNodes;
    private        MatrixCanvas matrixCanvas;
    private        MatrixNodes  matrixNodes;



    @Override public void init() {
        titleCanvas  = new Label("MatrixCanvas: - Nodes");
        titleCanvas.setFont(Font.font("SF Pro", FontWeight.NORMAL, 18));
        titleNodes   = new Label("MatrixNodes: - Nodes");
        titleNodes.setFont(Font.font("SF Pro", FontWeight.NORMAL, 18));
        matrixCanvas = new MatrixCanvas();
        matrixNodes  = new MatrixNodes();
    }

    @Override public void start(final Stage stage) {
        VBox vBoxLeft  = new VBox(10, titleCanvas, matrixCanvas);
        VBox vBoxRight = new VBox(10, titleNodes, matrixNodes);
        VBox.setVgrow(matrixCanvas, Priority.ALWAYS);
        VBox.setVgrow(matrixNodes, Priority.ALWAYS);

        HBox hBox = new HBox(10, vBoxLeft, vBoxRight);
        hBox.widthProperty().addListener(o -> {
            vBoxLeft.setPrefWidth(hBox.getWidth() * 0.5);
            vBoxRight.setPrefWidth(hBox.getWidth() * 0.5);
        });

        StackPane pane = new StackPane(hBox);
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);

        stage.setTitle("Matrix Demo");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();


        noOfNodes = 0;
        calcNoOfNodes(matrixCanvas);
        titleCanvas.setText("MatrixCanvas: " + noOfNodes + " node");

        noOfNodes = 0;
        calcNoOfNodes(matrixNodes);
        titleNodes.setText("MatrixNodes: " + noOfNodes + " nodes");
    }

    @Override public void stop() {
        Platform.exit();
    }


    private static void calcNoOfNodes(Node node) {
        if (node instanceof Parent) {
            if (((Parent) node).getChildrenUnmodifiable().size() != 0) {
                //System.out.println("Parent: " + node.getClass().getSimpleName());
                ObservableList<Node> tempChildren = ((Parent) node).getChildrenUnmodifiable();
                noOfNodes += tempChildren.size();
                for (Node n : tempChildren) {
                    //System.out.println(n.getClass().getSimpleName());
                    calcNoOfNodes(n);
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
