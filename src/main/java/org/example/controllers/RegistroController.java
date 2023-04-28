package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {

    @FXML
    private Button btnRegistrar;

    @FXML
    private Hyperlink hyperlinkLogin;

    @FXML
    private Label labelErroRegistroConfirmaPass;

    @FXML
    private Label labelErroRegistroEmail;

    @FXML
    private Label labelErroRegistroNome;

    @FXML
    private Label labelErroRegistroPass;

    @FXML
    private TextField labelRegistroConfirmaPass;

    @FXML
    private TextField labelRegistroEmail;

    @FXML
    private TextField labelRegistroNome;

    @FXML
    private TextField labelRegistroPass;

    @FXML
    void btnRegistrar(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        Aqui coloca-se tipicamente açoes que quero que sejam executadas quando instancio o controlador
         */
    }
}
