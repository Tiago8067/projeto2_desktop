package org.example.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    //Tentativa 3
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    //Tentativa 2
    //EntityManager entityManager; // = JPAUtil.getEntityManager();
    //UtilizadorDao utilizadorDao; // = new UtilizadorDao(entityManager);
    //EntityManager entityManager = JPAUtil.getEntityManager();
    //UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
    GoToUtil goToUtil;  // = new GoToUtil();
    RegexDados regexDados;
    // todo usar ou nao construtores

    @FXML
    private TabPane abasTabPaneId;

    @FXML
    private Button idEditarFuncionarioApagar;

    @FXML
    private Button idEditarFuncionarioAtualizar;

    @FXML
    private Button idEditarFuncionarioVoltar;

    @FXML
    private MenuItem idgotoLoginPage;
    @FXML
    private Label labelIdErroAtualizaCP;

    @FXML
    private Label labelIdErroAtualizaCidade;

    @FXML
    private Label labelIdErroAtualizaContacto;

    @FXML
    private Label labelIdErroAtualizaLocalidade;

    @FXML
    private Label labelIdErroAtualizaN_Porta;

    @FXML
    private Label labelIdErroAtualizaNome;

    @FXML
    private Label labelIdErroAtualizaRua;

    @FXML
    private MenuItem menuItemIdAtivo;

    @FXML
    private MenuItem menuItemIdInativo;

    @FXML
    private Tab tabEditarFuncionarioId;

    @FXML
    private Tab tabIdDoacaoes;

    @FXML
    private Tab tabIdEncomendas;

    @FXML
    private Tab tabIdFuncionarios;

    @FXML
    private Tab tabIdSotck;

    @FXML
    private TableView<Utilizador> tableViewFuncionarios;

    @FXML
    private TableColumn<Utilizador, Integer> tableColumnId;

    @FXML
    private TableColumn<Utilizador, String> tableColumnUsername;

    @FXML
    private TableColumn<Utilizador, String> tableColumnNome;

    @FXML
    private TableColumn<Utilizador, EstadoUtilizador> tableColumnEstado;

    @FXML
    private TableColumn<Utilizador, Utilizador> tableColumnAcoes;

    @FXML
    private TextField txtFdAtualizarContactoId;

    @FXML
    private TextField txtFdAtualizarIdCP;

    @FXML
    private TextField txtFdAtualizarIdCidade;

    @FXML
    private TextField txtFdAtualizarIdId;

    @FXML
    private TextField txtFdAtualizarIdLocalidade;

    @FXML
    private TextField txtFdAtualizarIdN_Porta;

    @FXML
    private TextField txtFdAtualizarIdRua;

    @FXML
    private TextField txtFdAtualizarNomeId;

    @FXML
    private TextField txtFdIdEstado;

    private ObservableList<Utilizador> observableListFuncionarios;

    @FXML
    void btnEditarFuncionarioApagar(ActionEvent event) {
        int idFuncionarioAtualizar = Integer.parseInt(txtFdAtualizarIdId.getText());

        for (Utilizador u: this.observableListFuncionarios) {
            if (u.getIdUtilizador() == idFuncionarioAtualizar) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Tem a Certeza de querer Eliminar este Funcionário.");
                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.get() == ButtonType.OK) {
                    this.utilizadorDao.remover(u);
                    gotoTabFuncionarios();
                    listaFuncionarios();
                } else {
                    return;
                }
            }
        }
    }

    @FXML
    void btnEditarFuncionarioAtualizar(ActionEvent event) {
        int verificaContacto = 0;
        int verificaNumPorta = 0;
        int idFuncionarioAtualizar = Integer.parseInt(txtFdAtualizarIdId.getText());

        try {
            verificaNumPorta = Integer.parseInt(txtFdAtualizarIdN_Porta.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaContacto = Integer.parseInt(txtFdAtualizarContactoId.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        for (Utilizador u: this.observableListFuncionarios) {
            if (u.getIdUtilizador() == idFuncionarioAtualizar) {
                if (txtFdAtualizarNomeId.getText().isEmpty()) {
                    labelIdErroAtualizaNome.setText("Tem de preencher o Nome Completo.");
                } else {
                    u.setNome(txtFdAtualizarNomeId.getText());
                    labelIdErroAtualizaNome.setText("");
                }

                if (txtFdAtualizarContactoId.getText().isEmpty()) {
                    labelIdErroAtualizaContacto.setText("Tem de preencher o Contacto no seu Registro");
                } else if (verificaContacto == 0) {
                    labelIdErroAtualizaContacto.setText("Preencha corretamente o Contacto no seu Registro");
                } else {
                    u.setContacto(Integer.valueOf(txtFdAtualizarContactoId.getText()));
                    labelIdErroAtualizaContacto.setText("");
                }

                if (txtFdAtualizarIdCidade.getText().isEmpty()){
                    labelIdErroAtualizaCidade.setText("Tem de preencher o Distrito no seu Registro");
                } else {
                    u.getLocalizacao().setCidade(txtFdAtualizarIdCidade.getText());
                    labelIdErroAtualizaCidade.setText("");
                }

                if (txtFdAtualizarIdCP.getText().isEmpty()){
                    labelIdErroAtualizaCP.setText("Tem de preencher o Código Postal no seu Registro");
                } else if (!this.regexDados.isValidCP(txtFdAtualizarIdCP.getText())) {
                    labelIdErroAtualizaCP.setText("Por favor, preencha o Código Postal Corretamente");
                } else {
                    u.getLocalizacao().setCodigoPostal(txtFdAtualizarIdCP.getText());
                    labelIdErroAtualizaCP.setText("");
                }

                if (txtFdAtualizarIdLocalidade.getText().isEmpty()){
                    labelIdErroAtualizaLocalidade.setText("Tem de preencher a Localidade no seu Registro");
                } else {
                    u.getLocalizacao().setLocalidade(txtFdAtualizarIdLocalidade.getText());
                    labelIdErroAtualizaLocalidade.setText("");
                }

                if (txtFdAtualizarIdRua.getText().isEmpty()){
                    labelIdErroAtualizaRua.setText("Tem de preencher a Rua no seu Registro");
                } else {
                    u.getLocalizacao().setRua(txtFdAtualizarIdRua.getText());
                    labelIdErroAtualizaRua.setText("");
                }

                if (txtFdAtualizarIdN_Porta.getText().isEmpty()){
                    labelIdErroAtualizaN_Porta.setText("Tem de preencher o Número da Porta no seu Registro");
                } else if (verificaNumPorta == 0) {
                    labelIdErroAtualizaN_Porta.setText("Preencha corretamente o Número da Porta no seu Registro");
                } else {
                    u.getLocalizacao().setNumeroPorta(Integer.valueOf(txtFdAtualizarIdN_Porta.getText()));
                    labelIdErroAtualizaN_Porta.setText("");
                }

                if (txtFdIdEstado.getText().equalsIgnoreCase("ATIVO")){
                    u.setEstadoUtilizador(EstadoUtilizador.ATIVO);
                } else if (txtFdIdEstado.getText().equalsIgnoreCase("INATIVO")) {
                    u.setEstadoUtilizador(EstadoUtilizador.INATIVO);
                }

                if (labelIdErroAtualizaNome.getText().equals("") && labelIdErroAtualizaContacto.getText().equals("") && labelIdErroAtualizaCidade.getText().equals("") && labelIdErroAtualizaCP.getText().equals("")
                        && labelIdErroAtualizaLocalidade.getText().equals("") && labelIdErroAtualizaRua.getText().equals("") && labelIdErroAtualizaN_Porta.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Tem a Certeza de querer Editar este Funcionário.");
                    Optional<ButtonType> resultado = alert.showAndWait();
                    if (resultado.get() == ButtonType.OK) {
                        this.utilizadorDao.registrar(u);
                        gotoTabFuncionarios();
                        listaFuncionarios();
                    } else {
                        return;
                    }
                }
            }
        }
    }

    @FXML
    void btnEditarFuncionarioVoltar(ActionEvent event) {
        gotoTabFuncionarios();
    }

    @FXML
    void gotoLoginPage(ActionEvent event) {
        this.goToUtil.goToLogin();
        Stage stage = (Stage)idgotoLoginPage.getParentPopup().getOwnerWindow();
        stage.close();
    }

    @FXML
    void menuItemActionAtivo(ActionEvent event) {
        txtFdIdEstado.setText(menuItemIdAtivo.getText());
    }

    @FXML
    void menuItemActionInativo(ActionEvent event) {
        txtFdIdEstado.setText(menuItemIdInativo.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.goToUtil = new GoToUtil();
        this.regexDados = new RegexDados();
        
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idUtilizador"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estadoUtilizador"));

        // TODO => tornar javafx responsivo

        /*
        Stage stage = (Stage) new Stage().getScene().getWindow();
        tableViewFuncionarios.prefHeightProperty().bind(stage.heightProperty());
        */
    }

    public void listaFuncionarios() {
        List<Utilizador> utilizadorList = this.utilizadorDao.buscarTodos();
        List<Utilizador> listaFuncionarios = new ArrayList<>();

        for (Utilizador u: utilizadorList) {
          if (u.getTipoUtilizador().equals(TipoUtilizador.FUNCIONARIO)) {
              listaFuncionarios.add(u);
              System.out.println(listaFuncionarios);
          }
        }

        observableListFuncionarios = FXCollections.observableArrayList(listaFuncionarios);
        tableViewFuncionarios.setItems(observableListFuncionarios);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(observableListFuncionarios);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");

        initEditButtons();
    }

    private void exibirTabEditarFuncionarios(Utilizador obj) {
        tabEditarFuncionarioId.setDisable(false);
        tabIdDoacaoes.setDisable(true);
        tabIdSotck.setDisable(true);
        tabIdEncomendas.setDisable(true);
        tabIdFuncionarios.setDisable(true);
        abasTabPaneId.getSelectionModel().select(tabEditarFuncionarioId);
        //System.out.println(obj);
        txtFdAtualizarIdId.setText(String.valueOf(obj.getIdUtilizador()));
        txtFdAtualizarNomeId.setText(obj.getNome());
        // todo - que fazer na morada
        //txtFdAtualizarMoradaId.setText(obj.);
        txtFdAtualizarIdCidade.setText(obj.getLocalizacao().getCidade());
        txtFdAtualizarIdLocalidade.setText(obj.getLocalizacao().getLocalidade());
        txtFdAtualizarIdRua.setText(obj.getLocalizacao().getRua());
        txtFdAtualizarIdCP.setText(obj.getLocalizacao().getCodigoPostal());
        txtFdAtualizarIdN_Porta.setText(String.valueOf(obj.getLocalizacao().getNumeroPorta()));
        txtFdAtualizarContactoId.setText(String.valueOf(obj.getContacto()));
        txtFdIdEstado.setText(String.valueOf(obj.getEstadoUtilizador()));
    }

    private void initEditButtons() {
        tableColumnAcoes.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnAcoes.setCellFactory(param -> new TableCell<Utilizador, Utilizador>() {
            private final Button button = new Button("Editar");

            @Override
            protected void updateItem(Utilizador obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setPrefWidth(70);
                button.setOnAction(
                        event -> exibirTabEditarFuncionarios(obj));
            }
        });
    }

    private void gotoTabFuncionarios() {
        tabEditarFuncionarioId.setDisable(true);
        tabIdDoacaoes.setDisable(false);
        tabIdSotck.setDisable(false);
        tabIdEncomendas.setDisable(false);
        tabIdFuncionarios.setDisable(false);
        abasTabPaneId.getSelectionModel().select(tabIdFuncionarios);
    }
}
