package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.example.util.GoToUtil;

import java.io.IOException;

public class HomePageController {
    @FXML
    private MenuItem idgotoLoginPage;

    GoToUtil goToUtil = new GoToUtil();

    @FXML
    void gotoLoginPage(ActionEvent event) {
        this.goToUtil.goToLogin();
        //Stage stage = (Stage)idgotoLoginPage.getScene().getWindow();
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //https://stackoverflow.com/questions/20594392/unable-to-get-scene-from-menuitem-in-javafx
        //Para fechar o menuitem em "SAIR"
        Stage stage = (Stage)idgotoLoginPage.getParentPopup().getOwnerWindow();
        stage.close();
    }
}
