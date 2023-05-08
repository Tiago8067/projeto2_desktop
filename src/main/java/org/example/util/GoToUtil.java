package org.example.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.example.controllers.HomePageController;

@NoArgsConstructor
public class GoToUtil {
    public void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToRegistro() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/registro.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToRegistroDadosPessoas() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/registroDadosPessoais.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToRegistroDadosLocalizacao() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/registroDadosLocalizacao.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public void goToHomePageAdmin() {
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("/views/admin/homePage.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            // carregar dados para aparecer ao incicializar
            HomePageController homePageController = fxmlLoader.getController();
            homePageController.listaFuncionarios();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToHomePageFuncionario() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/funcionario/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
