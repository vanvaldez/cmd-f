package com.example.controlledbreathing;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class HelloApplication extends Application {

    private Pane root;
    private Button playButton;
    private Circle[] circles;
    private Circle circle;
    private Group Shape;
    private Group button;

    private Text heading;
    private Text inhaleText;
    private Text exhaleText;
    private Group inhaleGroup;
    private Group exhaleGroup;
    private Button stop;
    private int pressCounter = 0;
    private FadeTransition inhaleAni;
    private FadeTransition exhaleAni;
    private FadeTransition pulsing;


    @Override
    public void start(Stage primaryStage) throws IOException {
        root = new Pane();

        //bg
        root.setStyle(
                "-fx-background-image: url('https://wallpapercave.com/wp/wp3274885.jpg'); -fx-background-repeat: no-repeat; -fx-background-size: 500 500; -fx-background-position: center");

        //heading
        heading = new Text(40, 70, "Breathe");
        heading.setOpacity(0.4);
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 50));

        root.getChildren().add(heading);

        //yellow circle

        circle = new Circle(150, 200, 100, Color.web("#fff9c4"));
        circle.setOpacity(0.7);
        root.getChildren().add(circle);

        //play button

        //image source
        FileInputStream input = new FileInputStream("C:\\Users\\Vanessa Valdez\\Documents\\Projects\\cmd-f\\ControlledBreathing\\src\\main\\java\\com\\example\\controlledbreathing\\playButton.png");
        System.out.println(System.getProperty("user.dir"));
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        playButton = new Button("", imageView);
        playButton.setLayoutX(93);
        playButton.setLayoutY(146);
        playButton.setStyle("-fx-background-color: transparent; ");
        playButton.setOnMouseClicked(new ClickEventHandler());

        button = new Group();
        button.getChildren().add(playButton);

        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 450);
        primaryStage.setTitle("cmd-f 2023");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private class ClickEventHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e) {

            //increment counter
            pressCounter++;
            System.out.println(pressCounter);

            //circle maker
            Color[] YellowShades = {
                    Color.web("#FFFF00"),// yellow
                    Color.web("#f9a825"),// deep yellow
                    Color.web("#fbc02d"), //mustard yellow
                    Color.web("#fdd835"), //bright yellow
                    Color.web("#ffee58"), //pale yellow
                    Color.web("#fff176"), //light yellow
                    Color.web("#fff9c4") //cream yellow
            };

            circles = new Circle[YellowShades.length];
            Shape = new Group();
            for (int i = YellowShades.length - 1; i >= 0; i--)
            {
                circles[i] = new Circle(150,200, 10 * (i + 1));
                circles[i].setFill(YellowShades[i]);
                Shape.getChildren().add(circles[i]);
            }
            root.getChildren().add(Shape);

            //inhale
            inhaleText = new Text(80, 355, "I N H A L E");
            inhaleText.setFont(Font.font("Helvetica", FontWeight.THIN, 30));

            inhaleGroup = new Group();
            inhaleGroup.getChildren().add(inhaleText);
            root.getChildren().add(inhaleGroup);

            //inhale
            exhaleText = new Text(85, 400, "e x h a l e");
            exhaleText.setFont(Font.font("Helvetica", FontWeight.THIN, 30));

            exhaleGroup = new Group();
            exhaleGroup.getChildren().add(exhaleText);
            root.getChildren().add(exhaleGroup);

            inhaleAni = new FadeTransition(Duration.millis(5000), inhaleGroup);
            exhaleAni = new FadeTransition(Duration.millis(5000), exhaleGroup);
            pulsing = new FadeTransition(Duration.millis(5000), Shape);


            if (pressCounter == 1) {
                //remove play
                button.setVisible(false);

                //pulsing circle

                pulsing.setFromValue(0.0);
                pulsing.setToValue(1.0);
                pulsing.setAutoReverse(true);
                pulsing.setCycleCount(50);
                pulsing.play();


                //breathing prompts
                //inhale
                inhaleAni.setFromValue(0.0);
                inhaleAni.setToValue(1.0);
                inhaleAni.setAutoReverse(true);
                inhaleAni.setCycleCount(Timeline.INDEFINITE);
                inhaleAni.play();
                inhaleAni.setOnFinished(null);

                inhaleAni.setDelay(Duration.millis(5000));

                //exhale
                exhaleAni.setFromValue(1.0);
                exhaleAni.setToValue(0.0);
                exhaleAni.setAutoReverse(true);
                exhaleAni.setCycleCount(Timeline.INDEFINITE);
                exhaleAni.play();
                exhaleAni.setOnFinished(null);

                e.consume();

            }

        }
    }

    private class StopHandler implements EventHandler<ActionEvent> {
        @Override
        /** closes the stage (i.e. GUI window)
         *
         * @param e triggered event
         */
        public void handle(ActionEvent e) {
            //stop
            pulsing.stop();
            inhaleAni.stop();
            exhaleAni.stop();

            //put back to original
            pressCounter = 0;

            e.consume();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
