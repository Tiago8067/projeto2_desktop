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
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;
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
    private TextField tfLoginNome;

    @FXML
    private TextField tfLoginPass;

    EntityManager entityManager = JPAUtil.getEntityManager();
    UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
    Utilizador utilizador = new Utilizador();

    @FXML
    void btnLogin(ActionEvent event) {
        if (tfLoginNome.getText().isEmpty()) {
            labelErroLoginNomeEmail.setText("Tem de inserir o seu Nome de Utilizador para realizar o Login");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()) == null) {
            labelErroLoginNomeEmail.setText("O Nome de Utilizador não existe. Insira o seu Nome de Utilizador.");
        } else {
            labelErroLoginNomeEmail.setText("");
        }

        //System.out.println(this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getPassword());

        if (tfLoginPass.getText().isEmpty()) {
            labelErroLoginPass.setText("Tem de inserir a sua Palavra-passe para realizar o Login");
        } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getPassword().equals(tfLoginPass.getText())) {
            labelErroLoginPass.setText("A Palavra-passe está Incorreta. Tente Novamente.");
        } else {
            labelErroLoginPass.setText("");
        }

        //System.out.println(this.utilizadorDao.buscarUtilizadorPorUsername(labelLoginNomeEmail.getText()));

        //System.out.println(this.utilizadorDao.buscarUtilizadorPorUsername(labelLoginNomeEmail.getText()));

        //System.out.println(this.utilizadorDao.buscarUtilizador(labelLoginNomeEmail.getText()));

        //if (labelLoginPass.getText().isEmpty()) {
        //    labelErroLoginPass.setText("Tem de inserir a sua Palavra-passe para realizar o Login");
        //}

        //System.out.println(new UtilizadorDao().buscarUtilizador(labelLoginPass.getText()));



        if (labelErroLoginNomeEmail.getText().equals("") && labelErroLoginPass.getText().equals("")){
            if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getEstadoUtilizador().equals(EstadoUtilizador.PENDENTE)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().setPrefSize(450, 150);
                alert.setContentText("O seu Estado está Pendente. Aguarde pela aprovação de um admin!");
                alert.show();
            } else if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getEstadoUtilizador().equals(EstadoUtilizador.INATIVO)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().setPrefSize(450, 150);
                alert.setContentText("O seu Estado está Inativo. Á espera de moderação de um admin!");
                alert.show();
            } else if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getEstadoUtilizador().equals(EstadoUtilizador.ATIVO)) {
                if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getTipoUtilizador().equals(TipoUtilizador.ADMIN)) {
                    goToHomePageAdmin();
                    Stage stage = (Stage) btnLoginId.getScene().getWindow();
                    stage.close();
                }

                if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getTipoUtilizador().equals(TipoUtilizador.FUNCIONARIO)){
                    goToHomePageFuncionario();
                    Stage stage = (Stage) btnLoginId.getScene().getWindow();
                    stage.close();
                }
            }
        }
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
