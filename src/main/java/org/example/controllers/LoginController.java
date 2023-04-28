package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox checkBoxLoginGuardaPass;

    @FXML
    private Hyperlink hyperlinkLogin;

    @FXML
    private Label labelErroLoginNomeEmail;

    @FXML
    private Label labelErroLoginPass;

    @FXML
    private TextField labelLoginNomeEmail;

    @FXML
    private TextField labelLoginPass;

    @FXML
    void btnLogin(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
