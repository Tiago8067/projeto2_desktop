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

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private TableColumn<Utilizador, String> tableColumnNome;

    @FXML
    private TableColumn<Utilizador, EstadoUtilizador> tableColumnEstado;

    @FXML
    private TableColumn<Utilizador, Utilizador> tableColumnAcoes;

    @FXML
    private TextField txtFdAtualizarIdId;

    @FXML
    private TextField txtFdAtualizarContactoId;

    @FXML
    private TextField txtFdAtualizarMoradaId;

    @FXML
    private TextField txtFdAtualizarNomeId;

    private ObservableList<Utilizador> observableListFuncionarios;

    @FXML
    void btnEditarFuncionarioApagar(ActionEvent event) {

    }

    @FXML
    void btnEditarFuncionarioAtualizar(ActionEvent event) {
        int idFuncionarioAtualizar = Integer.parseInt(txtFdAtualizarIdId.getText());
        for (Utilizador u: this.observableListFuncionarios) {
            if (u.getIdUtilizador() == idFuncionarioAtualizar) {
                if (txtFdAtualizarNomeId.getText().isEmpty()) {
                    labelIdErroAtualizaNome.setText("Tem de preencher o Nome Completo.");
                } else {
                    u.setNome(txtFdAtualizarNomeId.getText());
                    labelIdErroAtualizaNome.setText("");
                }

                if (labelIdErroAtualizaNome.getText().equals("")) {
                    //atualizarFuncionario.setEstadoUtilizador(EstadoUtilizador.PENDENTE);
                    this.utilizadorDao.registrar(u);
                    gotoTabFuncionarios();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.goToUtil = new GoToUtil();
        
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idUtilizador"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("username"));
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
        txtFdAtualizarNomeId.setText(obj.getUsername());
        // todo - que fazer na morada
        //txtFdAtualizarMoradaId.setText(obj.);
        txtFdAtualizarContactoId.setText(String.valueOf(obj.getContacto()));
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
