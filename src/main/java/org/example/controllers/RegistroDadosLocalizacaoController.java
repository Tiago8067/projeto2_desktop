package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroDadosLocalizacaoController implements Initializable {
    @FXML
    private Button btnRegistrarSeguinteId;

    @FXML
    private Hyperlink hyperlinkLoginId;

    @FXML
    private Label labelIdErroCodP;

    @FXML
    private Label labelIdErroDistrito;

    @FXML
    private Label labelIdErroLocalidade;

    @FXML
    private Label labelIdErroNumPorta;

    @FXML
    private Label labelIdErroRua;

    @FXML
    private TextField txtFdRegistroCodPId;

    @FXML
    private TextField txtFdRegistroDiistritoId;

    @FXML
    private TextField txtFdRegistroLocalidadeId;

    @FXML
    private TextField txtFdRegistroNumPortaId;

    @FXML
    private TextField txtFdRegistroRuaId;


    EntityManager entityManager = JPAUtil.getEntityManager();
    UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
    Utilizador utilizador = new Utilizador();
    RegexDados regexDados = new RegexDados();
    GoToUtil goToUtil = new GoToUtil();

    @FXML
    void btnRegistrarSeguinteDF(ActionEvent event) {
        System.out.println(this.utilizador.getIdUtilizador());

    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {
        this.goToUtil.goToLogin();
        Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.goToUtil = new GoToUtil();
    }
}
