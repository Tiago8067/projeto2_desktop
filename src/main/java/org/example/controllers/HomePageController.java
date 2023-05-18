package org.example.controllers;

import com.sun.prism.shader.DrawRoundRect_Color_AlphaTest_Loader;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controllers.card.CardDoacoesController;
import org.example.dao.FornecedorDao;
import org.example.dao.RoupaDao;
import org.example.dao.UtilizadorDao;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    GoToUtil goToUtil;
    RegexDados regexDados;
    Fornecedor fornecedor;
    FornecedorDao fornecedorDao;
    RoupaDao roupaDao;

    //HOMEPAGE
    @FXML
    private TabPane abasTabPaneId;
    @FXML
    private MenuItem idgotoLoginPage;

    //DOACOES
    @FXML
    private Tab tabIdDoacoes;
    @FXML
    private Button btnIdAddDoacao;
    @FXML
    private TableView<Doacao> tableViewDoacao;
    @FXML
    private TableColumn<Integer, Doacao> tableColumnIdDoacao;
    @FXML
    private TableColumn<String, Utilizador> tableColumnNomeCliente;
    @FXML
    private TableColumn<String, Doacao> tableColumnDataDoacao;
    @FXML
    private TableColumn<TipoRoupa, Roupa> tableColumnTipoRoupaDoacao;
    @FXML
    private TableColumn<TamanhoRoupa, Roupa> tableColumnTamanhoRoupaDoacao;
    @FXML
    private TableColumn<Integer, Roupa_Doacao> tableColumnQtdDoacao;
    @FXML
    private TableColumn<Doacao, Doacao> tableColumnAcoesDoacao;

    //STOCK
    private List<Roupa> listaRoupaParaCardSotck;
    @FXML
    private Tab tabIdStock;
    @FXML
    private Button idBtnAddStock;
    @FXML
    private GridPane idCardStock;

    //PEDIDOS
    private ObservableList<Encomenda> encomendaObservableList;
    @FXML
    private Tab tabIdPedidos;
    @FXML
    private Button btnIdPedidoAdicionar;
    @FXML
    private TableView<Encomenda> tvPedidos;
    @FXML
    private TableColumn<Encomenda, String> tcIdPedidoDestinatario;
    @FXML
    private TableColumn<Encomenda, String> tcIdPedidoTipoRoupa;
    @FXML
    private TableColumn<Encomenda, String> tcIdPedidoTamanhoRoupa;
    @FXML
    private TableColumn<Encomenda, Integer> tcIdPedidoQuantidade;
    @FXML
    private TableColumn<?, ?> tcIdDataPedido;
    @FXML
    private TableColumn<Encomenda, Encomenda> tcIdPedidoAcoes;

    //ENTREGAS
    @FXML
    private Tab tabEntregas;
    @FXML
    private TableView<?> tvEntregas;
    @FXML
    private TableColumn<?, ?> tCNomeForn;
    @FXML
    private TableColumn<?, ?> tCDataEntrega;
    @FXML
    private TableColumn<?, ?> tCEstado;

    //FORNECEDOR
    private ObservableList<Fornecedor> observableListFornecedor;
    @FXML
    private Tab tabIdFornecedor;
    @FXML
    private Button btnIdAddFornecedor;
    @FXML
    private TableView<Fornecedor> tvFornecedor;
    @FXML
    private TableColumn<Fornecedor, Integer> tCIdFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> tCNomeFornecedor;
    @FXML
    private TableColumn<Fornecedor, Fornecedor> tCAcoesFornecedor;

    //FUNCIONARIOS
    private ObservableList<Utilizador> observableListFuncionarios;
    @FXML
    private Tab tabIdFuncionarios;
    @FXML
    private TableView<Utilizador> tvFuncionarios;
    @FXML
    private TableColumn<Utilizador, Integer> tCIdFuncionarios;
    @FXML
    private TableColumn<Utilizador, String> tCUsernameFunc;
    @FXML
    private TableColumn<Utilizador, String> tCNomeFunc;
    @FXML
    private TableColumn<Utilizador, EstadoUtilizador> tCIdEstadoFunc;
    @FXML
    private TableColumn<Utilizador, Utilizador> tCAcoesFunc;

    //EDITAR FUNCIONARIOS
    @FXML
    private Tab tabEditarFuncionario;
    @FXML
    private MenuItem menuItemIdAtivo;
    @FXML
    private MenuItem menuItemIdInativo;
    @FXML
    private TextField txtFdIdEstado;
    @FXML
    private TextField txtFdAtualizarId;
    @FXML
    private TextField txtFdAtualizarContacto;
    @FXML
    private Label lblErroAtualizaContacto;
    @FXML
    private TextField txtFdAtualizarNome;
    @FXML
    private TextField txtFdAtualizarCidade;
    @FXML
    private TextField txtFdAtualizarLocalidade;
    @FXML
    private TextField txtFdAtualizarRua;
    @FXML
    private Label lblErroAtualizaNome;
    @FXML
    private Label lblErroAtualizaCidade;
    @FXML
    private Label lblErroAtualizaLocalidade;
    @FXML
    private Label lblErroAtualizaRua;
    @FXML
    private TextField txtFdAtualizarCP;
    @FXML
    private TextField txtFdAtualizarNPorta;
    @FXML
    private Label lblErroAtualizaCP;
    @FXML
    private Label lblErroAtualizaNPorta;
    @FXML
    private Button idEditarFuncionarioVoltar;
    @FXML
    private Button idEditarFuncionarioApagar;
    @FXML
    private Button idEditarFuncionarioAtualizar;

    //HOMEPAGE
    @FXML
    void gotoLoginPage(ActionEvent event) {
        this.goToUtil.goToLogin();
        Stage stage = (Stage) idgotoLoginPage.getParentPopup().getOwnerWindow();
        stage.close();
    }

    //DOACOES
    @FXML
    void btnAddDoacao(ActionEvent event) {
        this.goToUtil.goToAddDoacao();
        Stage stage = (Stage) btnIdAddDoacao.getScene().getWindow();
        stage.close();
    }

    //STOCK

    //PEDIDOS
    @FXML
    void btnPedidoAdicionar(ActionEvent event) {
        this.goToUtil.goToAddPedido();
        Stage stage = (Stage) btnIdPedidoAdicionar.getScene().getWindow();
        stage.close();
    }

    //ENTREGAS

    //FORNECEDOR
    @FXML
    void btnAddFornecedor(ActionEvent event) {
        this.goToUtil.goToAddFornecedor();
        Stage stage = (Stage) btnIdAddFornecedor.getScene().getWindow();
        stage.close();
    }

    //FUNCIONARIO

    //EDITAR FUNCIONARIO
    @FXML
    void menuItemActionAtivo(ActionEvent event) {
        txtFdIdEstado.setText(menuItemIdAtivo.getText());
    }

    @FXML
    void menuItemActionInativo(ActionEvent event) {
        txtFdIdEstado.setText(menuItemIdInativo.getText());
    }

    @FXML
    void btnEditarFuncionarioVoltar(ActionEvent event) {
        gotoTabFuncionarios();
    }

    @FXML
    void btnEditarFuncionarioApagar(ActionEvent event) {
        int idFuncionarioAtualizar = Integer.parseInt(txtFdAtualizarId.getText());

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
        int idFuncionarioAtualizar = Integer.parseInt(txtFdAtualizarId.getText());

        try {
            verificaNumPorta = Integer.parseInt(txtFdAtualizarNPorta.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaContacto = Integer.parseInt(txtFdAtualizarContacto.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        for (Utilizador u: this.observableListFuncionarios) {
            if (u.getIdUtilizador() == idFuncionarioAtualizar) {
                if (txtFdAtualizarNome.getText().isEmpty()) {
                    lblErroAtualizaNome.setText("Tem de preencher o Nome Completo.");
                } else {
                    u.setNome(txtFdAtualizarNome.getText());
                    lblErroAtualizaNome.setText("");
                }

                if (txtFdAtualizarContacto.getText().isEmpty()) {
                    lblErroAtualizaContacto.setText("Tem de preencher o Contacto no seu Registro");
                } else if (verificaContacto == 0) {
                    lblErroAtualizaContacto.setText("Preencha corretamente o Contacto no seu Registro");
                } else {
                    u.setContacto(Integer.valueOf(txtFdAtualizarContacto.getText()));
                    lblErroAtualizaContacto.setText("");
                }

                if (txtFdAtualizarCidade.getText().isEmpty()){
                    lblErroAtualizaCidade.setText("Tem de preencher o Distrito no seu Registro");
                } else {
                    u.getLocalizacao().setCidade(txtFdAtualizarCidade.getText());
                    lblErroAtualizaCidade.setText("");
                }

                if (txtFdAtualizarCP.getText().isEmpty()){
                    lblErroAtualizaCP.setText("Tem de preencher o Código Postal no seu Registro");
                } else if (!this.regexDados.isValidCP(txtFdAtualizarCP.getText())) {
                    lblErroAtualizaCP.setText("Por favor, preencha o Código Postal Corretamente");
                } else {
                    u.getLocalizacao().setCodigoPostal(txtFdAtualizarCP.getText());
                    lblErroAtualizaCP.setText("");
                }

                if (txtFdAtualizarLocalidade.getText().isEmpty()){
                    lblErroAtualizaLocalidade.setText("Tem de preencher a Localidade no seu Registro");
                } else {
                    u.getLocalizacao().setLocalidade(txtFdAtualizarLocalidade.getText());
                    lblErroAtualizaLocalidade.setText("");
                }

                if (txtFdAtualizarRua.getText().isEmpty()){
                    lblErroAtualizaRua.setText("Tem de preencher a Rua no seu Registro");
                } else {
                    u.getLocalizacao().setRua(txtFdAtualizarRua.getText());
                    lblErroAtualizaRua.setText("");
                }

                if (txtFdAtualizarNPorta.getText().isEmpty()){
                    lblErroAtualizaNPorta.setText("Tem de preencher o Número da Porta no seu Registro");
                } else if (verificaNumPorta == 0) {
                    lblErroAtualizaNPorta.setText("Preencha corretamente o Número da Porta no seu Registro");
                } else {
                    u.getLocalizacao().setNumeroPorta(Integer.valueOf(txtFdAtualizarNPorta.getText()));
                    lblErroAtualizaNPorta.setText("");
                }

                if (txtFdIdEstado.getText().equalsIgnoreCase("ATIVO")){
                    u.setEstadoUtilizador(EstadoUtilizador.ATIVO);
                } else if (txtFdIdEstado.getText().equalsIgnoreCase("INATIVO")) {
                    u.setEstadoUtilizador(EstadoUtilizador.INATIVO);
                }

                if (lblErroAtualizaNome.getText().equals("") && lblErroAtualizaContacto.getText().equals("") && lblErroAtualizaCidade.getText().equals("") && lblErroAtualizaCP.getText().equals("")
                        && lblErroAtualizaLocalidade.getText().equals("") && lblErroAtualizaRua.getText().equals("") && lblErroAtualizaNPorta.getText().equals("")) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.goToUtil = new GoToUtil();
        this.regexDados = new RegexDados();
        this.fornecedor = new Fornecedor();
        this.fornecedorDao = new FornecedorDao(entityManager);
        this.roupaDao = new RoupaDao(entityManager);


        this.listaRoupaParaCardSotck = this.roupaDao.buscarTodas();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r: this.listaRoupaParaCardSotck) {
                //System.out.println(r);
                FXMLLoader fxmlLoader =  new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/cards/cardDoacoes.fxml"));
                VBox cardBox = fxmlLoader.load();
                CardDoacoesController cardDoacoesController = fxmlLoader.getController();
                cardDoacoesController.setCardDoacoes(r);

                if (coluna == 3) {
                    coluna = 0;
                    ++linha;
                }

                idCardStock.add(cardBox, coluna++, linha);
                GridPane.setMargin(cardBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        
        initializeNodes();
    }

    private void initializeNodes() {
        tCIdFuncionarios.setCellValueFactory(new PropertyValueFactory<>("idUtilizador"));
        tCUsernameFunc.setCellValueFactory(new PropertyValueFactory<>("username"));
        tCNomeFunc.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tCIdEstadoFunc.setCellValueFactory(new PropertyValueFactory<>("estadoUtilizador"));

        tCIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
        tCNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // TODO => tornar javafx responsivo

        /*
        Stage stage = (Stage) new Stage().getScene().getWindow();
        abasTabPaneId.prefHeightProperty().bind(stage.heightProperty());
        */
    }

    //HOMEPAGE

    //DOACOES

    //STOCK

    //PEDIDOS

    //ENTREGAS

    //FORNECEDOR
    public void listaFornecedor() {
        List<Fornecedor> fornecedorList = this.fornecedorDao.buscarTodosFornecedor();
        observableListFornecedor = FXCollections.observableArrayList(fornecedorList);
        tvFornecedor.setItems(observableListFornecedor);

        initEditButtonsFornecedor();
    }

    private void initEditButtonsFornecedor() {
        tCAcoesFornecedor.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tCAcoesFornecedor.setCellFactory(param -> new TableCell<Fornecedor, Fornecedor>() {
            private final Button button = new Button("Editar");

            @Override
            protected void updateItem(Fornecedor obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setPrefWidth(70);
                button.setOnAction(
                        event -> gotoEditarFornecedor(obj));
            }
        });
    }

    private void gotoEditarFornecedor(Fornecedor obj) {
        this.goToUtil.goToEditFornecedor(obj);
        Stage stage = (Stage) btnIdAddFornecedor.getScene().getWindow();
        stage.close();
    }

    //FUNCIONARIOS
    public void listaFuncionarios() {
        List<Utilizador> utilizadorList = this.utilizadorDao.buscarTodos();
        List<Utilizador> listaFuncionarios = new ArrayList<>();

        for (Utilizador u: utilizadorList) {
            if (u.getTipoUtilizador().equals(TipoUtilizador.FUNCIONARIO)) {
                listaFuncionarios.add(u);
            }
        }

        observableListFuncionarios = FXCollections.observableArrayList(listaFuncionarios);
        tvFuncionarios.setItems(observableListFuncionarios);

        initEditButtons();
    }

    private void exibirTabEditarFuncionarios(Utilizador obj) {
        tabEditarFuncionario.setDisable(false);
        tabIdDoacoes.setDisable(true);
        tabIdStock.setDisable(true);
        tabIdPedidos.setDisable(true);
        tabEntregas.setDisable(true);
        tabIdFornecedor.setDisable(true);
        tabIdFuncionarios.setDisable(true);
        abasTabPaneId.getSelectionModel().select(tabEditarFuncionario);
        txtFdAtualizarId.setText(String.valueOf(obj.getIdUtilizador()));
        txtFdAtualizarNome.setText(obj.getNome());
        txtFdAtualizarCidade.setText(obj.getLocalizacao().getCidade());
        txtFdAtualizarLocalidade.setText(obj.getLocalizacao().getLocalidade());
        txtFdAtualizarRua.setText(obj.getLocalizacao().getRua());
        txtFdAtualizarCP.setText(obj.getLocalizacao().getCodigoPostal());
        txtFdAtualizarNPorta.setText(String.valueOf(obj.getLocalizacao().getNumeroPorta()));
        txtFdAtualizarContacto.setText(String.valueOf(obj.getContacto()));
        txtFdIdEstado.setText(String.valueOf(obj.getEstadoUtilizador()));
    }

    private void initEditButtons() {
        tCAcoesFunc.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tCAcoesFunc.setCellFactory(param -> new TableCell<Utilizador, Utilizador>() {
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

    //EDITAR FUNCIONARIO
    private void gotoTabFuncionarios() {
        tabEditarFuncionario.setDisable(true);
        tabIdDoacoes.setDisable(false);
        tabIdStock.setDisable(false);
        tabIdPedidos.setDisable(false);
        tabEntregas.setDisable(false);
        tabIdFornecedor.setDisable(false);
        tabIdFuncionarios.setDisable(false);
        abasTabPaneId.getSelectionModel().select(tabIdFuncionarios);
    }

}
