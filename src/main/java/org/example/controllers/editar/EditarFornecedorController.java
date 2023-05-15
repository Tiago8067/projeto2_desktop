package org.example.controllers.editar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.FornecedorDao;
import org.example.dao.LocalizacaoDao;
import org.example.models.Fornecedor;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;
import javax.persistence.EntityManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditarFornecedorController implements Initializable {
    EntityManager entityManager;
    Fornecedor fornecedor;
    FornecedorDao forncedorDao;
    GoToUtil goToUtil;
    RegexDados regexDados;
    LocalizacaoDao localizacaoDao;
    @FXML
    private Button btnIdApagar;
    @FXML
    private Button btnIdAtualizar;
    @FXML
    private Button btnIdVoltar;
    @FXML
    private Label labelIdErroContacto;
    @FXML
    private Label labelIdErroCp;
    @FXML
    private Label labelIdErroDistrito;
    @FXML
    private Label labelIdErroLocalidade;
    @FXML
    private Label labelIdErroN_Porta;
    @FXML
    private Label labelIdErroNome;
    @FXML
    private Label labelIdErroRua;
    @FXML
    private TextField txtFdIdContacto;
    @FXML
    private TextField txtFdIdCp;
    @FXML
    private TextField txtFdIdDistrito;
    @FXML
    private TextField txtFdIdLocalidade;
    @FXML
    private TextField txtFdIdN_Porta;
    @FXML
    private TextField txtFdIdNome;
    @FXML
    private TextField txtFdIdRua;

    @FXML
    void btnApagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem a certeza que quer Eliminar este Fornecedor?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            this.forncedorDao.remover(this.fornecedor);
            this.localizacaoDao.removerPeloFornecedor(this.fornecedor.getLocalizacao());
            this.goToUtil.goToHomePageAdmin();
            Stage stage = (Stage) btnIdApagar.getScene().getWindow();
            stage.close();
        } else {
            return;
        }
    }

    @FXML
    void btnAtualizar(ActionEvent event) {
        int verificaNumPorta = 0;
        int verificaContacto = 0;

        try {
            verificaNumPorta = Integer.parseInt(txtFdIdN_Porta.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaContacto = Integer.parseInt(txtFdIdContacto.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        if (txtFdIdNome.getText().isEmpty()){
            labelIdErroNome.setText("Tem de preencher o Nome do Fornecedor");
        } else if (txtFdIdNome.getText().length() < 4) {
            labelIdErroNome.setText("O Nome do Fornecedor tem pelo menos 4 carateres!");
        } else if (this.forncedorDao.buscarFornecedorPorNome(txtFdIdNome.getText()) != null) {
            labelIdErroNome.setText("O Nome do Fornecedor já existe. Insira Outro.");
        } else {
            this.fornecedor.setNome(txtFdIdNome.getText());
            labelIdErroNome.setText("");
        }

        if (txtFdIdContacto.getText().isEmpty()) {
            labelIdErroContacto.setText("Tem de preencher o Contacto do Fornecedor");
        } else if (verificaContacto == 0) {
            labelIdErroContacto.setText("Preencha corretamente o Contacto do Fornecedor");
        } else {
            this.fornecedor.setContacto(Integer.valueOf(txtFdIdContacto.getText()));
            labelIdErroContacto.setText("");
        }

        if (txtFdIdDistrito.getText().isEmpty()){
            labelIdErroDistrito.setText("Tem de preencher o Distrito do Fornecedor");
        } else {
            this.fornecedor.getLocalizacao().setCidade(txtFdIdDistrito.getText());
            labelIdErroDistrito.setText("");
        }

        if (txtFdIdLocalidade.getText().isEmpty()){
            labelIdErroLocalidade.setText("Tem de preencher a Localidade do Fornecedor");
        } else {
            this.fornecedor.getLocalizacao().setLocalidade(txtFdIdLocalidade.getText());
            labelIdErroLocalidade.setText("");
        }

        if (txtFdIdRua.getText().isEmpty()){
            labelIdErroRua.setText("Tem de preencher a Rua do Fornecedor");
        } else {
            this.fornecedor.getLocalizacao().setRua(txtFdIdRua.getText());
            labelIdErroRua.setText("");
        }

        if (txtFdIdCp.getText().isEmpty()){
            labelIdErroCp.setText("Tem de preencher o Código Postal do Fornecedor");
        } else if (!this.regexDados.isValidCP(txtFdIdCp.getText())) {
            labelIdErroCp.setText("Preencha corretamente o Código Postal do Fornecedor no formato(XXXX-YYY)");
        } else {
            this.fornecedor.getLocalizacao().setCodigoPostal(txtFdIdCp.getText());
            labelIdErroCp.setText("");
        }

        if (txtFdIdN_Porta.getText().isEmpty()){
            labelIdErroN_Porta.setText("Tem de preencher o Número da Porta do Fornecedor");
        } else if (verificaNumPorta == 0) {
            labelIdErroN_Porta.setText("Preencha corretamente o Número da Porta do Fornecedor");
        } else {
            this.fornecedor.getLocalizacao().setNumeroPorta(Integer.valueOf(txtFdIdN_Porta.getText()));
            labelIdErroN_Porta.setText("");
        }

        if (labelIdErroNome.getText().equals("") && labelIdErroContacto.getText().equals("") && labelIdErroDistrito.getText().equals("")
                && labelIdErroLocalidade.getText().equals("") && labelIdErroRua.getText().equals("") && labelIdErroCp.getText().equals("") && labelIdErroN_Porta.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Tem a certeza que deseja Editar este Fornecedor?");
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.get() == ButtonType.OK) {
                this.localizacaoDao.atualizar(this.fornecedor.getLocalizacao());
                this.forncedorDao.atualizar(this.fornecedor);
                this.goToUtil.goToHomePageAdmin();
                Stage stage = (Stage) btnIdAtualizar.getScene().getWindow();
                stage.close();
            } else {
                return;
            }
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        this.goToUtil.goToHomePageAdmin();
        Stage stage = (Stage) btnIdVoltar.getScene().getWindow();
        stage.close();
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.forncedorDao = new FornecedorDao(this.entityManager);
        this.localizacaoDao = new LocalizacaoDao(this.entityManager);
        this.goToUtil = new GoToUtil();
        this.regexDados = new RegexDados();
    }

    public void passarDadosFornecedorEditar() {
        txtFdIdNome.setText(this.fornecedor.getNome());
        txtFdIdDistrito.setText(this.fornecedor.getLocalizacao().getCidade());
        txtFdIdLocalidade.setText(this.fornecedor.getLocalizacao().getLocalidade());
        txtFdIdRua.setText(this.fornecedor.getLocalizacao().getRua());
        txtFdIdCp.setText(this.fornecedor.getLocalizacao().getCodigoPostal());
        txtFdIdN_Porta.setText(String.valueOf(this.fornecedor.getLocalizacao().getNumeroPorta()));
        txtFdIdContacto.setText(String.valueOf(this.fornecedor.getContacto()));
    }
}
