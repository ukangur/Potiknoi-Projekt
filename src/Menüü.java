import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


//menuitemi klassi sain netist

public class Menüü extends Application {

    private Stage stage;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;

    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    private VBox menuBox;
    private int currentItem;

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private EventHandler getHandler(VBox menuBox) {
        return (EventHandler<KeyEvent>) event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    this.getMenuItem(currentItem).setActive(false);
                    this.getMenuItem(--currentItem).setActive(true);
                    System.out.println("üles");
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    this.getMenuItem(currentItem).setActive(false);
                    this.getMenuItem(++currentItem).setActive(true);
                    System.out.println("alla");
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                this.getMenuItem(currentItem).setActive(false);
                this.getMenuItem(currentItem).activate();
                this.getMenuItem(0).setActive(true);
            }

        };
    }

    private void LooSisu() throws Exception {
        currentItem = 0;
        StackPane root = new StackPane();
        root.setPrefSize(800, 600);

        InputStream pa = Files.newInputStream(Paths.get("Taust.png"));

        Image img = new Image(pa);
        pa.close();

        ImageView imgv = new ImageView(img);

        imgv.setFitWidth(800);
        imgv.setFitHeight(600);

        MenuItem itemExit = new MenuItem("Välju Mängust");
        itemExit.setOnActivate(() -> System.exit(0));

        MenuItem itemPlay = new MenuItem("Mängima");
        itemPlay.setOnActivate(() -> stage.setScene(scene4));

        MenuItem itemMusic = new MenuItem("Muusika");
        itemMusic.setOnActivate(() -> {
            stage.setScene(scene2);
            currentItem = 0;
            menuBox = (VBox) scene2.getRoot().getChildrenUnmodifiable().filtered(n -> n instanceof VBox).get(0);
        });

        MenuItem itemInstruction = new MenuItem("Õpetus");
        itemInstruction.setOnActivate(() -> {
            stage.setScene(scene3);
            currentItem = 0;
            menuBox = (VBox) scene3.getRoot().getChildrenUnmodifiable().filtered(n -> n instanceof VBox).get(0);
        });

        menuBox = new VBox(20,
                itemPlay,
                itemMusic,
                itemInstruction,
                itemExit);

        getMenuItem(currentItem).setActive(true);

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            double y = newValue.doubleValue();

            imgv.setFitHeight(y);


        });

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double x = newValue.doubleValue();
            imgv.setFitWidth(x);

        });

        menuBox.setAlignment(Pos.CENTER);

        StackPane.setAlignment(menuBox, Pos.CENTER);
        root.getChildren().addAll(imgv, menuBox);

        scene1 = new Scene(root);

        scene1.setOnKeyPressed(getHandler(menuBox));
    }

    private void LooSisu2() {

        StackPane root = new StackPane();
        root.setPrefSize(800, 600);

        Rectangle bg = new Rectangle(800, 600);

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            double y = newValue.doubleValue();

            bg.setHeight(y);


        });

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double x = newValue.doubleValue();
            bg.setWidth(x);

        });

        Media menuMusic1 = new Media(new File("muusika1.mp3").toURI().toString());
        MediaPlayer mp1 = new MediaPlayer(menuMusic1);
        mp1.setCycleCount(MediaPlayer.INDEFINITE);

        Media menuMusic2 = new Media(new File("muusika2.mp3").toURI().toString());
        MediaPlayer mp2 = new MediaPlayer(menuMusic2);
        mp2.setCycleCount(MediaPlayer.INDEFINITE);

        Media menuMusic3 = new Media(new File("muusika3.mp3").toURI().toString());
        MediaPlayer mp3 = new MediaPlayer(menuMusic3);
        mp3.setCycleCount(MediaPlayer.INDEFINITE);

        Media menuMusic4 = new Media(new File("muusika4.mp3").toURI().toString());
        MediaPlayer mp4 = new MediaPlayer(menuMusic4);
        mp4.setCycleCount(MediaPlayer.INDEFINITE);

        MenuItem itemMusic1 = new MenuItem("Lugu 1");
        itemMusic1.setOnActivate(() -> {
            mp1.stop();
            mp2.stop();
            mp3.stop();
            mp4.stop();
            mp1.play();
            currentItem = 0;
        });

        MenuItem itemMusic2 = new MenuItem("Lugu 2");
        itemMusic2.setOnActivate(() -> {
            mp1.stop();
            mp2.stop();
            mp3.stop();
            mp4.stop();
            mp2.play();
            currentItem = 0;
        });

        MenuItem itemMusic3 = new MenuItem("Lugu 3");
        itemMusic3.setOnActivate(() -> {
            mp1.stop();
            mp2.stop();
            mp3.stop();
            mp4.stop();
            mp3.play();
            currentItem = 0;
        });

        MenuItem itemMusic4 = new MenuItem("Lugu 4");
        itemMusic4.setOnActivate(() -> {
            mp1.stop();
            mp2.stop();
            mp3.stop();
            mp4.stop();
            mp4.play();
            currentItem = 0;
        });

        MenuItem itemTagasi = new MenuItem("Tagasi");
        itemTagasi.setOnActivate(() -> {
            stage.setScene(scene1);
            currentItem = 0;
            menuBox = (VBox) scene1.getRoot().getChildrenUnmodifiable().filtered(n -> n instanceof VBox).get(0);
            scene1.setOnKeyPressed(getHandler(menuBox));
        });

        menuBox = new VBox(20,
                itemMusic1,
                itemMusic2,
                itemMusic3,
                itemMusic4,
                itemTagasi);
        menuBox.setAlignment(Pos.CENTER);

        getMenuItem(currentItem).setActive(true);

        root.getChildren().addAll(bg, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER);

        scene2 = new Scene(root);
        scene2.setOnKeyPressed(getHandler(menuBox));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void LooSisu3() {

        StackPane root = new StackPane();
        root.setPrefSize(800, 600);

        Rectangle bg = new Rectangle(800, 600);
        Text kirjeldus = new Text("Igale mängijale jagatakse kuus kaarti. \n Ülejäänud kaartide seast tõmmatakse trump (kõige tugevam mast).\n Mängija, kellele käiakse, peab käidud kaardi ära tapma kas sama mastiga või trumbiga.\n Võidab see, kes kaartidest enne lahti saab.");
kirjeldus.setFill(Color.WHITE);

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            double y = newValue.doubleValue();

            bg.setHeight(y);


        });

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double x = newValue.doubleValue();
            bg.setWidth(x);

        });


        MenuItem itemTagasi = new MenuItem("Tagasi");
        itemTagasi.setOnActivate(() -> {
            stage.setScene(scene1);
            currentItem = 0;
            menuBox = (VBox) scene1.getRoot().getChildrenUnmodifiable().filtered(n -> n instanceof VBox).get(0);
            scene1.setOnKeyPressed(getHandler(menuBox));
        });

        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(kirjeldus);
        menuBox = new VBox(20,
                itemTagasi);
kirjeldus.setTextAlignment(TextAlignment.CENTER);
        menuBox.setAlignment(Pos.CENTER);

        getMenuItem(0).setActive(true);
kirjeldus.setFont(Font.font("Arial",20));

        root.getChildren().addAll(bg, menuBox,vbox2);
        StackPane.setAlignment(menuBox, Pos.CENTER);

        getHandler(menuBox);

        scene3 = new Scene(root);
        scene3.setOnKeyPressed(getHandler(menuBox));
    }

    private void LooSisu4() {

        StackPane root = new StackPane();
        root.setPrefSize(800, 600);

        Rectangle bg = new Rectangle(800, 600);

        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            double y = newValue.doubleValue();

            bg.setHeight(y);


        });

        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double x = newValue.doubleValue();
            bg.setWidth(x);

        });


        root.getChildren().addAll(bg);

        scene4 = new Scene(root);
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
            super(40);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(text);
            setActive(false);
        }

        public void setActive(boolean b) {
            text.setFill(b ? Color.DARKRED : Color.GREY);
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
        LooSisu2();
        LooSisu3();
        LooSisu4();
        LooSisu();

        this.stage = primaryStage;

        primaryStage.setScene(scene1);
        primaryStage.setOnCloseRequest(event -> bgThread.shutdownNow());

        primaryStage.show();
    }

    public static void main(String[] args) {
        Media menuMusic0 = new Media(new File("menuu.mp3").toURI().toString());
        MediaPlayer mp0 = new MediaPlayer(menuMusic0);
        mp0.play();
        launch(args);
    }
}

