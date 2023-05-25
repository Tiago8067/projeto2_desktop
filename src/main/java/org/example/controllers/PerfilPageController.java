package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PerfilPageController implements Initializable {
    private String usernameLogin;
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    GoToUtil goToUtil;

    @FXML
    private Button idVoltar;
    @FXML
    private TextField txtPerfilCidade;
    @FXML
    private TextField txtPerfilCodPostal;
    @FXML
    private TextField txtPerfilContacto;
    @FXML
    private TextField txtPerfilDataNasc;
    @FXML
    private TextField txtPerfilEmail;
    @FXML
    private TextField txtPerfilLocalidade;
    @FXML
    private TextField txtPerfilNCC;
    @FXML
    private TextField txtPerfilNIF;
    @FXML
    private TextField txtPerfilNPorta;
    @FXML
    private TextField txtPerfilNome;
    @FXML
    private TextField txtPerfilRua;
    @FXML
    private TextField txtPerfilUsername;

    @FXML
    void ativaEditarDadosPerfil(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem a certeza que quer Editar os seus Dados?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            txtPerfilNome.setEditable(true);
            //txtPerfilDataNasc.setEditable(true);
            txtPerfilNCC.setEditable(true);
            txtPerfilNIF.setEditable(true);
            txtPerfilContacto.setEditable(true);
            txtPerfilCidade.setEditable(true);
            txtPerfilLocalidade.setEditable(true);
            txtPerfilRua.setEditable(true);
            txtPerfilCidade.setEditable(true);
            txtPerfilCodPostal.setEditable(true);
            txtPerfilNPorta.setEditable(true);
        }
    }

    @FXML
    void btnAtualizar(ActionEvent event) {

    }

    @FXML
    void btnVoltar(ActionEvent event) {
        this.goToUtil.goToHomePageAdmin();
        Stage stage = (Stage) idVoltar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.goToUtil = new GoToUtil();
    }

    public String getUsernameLogin() {
        return usernameLogin;
    }

    public void setUsernameLogin(String usernameLogin) {
        this.usernameLogin = usernameLogin;
    }

    public void retornaUsernameLogin(String username) {
        setUsernameLogin(username);

        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(getUsernameLogin())) {
                txtPerfilNome.setText(u.getNome());
                txtPerfilUsername.setText(u.getUsername());
                //txtPerfilDataNasc.setText(u.getDataNascimento());
                txtPerfilNCC.setText(String.valueOf(u.getNumeroCc()));
                txtPerfilNIF.setText(String.valueOf(u.getNif()));
                txtPerfilContacto.setText(String.valueOf(u.getContacto()));
                txtPerfilEmail.setText(u.getEmail());
                txtPerfilCidade.setText(u.getLocalizacao().getCidade());
                txtPerfilLocalidade.setText(u.getLocalizacao().getLocalidade());
                txtPerfilRua.setText(u.getLocalizacao().getRua());
                txtPerfilCidade.setText(u.getLocalizacao().getCidade());
                txtPerfilCodPostal.setText(u.getLocalizacao().getCodigoPostal());
                txtPerfilNPorta.setText(String.valueOf(u.getLocalizacao().getNumeroPorta()));
            }
        }
    }
}
