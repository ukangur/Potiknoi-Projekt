import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


//menuitemi klassi sain netist

public class Graafika extends Application {

    private Stage stage;
    private Scene scene1;
    private Scene scene2;

    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    private VBox menuBox;
    private int currentItem = 0;

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private EventHandler getHandler(){
        EventHandler handler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {
                    if (currentItem > 0) {
                        Graafika.this.getMenuItem(currentItem).setActive(false);
                        Graafika.this.getMenuItem(--currentItem).setActive(true);
                    }
                }

                if (event.getCode() == KeyCode.DOWN) {
                    if (currentItem < menuBox.getChildren().size() - 1) {
                        Graafika.this.getMenuItem(currentItem).setActive(false);
                        Graafika.this.getMenuItem(++currentItem).setActive(true);
                    }
                }

                if (event.getCode() == KeyCode.ENTER) {
                    Graafika.this.getMenuItem(currentItem).activate();
                }

            }
        };
        return handler;
    }

    ;

    public Parent LooSisu() {
        StackPane root = new StackPane();
        root.setPrefSize(900, 600);

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

        Scene scene2 = new Scene(LooSisu2());

        MenuItem itemExit = new MenuItem("Välju Mängust");
        itemExit.setOnActivate(() -> System.exit(0));

        MenuItem itemPlay = new MenuItem("Mängima");
        itemPlay.setOnActivate(() -> System.exit(0));

        MenuItem itemSettings = new MenuItem("Seaded");
        itemSettings.setOnActivate(() -> System.exit(0));

        MenuItem itemMusic = new MenuItem("Muusika");
        itemMusic.setOnActivate(() -> {
            stage.setScene(scene2);
            scene2.setOnKeyPressed(getHandler());
        });

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
        StackPane.setAlignment(menuBox, Pos.CENTER);

        return root;
    }

    public Parent LooSisu2() {

        StackPane root = new StackPane();
        root.setPrefSize(900, 600);

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

        Media menuMusic1 = new Media(new File("C:\\Users\\Laptop\\Documents\\GitHub\\Potiknoi-Projekt\\out\\production\\Potiknoi\\muusika1.mp3").toURI().toString());
        MediaPlayer mp1 = new MediaPlayer(menuMusic1);
        mp1.setCycleCount(MediaPlayer.INDEFINITE);


        MenuItem itemMusic1 = new MenuItem("Lugu 1");
        itemMusic1.setOnActivate(mp1::play);

        MenuItem itemPlay = new MenuItem("Lugu 2");
        itemPlay.setOnActivate(() -> System.exit(0));

        MenuItem itemSettings = new MenuItem("Lugu 3");
        itemSettings.setOnActivate(() -> System.exit(0));

        MenuItem itemMusic = new MenuItem("Lugu 4");
        itemMusic.setOnActivate(() -> System.exit(0));

        MenuItem itemInstruction = new MenuItem("Lugu 5");
        itemInstruction.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(20,
                itemMusic1,
                itemPlay,
                itemSettings,
                itemMusic,
                itemInstruction);
        menuBox.setAlignment(Pos.CENTER);

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bg, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER);

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

        this.stage = primaryStage;
        Scene scene1 = new Scene(LooSisu());

        scene1.setOnKeyPressed(getHandler());


        primaryStage.setScene(scene1);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
