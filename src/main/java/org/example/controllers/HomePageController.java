package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    @FXML
    private MenuItem idgotoLoginPage;

    @FXML
    void gotoLoginPage(ActionEvent event) {
        gotoLogin();
        //Stage stage = (Stage)idgotoLoginPage.getScene().getWindow();
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //https://stackoverflow.com/questions/20594392/unable-to-get-scene-from-menuitem-in-javafx
        //Para fechar o menuitem em "SAIR"
        Stage stage = (Stage)idgotoLoginPage.getParentPopup().getOwnerWindow();
        stage.close();
    }

    public void gotoLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
