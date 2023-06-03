package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dao.LocalizacaoDao;
import org.example.dao.UtilizadorDao;
import org.example.models.Localizacao;
import org.example.models.Utilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;
import org.example.util.Verificacoes;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistroDadosLocalizacaoController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    Utilizador utilizador;
    Localizacao localizacao;
    LocalizacaoDao localizacaoDao;
    RegexDados regexDados;
    GoToUtil goToUtil;
    Verificacoes verificacoes;
    List<Utilizador> utilizadorList;

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

    @FXML
    void btnRegistrarSeguinteDF(ActionEvent event) {
        int verificaNumPorta = 0;

        if (txtFdRegistroDiistritoId.getText().isEmpty()) {
            labelIdErroDistrito.setText("Tem de preencher o Distrito!");
        } else {
            this.localizacao.setCidade(txtFdRegistroDiistritoId.getText());
            labelIdErroDistrito.setText("");
        }

        if (txtFdRegistroCodPId.getText().isEmpty()) {
            labelIdErroCodP.setText("Tem de preencher o Código Postal!");
        } else if (!this.regexDados.isValidCP(txtFdRegistroCodPId.getText())) {
            labelIdErroCodP.setText("Preencha corretamente o Código Postal no formato(XXXX-YYY)!");
        } else {
            this.localizacao.setCodigoPostal(txtFdRegistroCodPId.getText());
            labelIdErroCodP.setText("");
        }

        if (txtFdRegistroLocalidadeId.getText().isEmpty()) {
            labelIdErroLocalidade.setText("Tem de preencher a Localidade!");
        } else {
            this.localizacao.setLocalidade(txtFdRegistroLocalidadeId.getText());
            labelIdErroLocalidade.setText("");
        }

        if (txtFdRegistroRuaId.getText().isEmpty()) {
            labelIdErroRua.setText("Tem de preencher a Rua!");
        } else {
            this.localizacao.setRua(txtFdRegistroRuaId.getText());
            labelIdErroRua.setText("");
        }

        if (txtFdRegistroNumPortaId.getText().isEmpty()) {
            labelIdErroNumPorta.setText("Tem de preencher o Número da Porta!");
        } else if (this.verificacoes.verficaInteiro(verificaNumPorta, txtFdRegistroNumPortaId.getText()) == 0) {
            labelIdErroNumPorta.setText("Preencha corretamente o Número da Porta!");
        } else {
            this.localizacao.setNumeroPorta(Integer.valueOf(txtFdRegistroNumPortaId.getText()));
            labelIdErroNumPorta.setText("");
        }

        for (Utilizador u : this.utilizadorList) {
            if (u.getUsername() == null) {
                u.setLocalizacao(this.localizacao);
                if (labelIdErroDistrito.getText().equals("") && labelIdErroCodP.getText().equals("") && labelIdErroLocalidade.getText().equals("") &&
                        labelIdErroRua.getText().equals("") && labelIdErroNumPorta.getText().equals("")) {
                    this.localizacaoDao.registrar(this.localizacao);
                    this.utilizadorDao.registrar(u);
                    this.goToUtil.goToRegistro();
                    Stage stage = (Stage) btnRegistrarSeguinteId.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {
        for (Utilizador u : this.utilizadorList) {
            if (u.getUsername() == null) {
                this.utilizadorDao.remover(u);
            }
        }

        this.goToUtil.goToLogin();
        Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.utilizador = new Utilizador();
        this.localizacao = new Localizacao();
        this.localizacaoDao = new LocalizacaoDao(entityManager);
        this.regexDados = new RegexDados();
        this.goToUtil = new GoToUtil();
        utilizadorList = this.utilizadorDao.buscarTodos();
        this.verificacoes = new Verificacoes();
    }
}
