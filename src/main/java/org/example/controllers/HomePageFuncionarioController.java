package org.example.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controllers.card.CardDoacoesController;
import org.example.dao.*;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.modelsHelp.LinhaDoacoes;
import org.example.modelsHelp.LinhaEncomendas;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageFuncionarioController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    GoToUtil goToUtil;
    FornecedorDao fornecedorDao;
    DoacaoDao doacaoDao;
    RoupaDao roupaDao;
    EncomendaDao encomendaDao;

    //HOMEPAGE
    @FXML
    public TabPane abasTabPaneId;
    @FXML
    private MenuItem idgotoPerfilPage;
    @FXML
    private MenuItem idgotoLoginPage;

    //DOACOES
    ObservableList<LinhaDoacoes> observableListDoacoes;
    @FXML
    private Button btnIdAddDoacao;
    @FXML
    private TableView<LinhaDoacoes> tableViewDoacao;
    @FXML
    private TableColumn<LinhaDoacoes, Integer> tableColumnIdDoacao;
    @FXML
    private TableColumn<LinhaDoacoes, String> tableColumnNomeCliente;
    @FXML
    private TableColumn<LinhaDoacoes, String> tableColumnDataDoacao;
    @FXML
    private TableColumn<LinhaDoacoes, String> tableColumnTipoRoupaDoacao;
    @FXML
    private TableColumn<LinhaDoacoes, String> tableColumnTamanhoRoupaDoacao;
    @FXML
    private TableColumn<LinhaDoacoes, Integer> tableColumnQtdDoacao;
    @FXML
    private TableColumn<LinhaDoacoes, LinhaDoacoes> tableColumnAcoesDoacao;

    //STOCK
    @FXML
    private GridPane idCardStock;

    //PEDIDOS
    private ObservableList<LinhaEncomendas> encomendaObservableList;
    @FXML
    private Tab tabIdPedidos;
    @FXML
    private Button btnIdPedidoAdicionar;
    @FXML
    private TableView<LinhaEncomendas> tvPedidos;
    @FXML
    private TableColumn<LinhaEncomendas, String> tcIdPedidoDestinatario;
    @FXML
    private TableColumn<LinhaEncomendas, String> tcIdPedidoTipoRoupa;
    @FXML
    private TableColumn<LinhaEncomendas, String> tcIdPedidoTamanhoRoupa;
    @FXML
    private TableColumn<LinhaEncomendas, Integer> tcIdPedidoQuantidade;
    @FXML
    private TableColumn<LinhaEncomendas, String> tcIdDataPedido;
    @FXML
    private TableColumn<LinhaEncomendas, LinhaEncomendas> tcIdPedidoAcoes;

    //ENTREGAS
    @FXML
    private TableView<LinhaEncomendas> tvEntregas;
    @FXML
    private TableColumn<LinhaEncomendas, String> tCNomeForn;
    @FXML
    private TableColumn<LinhaEncomendas, String> tCDataEntrega;
    @FXML
    private TableColumn<LinhaEncomendas, String> tCEstado;

    //FORNECEDOR
    ObservableList<Fornecedor> observableListFornecedor;
    @FXML
    public Tab tabIdFornecedor;
    @FXML
    private TableView<Fornecedor> tvFornecedor;
    @FXML
    private TableColumn<Fornecedor, Integer> tCIdFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> tCNomeFornecedor;

    //ÇLIENTES
    ObservableList<Utilizador> observableListClientes;
    @FXML
    private TableView<Utilizador> tvClientes;
    @FXML
    private TableColumn<Utilizador, Integer> tCIdCliente;
    @FXML
    private TableColumn<Utilizador, String> tCUsernameCliente;
    @FXML
    private TableColumn<Utilizador, String> tCNomeCliente;
    @FXML
    private TableColumn<Utilizador, EstadoUtilizador> tCIdEstadoCliente;

    //HOMEPAGE
    @FXML
    void gotoPerfilPage(ActionEvent event) {
        this.goToUtil.gotoPerfilPage();
        Stage stage = (Stage) idgotoPerfilPage.getParentPopup().getOwnerWindow();
        stage.close();
    }

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
    @FXML
    void btnVerSotck(ActionEvent event) {
        idCardStock.getChildren().clear();
        verCardStock();
    }

    @FXML
    void btnParteCima(ActionEvent event) {
        filtrarCardSotckPorCategoria(CategoriaRoupa.PARTEDECIMA);
    }

    @FXML
    void btnParteBaixo(ActionEvent event) {
        filtrarCardSotckPorCategoria(CategoriaRoupa.PARTEDEBAIXO);
    }

    @FXML
    void btnBebe(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.MESES0);
    }

    @FXML
    void btnBebe12(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.MESES12);
    }

    @FXML
    void btnBebe18(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.MESES18);
    }

    @FXML
    void btnBebe24(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.MESES24);
    }

    @FXML
    void btnBebe3(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.MESES3);
    }

    @FXML
    void btnBebe6(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.MESES6);
    }

    @FXML
    void btnBebe9(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.MESES9);
    }

    @FXML
    void btnCrianca10(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.ANOS10);
    }

    @FXML
    void btnCrianca12(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.ANOS12);
    }

    @FXML
    void btnCrianca14(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.ANOS14);
    }

    @FXML
    void btnCrianca3(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.ANOS3);
    }

    @FXML
    void btnCrianca6(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.ANOS6);
    }

    @FXML
    void btnCrianca8(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.ANOS8);
    }

    @FXML
    void btnAdolescente16(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.ANOS16);
    }

    @FXML
    void btnXS(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.XS);
    }

    @FXML
    void btnS(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.S);
    }

    @FXML
    void btnM(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.M);
    }

    @FXML
    void btnL(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.L);
    }

    @FXML
    void btnXL(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.XL);
    }

    @FXML
    void btnXXL(ActionEvent event) {
        filtrarCardSotckPorTamanho(TamanhoRoupa.XXL);
    }

    //PEDIDOS => ENTREGAS
    @FXML
    void btnPedidoAdicionar(ActionEvent event) {
        this.goToUtil.goToAddPedido();
        Stage stage = (Stage) btnIdPedidoAdicionar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void filtrarEntregasEstadoTodos(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendas();
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        mostrarDadosTableView();
    }

    @FXML
    void filtrarEntregasEstadoPorEnviar(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendasFiltradasPeloEstado(String.valueOf(EstadoEncomenda.EMPREPARACAO));
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        mostrarDadosTableView();
    }

    @FXML
    void filtrarEntregasEstadoEnviado(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendasFiltradasPeloEstado(String.valueOf(EstadoEncomenda.ENVIADO));
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        mostrarDadosTableView();
    }

    @FXML
    void filtrarEntregasEstadoFinalizado(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendasFiltradasPeloEstado(String.valueOf(EstadoEncomenda.FINALIZADO));
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        mostrarDadosTableView();
    }

    //FORNECEDOR

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.goToUtil = new GoToUtil();
        this.fornecedorDao = new FornecedorDao(entityManager);
        this.doacaoDao = new DoacaoDao(entityManager);
        this.roupaDao = new RoupaDao(entityManager);
        this.encomendaDao = new EncomendaDao(entityManager);

        verCardStock();
        initializeNodes();
    }

    private void initializeNodes() {

        //TABLEVIEW DOACOES
        tableColumnIdDoacao.setCellValueFactory(linhaDoacoesIntegerCellDataFeatures -> linhaDoacoesIntegerCellDataFeatures.getValue().getIdDoacao());

        tableColumnNomeCliente.setCellValueFactory(linhaDoacoesStringCellDataFeatures -> linhaDoacoesStringCellDataFeatures.getValue().getUsername());

        tableColumnTipoRoupaDoacao.setCellValueFactory(linhaDoacoesStringCellDataFeatures -> linhaDoacoesStringCellDataFeatures.getValue().getTipoRoupa());

        tableColumnTamanhoRoupaDoacao.setCellValueFactory(linhaDoacoesStringCellDataFeatures -> linhaDoacoesStringCellDataFeatures.getValue().getTamanhoRoupa());

        tableColumnQtdDoacao.setCellValueFactory(linhaDoacoesIntegerCellDataFeatures -> linhaDoacoesIntegerCellDataFeatures.getValue().getQuantidade());

        tableColumnDataDoacao.setCellValueFactory(linhaDoacoesStringCellDataFeatures -> linhaDoacoesStringCellDataFeatures.getValue().getDataDoacao());

        //TABLE VIEW PEDIDOS E ENTREGAS
        mostrarDadosTableView();

        //TABLE VIEW FORNECEDOR
        tCIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
        tCNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nome"));

        //TABLE VIEW Cliente
        tCIdCliente.setCellValueFactory(new PropertyValueFactory<>("idUtilizador"));
        tCUsernameCliente.setCellValueFactory(new PropertyValueFactory<>("username"));
        tCNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tCIdEstadoCliente.setCellValueFactory(new PropertyValueFactory<>("estadoUtilizador"));
    }

    //HOMEPAGE

    //DOACOES
    public void listarDoacoes() {
        List<LinhaDoacoes> listTodos = this.doacaoDao.buscarTodasDoacaoRoupa();
        observableListDoacoes = FXCollections.observableArrayList(listTodos);
        tableViewDoacao.setItems(observableListDoacoes);

        initEditButtonDoacoes();
    }

    private void initEditButtonDoacoes() {
        tableColumnAcoesDoacao.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnAcoesDoacao.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Editar");

            @Override
            protected void updateItem(LinhaDoacoes obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setPrefWidth(70);
                button.setOnAction(event -> gotoEditarDoacoes(obj));
            }
        });
    }

    private void gotoEditarDoacoes(LinhaDoacoes obj) {
        this.goToUtil.gotoEditarDoacoes(obj);
        Stage stage = (Stage) btnIdAddDoacao.getScene().getWindow();
        stage.close();
    }

    //STOCK
    private void verCardStock() {
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa roupas : this.roupaDao.buscarTipoTamanhoUnico()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/cards/cardDoacoes.fxml"));
                VBox cardBox = fxmlLoader.load();
                CardDoacoesController cardDoacoesController = fxmlLoader.getController();
                cardDoacoesController.setCardDoacoes(roupas);

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
    }

    private void filtrarCardSotckPorCategoria(CategoriaRoupa categoriaRoupa) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarCategoriaBtnEspecifico(categoriaRoupa)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
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
    }

    private void filtrarCardSotckPorTamanho(TamanhoRoupa tamanhoRoupa) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(tamanhoRoupa)) {
                FXMLLoader fxmlLoader = new FXMLLoader();
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
    }

    //PEDIDOS => ENTREGAS
    private void mostrarDadosTableView() {
        //TABLE VIEW PEDIDOS
        tcIdPedidoDestinatario.setCellValueFactory(linhaEncomendasStringCellDataFeatures -> linhaEncomendasStringCellDataFeatures.getValue().getUsernameCliente());

        tcIdPedidoTipoRoupa.setCellValueFactory(linhaEncomendasStringCellDataFeatures -> linhaEncomendasStringCellDataFeatures.getValue().getTipoRoupa());

        tcIdPedidoTamanhoRoupa.setCellValueFactory(linhaEncomendasStringCellDataFeatures -> linhaEncomendasStringCellDataFeatures.getValue().getTamanhoRoupa());

        tcIdPedidoQuantidade.setCellValueFactory(linhaEncomendasIntegerCellDataFeatures -> linhaEncomendasIntegerCellDataFeatures.getValue().getQuantidade());

        tcIdDataPedido.setCellValueFactory(linhaEncomendasStringCellDataFeatures -> linhaEncomendasStringCellDataFeatures.getValue().getDataDePedido());

        //TABLE VIEW ENTREGAS
        tCNomeForn.setCellValueFactory(linhaEncomendasStringCellDataFeatures -> linhaEncomendasStringCellDataFeatures.getValue().getUsernameFonecedor());

        tCDataEntrega.setCellValueFactory(linhaEncomendasStringCellDataFeatures -> linhaEncomendasStringCellDataFeatures.getValue().getDataDeEntrega());

        tCEstado.setCellValueFactory(linhaEncomendasStringCellDataFeatures -> linhaEncomendasStringCellDataFeatures.getValue().getEstado());
    }

    public void listarEncomendas() {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendas();
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        initEditButtonEnomendas();
    }

    private void initEditButtonEnomendas() {
        tcIdPedidoAcoes.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tcIdPedidoAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Editar");

            @Override
            protected void updateItem(LinhaEncomendas obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setPrefWidth(70);
                button.setOnAction(event -> gotoEditarEncomendas(obj));
            }
        });
    }

    private void gotoEditarEncomendas(LinhaEncomendas obj) {
        this.goToUtil.gotoEditarEncomendas(obj);
        Stage stage = (Stage) btnIdPedidoAdicionar.getScene().getWindow();
        stage.close();
    }

    public void voltarTabPedidos() {
        abasTabPaneId.getSelectionModel().select(tabIdPedidos);
    }

    //FORNECEDOR
    public void listaFornecedor() {
        List<Fornecedor> fornecedorList = this.fornecedorDao.buscarTodosFornecedor();
        observableListFornecedor = FXCollections.observableArrayList(fornecedorList);
        tvFornecedor.setItems(observableListFornecedor);
    }

    //CLIENTES
    public void listaClientes() {
        List<Utilizador> utilizadorList = this.utilizadorDao.buscarTodos();
        List<Utilizador> listaClientes = new ArrayList<>();

        for (Utilizador u : utilizadorList) {
            if (u.getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
                listaClientes.add(u);
            }
        }

        observableListClientes = FXCollections.observableArrayList(listaClientes);
        tvClientes.setItems(observableListClientes);
    }
}
