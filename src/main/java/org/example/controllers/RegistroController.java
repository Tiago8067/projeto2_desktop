package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.util.JPAUtil;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;

import javax.persistence.EntityManager;
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

    EntityManager entityManager = JPAUtil.getEntityManager();

    @FXML
    void btnRegistrar(ActionEvent event) {
        Utilizador utilizador = new Utilizador();
        utilizador.setNome(labelRegistroNome.getText());
        utilizador.setEmail(labelErroRegistroEmail.getText());
        utilizador.setPassword(labelRegistroPass.getText());

        if (labelRegistroPass.getText().equals(labelRegistroConfirmaPass.getText())){
            UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
            //entityManager.getTransaction().begin();
            utilizadorDao.registrar(utilizador);
            //entityManager.getTransaction().commit();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("As palavras-passe não coincidem. Tente Novamentre.");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        Aqui coloca-se tipicamente açoes que quero que sejam executadas quando instancio o controlador
         */
    }
}
