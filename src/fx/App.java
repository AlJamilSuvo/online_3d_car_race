package fx;/**
 * Created by Suvo on 17-Dec-15.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class App extends Application {
    Stage stage;
    int status=0;
    long lastFrmae;
    Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root=new Group();
        Image image=new Image(String.valueOf(getClass().getResource("buet_logo.png")));
        ImageView imageView=new ImageView(image);
        root.getChildren().add(imageView);
        Scene scene=new Scene(root,image.getWidth(),image.getHeight(), Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        stage=primaryStage;
        stage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        scene.setOnMouseClicked(e->{
            Platform.exit();
        });




        /*new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(lastFrmae==0) {
                    lastFrmae=now;
                    return;
                }
                if (status==0){
                    System.out.println(now-lastFrmae);
                    if ((now-lastFrmae)>600000000){
                        FXMLLoader loader=new FXMLLoader();
                        loader.setLocation(getClass().getResource("page.fxml"));
                        try {
                            Parent root=loader.load();
                            Scene scene1=new Scene(root,740,420);
                            primaryStage.setScene(scene1);
                            controller=loader.getController();
                            primaryStage.show();
                            status=1;
                            lastFrmae=now;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                if (status==1){
                    if((now-lastFrmae)>600000000){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                controller.changeImage();
                            }
                        });

                        lastFrmae=now;
                    }
                }
            }
        }.start();*/


    }

}
