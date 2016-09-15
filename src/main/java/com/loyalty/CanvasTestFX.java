package canvastestfx;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class CanvasTestFX extends Application {

    private double textXPos;
    private double textYPos;
    private double textSize;

    private Toolkit tk;
    private Dimension screenDimension;
    private String stringToDisplay;
    private Text text;

    private Canvas canvas;
    private GraphicsContext canvasgc;

    @Override
    public void init(){

        tk = Toolkit.getDefaultToolkit();
        screenDimension = tk.getScreenSize();

        stringToDisplay = "Text to display Text to display Text to display Text to display Text to display Text to display Text to display Text to display";
        text = new Text(stringToDisplay);
        textSize = 60.00;
        text.setFont(Font.font("Verdana", textSize));
        textXPos = screenDimension.getWidth();
        textYPos = textSize;
        text.setX(textXPos);
        text.setY(textYPos);
        text.setFill(Color.DARKVIOLET);
        text.setFontSmoothingType(FontSmoothingType.LCD);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setTitle("JavaFX 2 Canvas Test");

        Group root = new Group();
        canvas = new Canvas(screenDimension.getWidth(), textSize+10);
        canvasgc = canvas.getGraphicsContext2D();
        canvasgc.setFill(Color.BLACK);
        canvasgc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Animate();

        root.getChildren().add(canvas);
        root.getChildren().add(text);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void Animate(){

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        final KeyValue keyvalue = new KeyValue(text.xProperty(), screenDimension.getWidth()-screenDimension.getWidth()-text.getBoundsInLocal().getWidth());
        final KeyFrame keyframe = new KeyFrame(Duration.millis(10000), keyvalue);
        timeline.getKeyFrames().add(keyframe);
        timeline.play();
    }

    @Override
    public void stop()
    {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}