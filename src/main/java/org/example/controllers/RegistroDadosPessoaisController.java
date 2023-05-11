package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.models.enums.EstadoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;
import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroDadosPessoaisController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    Utilizador utilizador;
    RegexDados regexDados;
    GoToUtil goToUtil;

    @FXML
    private Button btnRegistrarSeguinteId;
    @FXML
    private Hyperlink hyperlinkLoginId;
    @FXML
    private Label labelIdErroContacto;
    @FXML
    private Label labelIdErroNIF;
    @FXML
    private Label labelIdErroNomeCompleto;
    @FXML
    private Label labelIdErroNumCC;
    @FXML
    private Label labeldErroDataNasc;
    @FXML
    private TextField txtFdRegistroContactoId;
    @FXML
    private DatePicker txtFdRegistroDataNascId;
    @FXML
    private TextField txtFdRegistroNIFD;
    @FXML
    private TextField txtFdRegistroNccId;
    @FXML
    private TextField txtFdRegistroNomeCompletoId;

    @FXML
    void btnRegistrarSeguinteDD(ActionEvent event) {
        int verificaNumCC = 0;
        int verificaNIF = 0;
        int verificaContacto = 0;

        try {
            verificaNumCC = Integer.parseInt(txtFdRegistroNccId.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaNIF = Integer.parseInt(txtFdRegistroNIFD.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaContacto = Integer.parseInt(txtFdRegistroContactoId.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        if (txtFdRegistroNomeCompletoId.getText().isEmpty()) {
            labelIdErroNomeCompleto.setText("Tem de preencher o Nome Completo!");
        } else {
            this.utilizador.setNome(txtFdRegistroNomeCompletoId.getText());
            labelIdErroNomeCompleto.setText("");
        }

        if (txtFdRegistroNccId.getText().isEmpty()) {
            labelIdErroNumCC.setText("Tem de preencher o Número do CC!");
        } else if (verificaNumCC == 0) {
            labelIdErroNumCC.setText("Preencha corretamente o Número CC!");
        } else {
            this.utilizador.setNumeroCc(Integer.valueOf(txtFdRegistroNccId.getText()));
            labelIdErroNumCC.setText("");
        }

        if (txtFdRegistroNIFD.getText().isEmpty()) {
            labelIdErroNIF.setText("Tem de preencher o NIF!");
        } else if (verificaNIF == 0) {
            labelIdErroNIF.setText("Preencha corretamente o NIF!");
        } else {
            this.utilizador.setNif(Integer.valueOf(txtFdRegistroNIFD.getText()));
            labelIdErroNIF.setText("");
        }

        if (txtFdRegistroContactoId.getText().isEmpty()) {
            labelIdErroContacto.setText("Tem de preencher o Contacto!");
        } else if (verificaContacto == 0) {
            labelIdErroContacto.setText("Preencha corretamente o Contacto!");
        } else {
            this.utilizador.setContacto(Integer.valueOf(txtFdRegistroContactoId.getText()));
            labelIdErroContacto.setText("");
        }

        // TODO=> falta a data

        if (labelIdErroNomeCompleto.getText().equals("") && labelIdErroNumCC.getText().equals("") && labelIdErroNIF.getText().equals("") &&
                labelIdErroContacto.getText().equals("")) {
            this.utilizador.setEstadoUtilizador(EstadoUtilizador.PENDENTE);
            this.utilizadorDao.registrar(utilizador);
            this.goToUtil.goToRegistroDadosLocalizacao();
            Stage stage = (Stage) btnRegistrarSeguinteId.getScene().getWindow();
            stage.close();
        }
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
        this.utilizador = new Utilizador();
        this.regexDados = new RegexDados();
        this.goToUtil = new GoToUtil();
    }
}
