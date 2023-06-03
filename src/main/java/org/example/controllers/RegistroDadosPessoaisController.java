package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.models.enums.EstadoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;
import org.example.util.SecureLocalDateStringConverter;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class RegistroDadosPessoaisController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    Utilizador utilizador;
    RegexDados regexDados;
    GoToUtil goToUtil;
    SecureLocalDateStringConverter secureLocalDateStringConverter;

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

        if (txtFdRegistroDataNascId.getValue() == null) {
            labeldErroDataNasc.setText("Tem de preencher a Data de Nascimento.");
        } else {
            this.utilizador.setDataNascimento(txtFdRegistroDataNascId.getValue().toString());
            labeldErroDataNasc.setText("");
        }

        if (labelIdErroNomeCompleto.getText().equals("") && labelIdErroNumCC.getText().equals("") && labelIdErroNIF.getText().equals("") && labelIdErroContacto.getText().equals("") && labeldErroDataNasc.getText().equals("")) {
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

    @FXML
    void verficaValorteste3(KeyEvent event) {
        final StringConverter<LocalDate> defaultConverter = txtFdRegistroDataNascId.getConverter();
        txtFdRegistroDataNascId.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate value) {
                try {
                    return defaultConverter.toString(value);
                } catch (DateTimeParseException ex) {
                    labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato (dd/MM/yyyy)!");
                    System.err.println("HelloDatePicker: " + ex.getMessage());
                    throw ex;
                }
            }

            @Override
            public LocalDate fromString(String text) {
                try {
                    if (text == null || text.trim().isEmpty()) {
                        return null;
                    } else {
                        return defaultConverter.fromString(text);
                    }

                } catch (DateTimeParseException ex) {
                    labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato (dd/MM/yyyy)!");
                    System.err.println("HelloDatePicker: " + ex.getMessage());
                    return null;
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.utilizador = new Utilizador();
        this.regexDados = new RegexDados();
        this.goToUtil = new GoToUtil();
        this.secureLocalDateStringConverter = new SecureLocalDateStringConverter();
    }
}
