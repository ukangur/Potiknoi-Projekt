import java.awt.font.ImageGraphicAttribute;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import static javafx.scene.paint.Color.BLACK;


//menuitemi klassi sain netist

public class Graafika extends Application {

    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    private VBox menuBox;
    private int currentItem = 0;

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent LooSisu() {
        StackPane root = new StackPane();
        root.setPrefSize(900, 600);

        BackgroundImage piltbg = new BackgroundImage(new Image("Taust.png", 900, 600, true, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Image pilt = new Image("taust.png");
        Rectangle bg = new Rectangle(900, 600);

        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double y = newValue.doubleValue();

                bg.setHeight(y);


            }
        });

        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double x = newValue.doubleValue();
                bg.setWidth(x);

            }
        });

        MenuItem itemExit = new MenuItem("Välju Mängust");
        itemExit.setOnActivate(() -> System.exit(0));

        MenuItem itemPlay = new MenuItem("Mängima");
        itemPlay.setOnActivate(() -> System.exit(0));

        MenuItem itemSettings = new MenuItem("Seaded");
        itemSettings.setOnActivate(() -> System.exit(0));

        MenuItem itemMusic = new MenuItem("Muusika");
        itemMusic.setOnActivate(() -> System.exit(0));

        MenuItem itemInstruction = new MenuItem("Õpetus");
        itemInstruction.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(20,
                itemPlay,
                itemSettings,
                itemMusic,
                itemInstruction,
                itemExit);
        menuBox.setAlignment(Pos.CENTER);

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bg, menuBox);
        StackPane.setAlignment(menuBox,Pos.CENTER);

        return root;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }

    private static class SisuRaam extends StackPane {
        public SisuRaam(Node sisu) {
            setAlignment(Pos.CENTER);

            Rectangle raam = new Rectangle(200, 200);
            raam.setArcWidth(25);
            raam.setArcHeight(25);
            raam.setStroke(Color.WHITESMOKE);

            getChildren().addAll(raam, sisu);
        }
    }

    private static class MenuItem extends HBox {
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(text);
            setActive(false);
        }

        public void setActive(boolean b) {
            text.setFill(b ? Color.WHITE : Color.GREY);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(LooSisu());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                getMenuItem(currentItem).activate();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
