package org.example.controllers.editar;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.dao.LocalizacaoDao;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
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

public class EditarClienteController implements Initializable {
    EntityManager entityManager;
    Utilizador utilizador;
    UtilizadorDao utilizadorDao;
    GoToUtil goToUtil;
    RegexDados regexDados;
    LocalizacaoDao localizacaoDao;
    Verificacoes verificacoes;
    private static String guardaCaminhoImagemPerfil;

    @FXML
    private Button idAtualizar;
    @FXML
    private ImageView idImagemPerfil;
    @FXML
    private Button idSelecionarImagem;
    @FXML
    private Button idVoltar;
    @FXML
    private TextField txtPerfilCidade;
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
    private TextField txtPerfilUsername;

    @FXML
    void ativaEditarDadosPerfil(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem a certeza que quer Editar os Dados do Cliente?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            txtPerfilNome.setEditable(true);
            txtPerfilNCC.setEditable(true);
            txtPerfilNIF.setEditable(true);
            txtPerfilContacto.setEditable(true);
            txtPerfilCidade.setEditable(true);
            txtPerfilLocalidade.setEditable(true);
            txtPerfilRua.setEditable(true);
            txtPerfilCidade.setEditable(true);
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

        if (txtPerfilNome.getText().isEmpty() || txtPerfilNCC.getText().isEmpty() || txtPerfilNIF.getText().isEmpty() || txtPerfilContacto.getText().isEmpty()
                || txtPerfilCidade.getText().isEmpty() || txtPerfilLocalidade.getText().isEmpty() || txtPerfilRua.getText().isEmpty()
                || txtPerfilCodPostal.getText().isEmpty() || txtPerfilNPorta.getText().isEmpty()
                || this.verificacoes.verficaInteiro(verificaContacto, txtPerfilContacto.getText()) == 0
                || this.verificacoes.verficaInteiro(verificaNumCC, txtPerfilNCC.getText()) == 0
                || this.verificacoes.verficaInteiro(verificaNIF, txtPerfilNIF.getText()) == 0
                || !this.regexDados.isValidCP(txtPerfilCodPostal.getText())
                || this.verificacoes.verficaInteiro(verificaNumPorta, txtPerfilNPorta.getText()) == 0
                || txtPerfilDataNasc.getValue() == null) {
            lancarErroAtualizacaoCliente("Realizou alguma edição nos Dados do Perfil do Cliente Errado! Verifique Corretamente.");
        } else {
            this.utilizador.setNome(txtPerfilNome.getText());
            this.utilizador.setNumeroCc(Integer.valueOf(txtPerfilNCC.getText()));
            this.utilizador.setNif(Integer.valueOf(txtPerfilNIF.getText()));
            this.utilizador.setContacto(Integer.valueOf(txtPerfilContacto.getText()));
            this.utilizador.getLocalizacao().setCidade(txtPerfilCidade.getText());
            this.utilizador.getLocalizacao().setLocalidade(txtPerfilLocalidade.getText());
            this.utilizador.getLocalizacao().setRua(txtPerfilRua.getText());
            this.utilizador.getLocalizacao().setCodigoPostal(txtPerfilCodPostal.getText());
            this.utilizador.getLocalizacao().setNumeroPorta(Integer.valueOf(txtPerfilNPorta.getText()));
            this.utilizador.setImagemPerfil(guardaCaminhoImagemPerfil);
            this.utilizador.setDataNascimento(txtPerfilDataNasc.getValue().toString());

            this.utilizadorDao.atualizar(this.utilizador);

            depoisDeAtualizaBloqueiaDados();
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        this.goToUtil.goToHomePageAdminDeTabClientes();
        Stage stage = (Stage) idVoltar.getScene().getWindow();
        stage.close();
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
    void verficaValorCliente(KeyEvent event) {
        final StringConverter<LocalDate> defaultConverter = txtPerfilDataNasc.getConverter();
        txtPerfilDataNasc.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate value) {
                try {
                    return defaultConverter.toString(value);
                } catch (DateTimeParseException ex) {
                    lancarErroAtualizacaoCliente("Realizou alguma edição nos Dados do Perfil do Cliente Errado! Verifique Corretamente.");
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
                    lancarErroAtualizacaoCliente("Realizou alguma edição nos Dados do Perfil do Cliente Errado! Verifique Corretamente.");
                    System.err.println("HelloDatePicker: " + ex.getMessage());
                    return null;
                }
            }
        });
    }

    public void setCliente(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(this.entityManager);
        this.localizacaoDao = new LocalizacaoDao(this.entityManager);
        this.goToUtil = new GoToUtil();
        this.regexDados = new RegexDados();
        this.verificacoes = new Verificacoes();
    }

    public void passarDadosClienteEditar() {
        txtPerfilNome.setText(this.utilizador.getNome());
        txtPerfilUsername.setText(this.utilizador.getUsername());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(this.utilizador.getDataNascimento(), formatter);
        txtPerfilDataNasc.setValue(date);
        txtPerfilNCC.setText(String.valueOf(this.utilizador.getNumeroCc()));
        txtPerfilNIF.setText(String.valueOf(this.utilizador.getNif()));
        txtPerfilContacto.setText(String.valueOf(this.utilizador.getContacto()));
        txtPerfilEmail.setText(this.utilizador.getEmail());
        txtPerfilCidade.setText(this.utilizador.getLocalizacao().getCidade());
        txtPerfilLocalidade.setText(this.utilizador.getLocalizacao().getLocalidade());
        txtPerfilRua.setText(this.utilizador.getLocalizacao().getRua());
        txtPerfilCodPostal.setText(this.utilizador.getLocalizacao().getCodigoPostal());
        txtPerfilNPorta.setText(String.valueOf(this.utilizador.getLocalizacao().getNumeroPorta()));
        if (this.utilizador.getImagemPerfil() != null) {
            Image image = new Image(this.utilizador.getImagemPerfil());
            idImagemPerfil.setImage(image);
        }
    }

    private void lancarErroAtualizacaoCliente(String erro) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(erro);
        alert.setResizable(true);
        alert.getDialogPane().setMinWidth(500);
        alert.show();
    }

    private void depoisDeAtualizaBloqueiaDados() {
        txtPerfilNome.setEditable(false);
        txtPerfilNCC.setEditable(false);
        txtPerfilNIF.setEditable(false);
        txtPerfilContacto.setEditable(false);
        txtPerfilCidade.setEditable(false);
        txtPerfilLocalidade.setEditable(false);
        txtPerfilRua.setEditable(false);
        txtPerfilCidade.setEditable(false);
        txtPerfilCodPostal.setEditable(false);
        txtPerfilNPorta.setEditable(false);
        txtPerfilDataNasc.setEditable(false);
        idAtualizar.setDisable(true);
        idSelecionarImagem.setDisable(true);
    }
}
