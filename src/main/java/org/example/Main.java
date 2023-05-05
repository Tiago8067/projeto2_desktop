package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import java.io.IOException;

import org.example.controllers.HomePageController;
import org.example.util.JPAUtil;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/registroDadosPessoais.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        //Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        //Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // teste carregar funcionarios
        //HomePageController homePageController = fxmlLoader.getController();
        //homePageController.listaFuncionarios();
    }

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        launch();
    }
}