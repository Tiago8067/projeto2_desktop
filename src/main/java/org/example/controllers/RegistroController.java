package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    Utilizador utilizador;
    RegexDados regexDados;
    GoToUtil goToUtil;
    List<Utilizador> utilizadorList;
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
    private TextField labelRegistroConfirmaPass;
    @FXML
    private TextField labelRegistroEmail;
    @FXML
    private TextField labelRegistroNome;
    @FXML
    private TextField labelRegistroPass;

    @FXML
    void btnRegistrar(ActionEvent event) {
        if (labelRegistroNome.getText().isEmpty()) {
            labelErroRegistroNome.setText("Tem de preencher o Nome de Utilizador.");
        } else if (labelRegistroNome.getText().length() < 4) {
            labelErroRegistroNome.setText("O Nome de Utilizador tem pelo menos 4 carateres!");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(labelRegistroNome.getText()) != null) {
            labelErroRegistroNome.setText("O Nome de Utilizador já existe. Insira Outro!");
        } else {
            labelErroRegistroNome.setText("");
        }

        if (labelRegistroEmail.getText().isEmpty()) {
            labelErroRegistroEmail.setText("Tem de preencher o Email.");
        } else if (!this.regexDados.validateEmail(labelRegistroEmail.getText())) {
            labelErroRegistroEmail.setText("Por favor, preencha o Email Corretamente no formato(abc[123]@abc.abc)!");
        } else if (this.utilizadorDao.buscarUtilizadorPorEmail(labelRegistroEmail.getText()) != null) {
            labelErroRegistroEmail.setText("O Email já existe. Insira Outro!");
        } else {
            labelErroRegistroEmail.setText("");
        }

        if (labelRegistroPass.getText().isEmpty()) {
            labelErroRegistroPass.setText("Tem de preencher a Palavra-passe");
        } else if (!this.regexDados.isValidPassword(labelRegistroPass.getText())) {
            labelErroRegistroPass.setText("Por favor, preencha a Palavra-passe Corretamente no formato([123].abc)!");
        } else {
            labelErroRegistroPass.setText("");
        }

        if (labelRegistroConfirmaPass.getText().isEmpty()) {
            labelErroRegistroConfirmaPass.setText("Tem de preencher a confirmação da Palavra-passe");
        } else if (!labelRegistroPass.getText().equals(labelRegistroConfirmaPass.getText())) {
            labelErroRegistroConfirmaPass.setText("As palavras-passe não coincidem. Tente Novamente!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("As palavras-passe não coincidem. Tente Novamente!");
            alert.show();
        } else {
            labelErroRegistroConfirmaPass.setText("");
        }

        if (labelErroRegistroNome.getText().equals("") && labelErroRegistroEmail.getText().equals("") && labelErroRegistroPass.getText().equals("") &&
                labelErroRegistroConfirmaPass.getText().equals("")) {
            for (Utilizador u : this.utilizadorList) {
                if (u.getUsername() == null) {
                    this.utilizadorDao.atualizarUtilizador(u.getIdUtilizador(), labelRegistroEmail.getText(), labelRegistroNome.getText(), labelRegistroPass.getText(), String.valueOf(TipoUtilizador.FUNCIONARIO), String.valueOf(EstadoUtilizador.PENDENTE));
                }
            }
            this.goToUtil.goToLogin();
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {

        for (Utilizador u : this.utilizadorList) {
            if (u.getUsername() == null) {
                System.out.println(u);
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
        this.regexDados = new RegexDados();
        this.goToUtil = new GoToUtil();
        this.utilizadorList = this.utilizadorDao.buscarTodos();
    }
}
