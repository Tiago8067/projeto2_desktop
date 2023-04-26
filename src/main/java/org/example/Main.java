package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        //Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        User u1 = new User();
        u1.setNome("User1");
        System.out.println(u1.getNome());
        //new login().setVisible(true);
        launch();
    }
}