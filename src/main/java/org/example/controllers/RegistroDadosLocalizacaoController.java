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
import org.example.models.enums.EstadoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    Localizacao localizacao = new Localizacao();
    LocalizacaoDao localizacaoDao = new LocalizacaoDao(entityManager);
    RegexDados regexDados = new RegexDados();
    GoToUtil goToUtil = new GoToUtil();
    RegistroDadosPessoaisController registroDadosPessoaisController;

    @FXML
    void btnRegistrarSeguinteDF(ActionEvent event) {
        int verificaNumPorta = 0;

        try {
            verificaNumPorta = Integer.parseInt(txtFdRegistroNumPortaId.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        if (txtFdRegistroDiistritoId.getText().isEmpty()){
            labelIdErroDistrito.setText("Tem de preencher o Distrito no seu Registro");
        } else {
            this.localizacao.setCidade(txtFdRegistroDiistritoId.getText());
            labelIdErroDistrito.setText("");
        }

        if (txtFdRegistroCodPId.getText().isEmpty()){
            labelIdErroCodP.setText("Tem de preencher o Código Postal no seu Registro");
        } else if (!this.regexDados.isValidCP(txtFdRegistroCodPId.getText())) {
            labelIdErroCodP.setText("Por favor, preencha o Código Postal Corretamente");
        } else {
            this.localizacao.setCodigoPostal(txtFdRegistroCodPId.getText());
            labelIdErroCodP.setText("");
        }

        if (txtFdRegistroLocalidadeId.getText().isEmpty()){
            labelIdErroLocalidade.setText("Tem de preencher a Localidade no seu Registro");
        } else {
            this.localizacao.setLocalidade(txtFdRegistroLocalidadeId.getText());
            labelIdErroLocalidade.setText("");
        }

        if (txtFdRegistroRuaId.getText().isEmpty()){
            labelIdErroRua.setText("Tem de preencher a Rua no seu Registro");
        } else {
            this.localizacao.setRua(txtFdRegistroRuaId.getText());
            labelIdErroRua.setText("");
        }

        if (txtFdRegistroNumPortaId.getText().isEmpty()){
            labelIdErroNumPorta.setText("Tem de preencher o Número da Porta no seu Registro");
        } else if (verificaNumPorta == 0) {
            labelIdErroNumPorta.setText("Preencha corretamente o Número da Porta no seu Registro");
        } else {
            this.localizacao.setNumeroPorta(Integer.valueOf(txtFdRegistroNumPortaId.getText()));
            labelIdErroNumPorta.setText("");
        }

        List<Utilizador> utilizadorList = this.utilizadorDao.buscarTodos();

        for (Utilizador u: utilizadorList) {
            if (u.getIdUtilizador() == utilizadorList.size()) {
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

        /*List<Localizacao> localizacaoList = this.localizacaoDao.buscarTodos();

        for (Localizacao l: localizacaoList) {
            if (l.getCidade() != null || l.getCodigoPostal() != null || l.getLocalidade() != null || l.getRua() != null || l.getNumeroPorta() != null) {
                this.localizacaoDao.remover(l);
                this.goToUtil.goToLogin();
                Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
                stage.close();
            } else {
                this.goToUtil.goToLogin();
                Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
                stage.close();
            }
        }*/

        List<Utilizador> utilizadorList = this.utilizadorDao.buscarTodos();

        for (Utilizador u: utilizadorList) {
            //if (u.getNome() != null && u.getNumeroCc() != null && u.getNif() != null && u.getContacto() != null) {
            if (u.getIdUtilizador() == utilizadorList.size()) {
                // todo ir buscar username 
                if (u.getNome() != null && u.getNumeroCc() != null && u.getNif() != null && u.getContacto() != null) {
                    //System.out.println(u);
                    this.utilizadorDao.remover(u);
                }
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
        this.goToUtil = new GoToUtil();
    }
}
