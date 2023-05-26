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
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PerfilPageController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    GoToUtil goToUtil;
    RegexDados regexDados;
    LoginController loginController = new LoginController();
    String guardaUsernameLogin = LoginController.usernameGuardado;

    @FXML
    private TextField txtPerfilUsername;
    @FXML
    private Button idVoltar;
    @FXML
    private Button idAtualizar;
    @FXML
    private Button idSelecionarImagem;
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
            idAtualizar.setDisable(false);
            idSelecionarImagem.setDisable(false);
        }
    }

    @FXML
    void btnAtualizar(ActionEvent event) {
        int verificaContacto = 0;
        int verificaNumPorta = 0;
        int verificaNumCC = 0;
        int verificaNIF = 0;

        try {
            verificaNumPorta = Integer.parseInt(txtPerfilNPorta.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaContacto = Integer.parseInt(txtPerfilContacto.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaNumCC = Integer.parseInt(txtPerfilNCC.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaNIF = Integer.parseInt(txtPerfilNIF.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(guardaUsernameLogin)) {
                if (txtPerfilNome.getText().isEmpty() || txtPerfilNCC.getText().isEmpty() || txtPerfilNIF.getText().isEmpty() || txtPerfilContacto.getText().isEmpty()
                        || txtPerfilCidade.getText().isEmpty() || txtPerfilLocalidade.getText().isEmpty() || txtPerfilRua.getText().isEmpty()
                        || txtPerfilCodPostal.getText().isEmpty() || txtPerfilNPorta.getText().isEmpty()
                        || verificaNumCC == 0 || verificaNIF == 0 || verificaContacto == 0
                        || !this.regexDados.isValidCP(txtPerfilCodPostal.getText()) || verificaNumPorta == 0) {
                    lancarErroAtualizacao("Realizou alguma edição nos seus Dados do Perfil Errado! Verifique Corretamente.");
                } else {
                    u.setNome(txtPerfilNome.getText());
                    u.setNumeroCc(Integer.valueOf(txtPerfilNCC.getText()));
                    u.setNif(Integer.valueOf(txtPerfilNIF.getText()));
                    u.setContacto(Integer.valueOf(txtPerfilContacto.getText()));
                    u.getLocalizacao().setCidade(txtPerfilCidade.getText());
                    u.getLocalizacao().setLocalidade(txtPerfilLocalidade.getText());
                    u.getLocalizacao().setRua(txtPerfilRua.getText());
                    u.getLocalizacao().setCodigoPostal(txtPerfilCodPostal.getText());
                    u.getLocalizacao().setNumeroPorta(Integer.valueOf(txtPerfilNPorta.getText()));

                    this.utilizadorDao.registrar(u);

                    depoisDeAtualizarVoltaBloqueado();
                }
            }
        }
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
        this.regexDados = new RegexDados();
    }

    public void retornaUsernameLogin() {
        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(guardaUsernameLogin)) {
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

    private void lancarErroAtualizacao(String erro) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(erro);
        alert.setResizable(true);
        alert.getDialogPane().setMinWidth(500);
        alert.show();
    }

    private void depoisDeAtualizarVoltaBloqueado() {
        txtPerfilNome.setEditable(false);
        //txtPerfilDataNasc.setEditable(true);
        txtPerfilNCC.setEditable(false);
        txtPerfilNIF.setEditable(false);
        txtPerfilContacto.setEditable(false);
        txtPerfilCidade.setEditable(false);
        txtPerfilLocalidade.setEditable(false);
        txtPerfilRua.setEditable(false);
        txtPerfilCidade.setEditable(false);
        txtPerfilCodPostal.setEditable(false);
        txtPerfilNPorta.setEditable(false);
        idAtualizar.setDisable(true);
        idSelecionarImagem.setDisable(true);
    }
}
