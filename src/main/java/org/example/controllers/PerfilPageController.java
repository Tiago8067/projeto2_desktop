package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.models.enums.TipoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;
import org.example.util.Verificacoes;

import javax.persistence.EntityManager;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class PerfilPageController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    GoToUtil goToUtil;
    RegexDados regexDados;
    String guardaUsernameLogin = LoginController.usernameGuardado;
    private static String guardaCaminhoImagemPerfil;
    Verificacoes verificacoes;

    @FXML
    private TextField txtPerfilUsername;
    @FXML
    private Button idVoltar;
    @FXML
    private Button idAtualizar;
    @FXML
    private Button idSelecionarImagem;
    @FXML
    private TextField txtPerfilCodPostal;
    @FXML
    private TextField txtPerfilContacto;
    @FXML
    private DatePicker txtPerfilDataNasc;
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
    private ImageView idImagemPerfil;

    @FXML
    void ativaEditarDadosPerfil(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem a certeza que quer Editar os seus Dados?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            txtPerfilNome.setEditable(true);
            txtPerfilNCC.setEditable(true);
            txtPerfilNIF.setEditable(true);
            txtPerfilContacto.setEditable(true);
            txtPerfilLocalidade.setEditable(true);
            txtPerfilRua.setEditable(true);
            txtPerfilCodPostal.setEditable(true);
            txtPerfilNPorta.setEditable(true);
            txtPerfilDataNasc.setEditable(true);
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

        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(guardaUsernameLogin)) {
                if (txtPerfilNome.getText().isEmpty() || txtPerfilNCC.getText().isEmpty()
                        || txtPerfilNIF.getText().isEmpty() || txtPerfilContacto.getText().isEmpty()
                        || txtPerfilLocalidade.getText().isEmpty() || txtPerfilRua.getText().isEmpty()
                        || txtPerfilCodPostal.getText().isEmpty() || txtPerfilNPorta.getText().isEmpty()
                        || this.verificacoes.verficaInteiro(verificaContacto, txtPerfilContacto.getText()) == 0
                        || this.verificacoes.verficaInteiro(verificaNumCC, txtPerfilNCC.getText()) == 0
                        || this.verificacoes.verficaInteiro(verificaNIF, txtPerfilNIF.getText()) == 0
                        || !this.regexDados.isValidCP(txtPerfilCodPostal.getText())
                        || this.verificacoes.verficaInteiro(verificaNumPorta, txtPerfilNPorta.getText()) == 0
                        || txtPerfilDataNasc.getValue() == null) {
                    lancarErroAtualizacao("Realizou alguma edição nos seus Dados do Perfil Errado! Verifique Corretamente.");
                } else {
                    u.setNome(txtPerfilNome.getText());
                    u.setNumeroCc(Integer.valueOf(txtPerfilNCC.getText()));
                    u.setNif(Integer.valueOf(txtPerfilNIF.getText()));
                    u.setContacto(Integer.valueOf(txtPerfilContacto.getText()));
                    u.getLocalizacao().setLocalidade(txtPerfilLocalidade.getText());
                    u.getLocalizacao().setRua(txtPerfilRua.getText());
                    u.getLocalizacao().setCodigoPostal(txtPerfilCodPostal.getText());
                    u.getLocalizacao().setNumeroPorta(Integer.valueOf(txtPerfilNPorta.getText()));
                    u.setImagemPerfil(guardaCaminhoImagemPerfil);
                    u.setDataNascimento(txtPerfilDataNasc.getValue().toString());

                    this.utilizadorDao.registrar(u);

                    depoisDeAtualizarVoltaBloqueado();
                }
            }
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(guardaUsernameLogin)) {
               if (u.getTipoUtilizador().equals(TipoUtilizador.ADMIN)) {
                   this.goToUtil.goToHomePageAdmin();
                   Stage stage = (Stage) idVoltar.getScene().getWindow();
                   stage.close();
               } else {
                   this.goToUtil.goToHomePageFuncionario();
                   Stage stage = (Stage) idVoltar.getScene().getWindow();
                   stage.close();
               }
            }
        }
    }

    @FXML
    void selecionarImagemPerfil(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"), new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png"));
        Stage stage = (Stage) idSelecionarImagem.getScene().getWindow();
        File ficheiroSelecionado = fileChooser.showOpenDialog(stage);

        if (ficheiroSelecionado != null) {
            guardaCaminhoImagemPerfil = ficheiroSelecionado.getPath();
            Image image = new Image(ficheiroSelecionado.getPath());
            idImagemPerfil.setImage(image);
        } else {
            System.out.println("Nenhum Ficheiro foi Selecionado!");
        }
    }

    @FXML
    void verficaValorteste3(KeyEvent event) {
        final StringConverter<LocalDate> defaultConverter = txtPerfilDataNasc.getConverter();
        txtPerfilDataNasc.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate value) {
                try {
                    return defaultConverter.toString(value);
                } catch (DateTimeParseException ex) {
                    lancarErroAtualizacao("Realizou alguma edição nos seus Dados do Perfil Errado! Verifique Corretamente.");
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
                    lancarErroAtualizacao("Realizou alguma edição nos seus Dados do Perfil Errado! Verifique Corretamente.");
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
        this.goToUtil = new GoToUtil();
        this.regexDados = new RegexDados();
        this.verificacoes = new Verificacoes();
    }

    public void retornaUsernameLogin() {
        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(guardaUsernameLogin)) {
                txtPerfilNome.setText(u.getNome());
                txtPerfilUsername.setText(u.getUsername());
                txtPerfilNCC.setText(String.valueOf(u.getNumeroCc()));
                txtPerfilNIF.setText(String.valueOf(u.getNif()));
                txtPerfilContacto.setText(String.valueOf(u.getContacto()));
                txtPerfilEmail.setText(u.getEmail());
                txtPerfilLocalidade.setText(u.getLocalizacao().getLocalidade());
                txtPerfilRua.setText(u.getLocalizacao().getRua());
                txtPerfilCodPostal.setText(u.getLocalizacao().getCodigoPostal());
                txtPerfilNPorta.setText(String.valueOf(u.getLocalizacao().getNumeroPorta()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(u.getDataNascimento(), formatter);
                txtPerfilDataNasc.setValue(date);
                if (u.getImagemPerfil() != null) {
                    Image image = new Image(u.getImagemPerfil());
                    idImagemPerfil.setImage(image);
                }
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
        txtPerfilNCC.setEditable(false);
        txtPerfilNIF.setEditable(false);
        txtPerfilContacto.setEditable(false);
        txtPerfilLocalidade.setEditable(false);
        txtPerfilRua.setEditable(false);
        txtPerfilCodPostal.setEditable(false);
        txtPerfilNPorta.setEditable(false);
        txtPerfilDataNasc.setEditable(false);
        idAtualizar.setDisable(true);
        idSelecionarImagem.setDisable(true);
    }
}
