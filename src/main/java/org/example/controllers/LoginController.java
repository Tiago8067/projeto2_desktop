package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnLoginId;

    @FXML
    private CheckBox checkBoxLoginGuardaPass;

    @FXML
    private Hyperlink hyperlinkLoginId;

    @FXML
    private Label labelErroLoginNomeEmail;

    @FXML
    private Label labelErroLoginPass;

    @FXML
    private TextField labelLoginNomeEmail;

    @FXML
    private TextField labelLoginPass;

    EntityManager entityManager = JPAUtil.getEntityManager();
    UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
    Utilizador utilizador = new Utilizador();

    @FXML
    void btnLogin(ActionEvent event) {
        //if (labelLoginNomeEmail.getText().isEmpty()) {
        //    labelErroLoginNomeEmail.setText("Tem de inserir o seu Nome de Utilizador ou Email para realizar o Login");
        //}

        System.out.println(this.utilizadorDao.buscarUtilizadorPorUsername(labelLoginNomeEmail.getText()));

        //System.out.println(this.utilizadorDao.buscarUtilizador(labelLoginNomeEmail.getText()));

        //if (labelLoginPass.getText().isEmpty()) {
        //    labelErroLoginPass.setText("Tem de inserir a sua Palavra-passe para realizar o Login");
        //}

        //System.out.println(new UtilizadorDao().buscarUtilizador(labelLoginPass.getText()));



        /*if (labelLoginNomeEmail.getText().equals(this.utilizador.getNome()) || labelLoginNomeEmail.getText().equals(this.utilizador.getEmail())){
            goToHomePageAdmin();
            Stage stage = (Stage) btnLoginId.getScene().getWindow();
            stage.close();
        }*/
    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {
        goToRegistro();
        Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void goToHomePageAdmin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/admin/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
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
