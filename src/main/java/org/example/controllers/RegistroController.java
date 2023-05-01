package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;
import org.example.util.JPAUtil;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {

    @FXML
    private Button btnRegistrar;

    @FXML
    private Hyperlink hyperlinkLoginId;

    @FXML
    private Label labelErroRegistroConfirmaPass;

    @FXML
    private Label labelErroRegistroEmail;

    @FXML
    private Label labelErroRegistroNome;

    @FXML
    private Label labelErroRegistroPass;

    @FXML
    private Label labelErroRegistroTipoUtilizador;

    @FXML
    private TextField labelRegistroConfirmaPass;

    @FXML
    private TextField labelRegistroEmail;

    @FXML
    private TextField labelRegistroNome;

    @FXML
    private TextField labelRegistroPass;

    @FXML
    private Label labelTipoUtilziador;

    @FXML
    private MenuItem menuItemIdAdmin;

    @FXML
    private MenuItem menuItemIdFuncionario;

    EntityManager entityManager = JPAUtil.getEntityManager();
    UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
    Utilizador utilizador = new Utilizador();
    RegexDados regexDados = new RegexDados();

    @FXML
    void btnRegistrar(ActionEvent event) {

        //System.out.println(labelRegistroNome.getText());

        //System.out.println(this.utilizadorDao.buscarUsernameDoUtilizador(labelRegistroNome.getText()));

        if (labelRegistroNome.getText().isEmpty()){
            labelErroRegistroNome.setText("Tem de preencher o Nome de Utilizador no seu Registro");
        } else if (labelRegistroNome.getText().length() < 4) {
            labelErroRegistroNome.setText("O Nome de Utilizador no seu Registro precisa de 4 carateres!");
        } else if (this.utilizadorDao.buscarUsernameDoUtilizador(labelRegistroNome.getText()).equals(labelRegistroNome.getText())) {
            labelErroRegistroNome.setText("O Nome de Utilizador já existe. Insira Outro.");
        } else {
            this.utilizador.setUsername(labelRegistroNome.getText());
            labelErroRegistroNome.setText("");
        }

        if (labelRegistroEmail.getText().isEmpty()){
            labelErroRegistroEmail.setText("Tem de preencher o Email no seu Registro");
        } else if (!this.regexDados.validateEmail(labelRegistroEmail.getText())) {
            labelErroRegistroEmail.setText("Por favor, preencha o Email Corretamente");
        }else {
            this.utilizador.setEmail(labelRegistroEmail.getText());
            labelErroRegistroEmail.setText("");
        }

        if (labelRegistroPass.getText().isEmpty()){
            labelErroRegistroPass.setText("Tem de preencher a Palavra-passe no seu Registro");
        } else if (!this.regexDados.isValidPassword(labelRegistroPass.getText())) {
            labelErroRegistroPass.setText("Por favor, preencha a Palavra-passe Corretamente");
        } else {
            this.utilizador.setPassword(labelRegistroPass.getText());
            labelErroRegistroPass.setText("");
        }

        if (labelRegistroConfirmaPass.getText().isEmpty()){
            labelErroRegistroConfirmaPass.setText("Tem de preencher a confirmação da Palavra-passe no seu Registro");
        } else if (!labelRegistroPass.getText().equals(labelRegistroConfirmaPass.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("As palavras-passe não coincidem. Tente Novamentre.");
            alert.show();
        } else {
            this.utilizador.setPassword(labelRegistroConfirmaPass.getText());
            labelErroRegistroConfirmaPass.setText("");
        }

        if (labelTipoUtilziador.getText().isEmpty()){
            labelErroRegistroTipoUtilizador.setText("Tem de selecionar o Tipo de Utilizador no seu Registro");
        } else if (labelTipoUtilziador.getText().toLowerCase().equals("admin")){
            this.utilizador.setTipoUtilizador(TipoUtilizador.ADMIN);
            labelErroRegistroTipoUtilizador.setText("");
        } else if (labelTipoUtilziador.getText().toLowerCase().equals("funcionario")) {
            this.utilizador.setTipoUtilizador(TipoUtilizador.FUNCIONARIO);
            labelErroRegistroTipoUtilizador.setText("");
        }

        if (labelErroRegistroNome.getText().equals("") && labelErroRegistroEmail.getText().equals("") && labelErroRegistroPass.getText().equals("") &&
            labelErroRegistroConfirmaPass.getText().equals("") && labelErroRegistroTipoUtilizador.getText().equals("")) {
            this.utilizador.setEstadoUtilizador(EstadoUtilizador.PENDENTE);
            this.utilizadorDao.registrar(utilizador);
            goToLogin();
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void menuItemAdmin(ActionEvent event) {
        labelTipoUtilziador.setText(menuItemIdAdmin.getText());
    }

    @FXML
    void menuItemFuncionario(ActionEvent event) {
        labelTipoUtilziador.setText(menuItemIdFuncionario.getText());
    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {
        goToLogin();
        Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        Aqui coloca-se tipicamente açoes que quero que sejam executadas quando instancio o controlador
         */
    }

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
}
