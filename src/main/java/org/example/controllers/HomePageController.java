package org.example.controllers;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.controllers.card.CardDoacoesController;
import org.example.modelsHelp.LinhaDoacoes;
import org.example.dao.*;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.modelsHelp.LinhaEncomendas;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class HomePageController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    GoToUtil goToUtil;
    RegexDados regexDados;
    Fornecedor fornecedor;
    FornecedorDao fornecedorDao;
    DoacaoDao doacaoDao;
    Roupa_DoacaoDao roupa_doacaoDao;
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
    private ObservableList<Doacao> doacaoObservableList;
    private ObservableList<Roupa_Doacao> roupa_doacaoObservableList;
    private ObservableList<Roupa> roupaObservableList;
    private ObservableList<LinhaDoacoes> observableListDoacoes;
    @FXML
    private Tab tabIdDoacoes;
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
    private List<Roupa> listaRoupaParaCardSotck;
    @FXML
    private Tab tabIdStock;
    @FXML
    private Button idBtnAddStock;
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
    private Tab tabEntregas;
    @FXML
    private TableView<LinhaEncomendas> tvEntregas;
    @FXML
    private TableColumn<LinhaEncomendas, String> tCNomeForn;
    @FXML
    private TableColumn<LinhaEncomendas, String> tCDataEntrega;
    @FXML
    private TableColumn<LinhaEncomendas, String> tCEstado;

    //FORNECEDOR
    private ObservableList<Fornecedor> observableListFornecedor;
    @FXML
    public Tab tabIdFornecedor;
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
    private MenuItem mtIdTipoAtualizeAdmin;
    @FXML
    private MenuItem mtIdTipoAtualizeFuncionario;
    @FXML
    private TextField txtFdIdTipo;
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
    void gotoPerfilPage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/perfilPage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            PerfilPageController perfilPageController = fxmlLoader.getController();
            perfilPageController.retornaUsernameLogin();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

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

    @FXML
    void btnParteCima(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarCategoriaBtnEspecifico(CategoriaRoupa.PARTEDECIMA)) {
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

    @FXML
    void btnParteBaixo(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarCategoriaBtnEspecifico(CategoriaRoupa.PARTEDEBAIXO)) {
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

    @FXML
    void btnAcessorios(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarCategoriaBtnEspecifico(CategoriaRoupa.ACESSORIOS)) {
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

    @FXML
    void btnBebe(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.BEBE)) {
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

    @FXML
    void btnCrianca(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.Crianca)) {
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

    @FXML
    void btnAdolescente12(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.Adolescente12)) {
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

    @FXML
    void btnAdolescente14(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.Adolescente14)) {
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

    @FXML
    void btnAdolescente16(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.Adolescente16)) {
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

    @FXML
    void btnXS(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.XS)) {
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

    @FXML
    void btnS(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.S)) {
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

    @FXML
    void btnM(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.M)) {
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

    @FXML
    void btnL(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.L)) {
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

    @FXML
    void btnXL(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.XL)) {
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

    @FXML
    void btnXXL(ActionEvent event) {
        idCardStock.getChildren().clear();
        int coluna = 0;
        int linha = 1;

        try {
            for (Roupa r : this.roupaDao.buscarTamanhoBtnEspecifico(TamanhoRoupa.XXL)) {
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
    @FXML
    void btnPedidoAdicionar(ActionEvent event) {
        this.goToUtil.goToAddPedido();
        Stage stage = (Stage) btnIdPedidoAdicionar.getScene().getWindow();
        stage.close();
    }

    //FORNECEDOR
    @FXML
    void btnAddFornecedor(ActionEvent event) {
        this.goToUtil.goToAddFornecedor();
        Stage stage = (Stage) btnIdAddFornecedor.getScene().getWindow();
        stage.close();
    }

    @FXML
    void filtrarEntregasEstadoTodos(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendas();
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        //TABLE VIEW PEDIDOS
        tcIdPedidoDestinatario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameCliente();
            }
        });

        tcIdPedidoTipoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTipoRoupa();
            }
        });

        tcIdPedidoTamanhoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTamanhoRoupa();
            }
        });

        tcIdPedidoQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaEncomendas, Integer> linhaEncomendasIntegerCellDataFeatures) {
                return linhaEncomendasIntegerCellDataFeatures.getValue().getQuantidade();
            }
        });

        //TABLE VIEW ENTREGAS
        tCNomeForn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameFonecedor();
            }
        });

        // todo falta a data

        tCEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getEstado();
            }
        });
    }

    @FXML
    void filtrarEntregasEstadoPorEnviar(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendasEmPreparacao(String.valueOf(EstadoEncomenda.EMPREPARACAO));
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        //TABLE VIEW PEDIDOS
        tcIdPedidoDestinatario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameCliente();
            }
        });

        tcIdPedidoTipoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTipoRoupa();
            }
        });

        tcIdPedidoTamanhoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTamanhoRoupa();
            }
        });

        tcIdPedidoQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaEncomendas, Integer> linhaEncomendasIntegerCellDataFeatures) {
                return linhaEncomendasIntegerCellDataFeatures.getValue().getQuantidade();
            }
        });

        //TABLE VIEW ENTREGAS
        tCNomeForn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameFonecedor();
            }
        });

        // todo falta a data

        tCEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getEstado();
            }
        });

        initEditButtonEnomendas();


        /*for (LinhaEncomendas le : this.encomendaDao.buscarTodasEncomendas()) {
            if (le.getEstado().getValue().equals(String.valueOf(EstadoEncomenda.EMPREPARACAO))) {
                encomendaObservableList = FXCollections.observableArrayList(le);
                tvPedidos.setItems(encomendaObservableList);
                tvEntregas.setItems(encomendaObservableList);

                //TABLE VIEW PEDIDOS
                tcIdPedidoDestinatario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                        return linhaEncomendasStringCellDataFeatures.getValue().getUsernameCliente();
                    }
                });

                tcIdPedidoTipoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                        return linhaEncomendasStringCellDataFeatures.getValue().getTipoRoupa();
                    }
                });

                tcIdPedidoTamanhoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                        return linhaEncomendasStringCellDataFeatures.getValue().getTamanhoRoupa();
                    }
                });

                tcIdPedidoQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, Integer>, ObservableValue<Integer>>() {
                    @Override
                    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaEncomendas, Integer> linhaEncomendasIntegerCellDataFeatures) {
                        return linhaEncomendasIntegerCellDataFeatures.getValue().getQuantidade();
                    }
                });

                //TABLE VIEW ENTREGAS
                tCNomeForn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                        return linhaEncomendasStringCellDataFeatures.getValue().getUsernameFonecedor();
                    }
                });

                // todo falta a data

                tCEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                        return linhaEncomendasStringCellDataFeatures.getValue().getEstado();
                    }
                });

                initEditButtonEnomendas();
            }
        }*/
    }

    @FXML
    void filtrarEntregasEstadoEnviado(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendasEnviado(String.valueOf(EstadoEncomenda.ENVIADO));
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        //TABLE VIEW PEDIDOS
        tcIdPedidoDestinatario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameCliente();
            }
        });

        tcIdPedidoTipoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTipoRoupa();
            }
        });

        tcIdPedidoTamanhoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTamanhoRoupa();
            }
        });

        tcIdPedidoQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaEncomendas, Integer> linhaEncomendasIntegerCellDataFeatures) {
                return linhaEncomendasIntegerCellDataFeatures.getValue().getQuantidade();
            }
        });

        //TABLE VIEW ENTREGAS
        tCNomeForn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameFonecedor();
            }
        });

        // todo falta a data

        tCEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getEstado();
            }
        });

        initEditButtonEnomendas();
    }

    @FXML
    void filtrarEntregasEstadoFinalizado(ActionEvent event) {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendasEnviado(String.valueOf(EstadoEncomenda.FINALIZADO));
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        //TABLE VIEW PEDIDOS
        tcIdPedidoDestinatario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameCliente();
            }
        });

        tcIdPedidoTipoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTipoRoupa();
            }
        });

        tcIdPedidoTamanhoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTamanhoRoupa();
            }
        });

        tcIdPedidoQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaEncomendas, Integer> linhaEncomendasIntegerCellDataFeatures) {
                return linhaEncomendasIntegerCellDataFeatures.getValue().getQuantidade();
            }
        });

        //TABLE VIEW ENTREGAS
        tCNomeForn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameFonecedor();
            }
        });

        // todo falta a data

        tCEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getEstado();
            }
        });
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
    void mtTipoAtualizeAdmin(ActionEvent event) {
        txtFdIdTipo.setText(mtIdTipoAtualizeAdmin.getText());
    }

    @FXML
    void mtTipoAtualizeFuncionario(ActionEvent event) {
        txtFdIdTipo.setText(mtIdTipoAtualizeFuncionario.getText());
    }

    @FXML
    void btnEditarFuncionarioVoltar(ActionEvent event) {
        gotoTabFuncionarios();
    }

    @FXML
    void btnEditarFuncionarioApagar(ActionEvent event) {
        int idFuncionarioAtualizar = Integer.parseInt(txtFdAtualizarId.getText());

        for (Utilizador u : this.observableListFuncionarios) {
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

        for (Utilizador u : this.observableListFuncionarios) {
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

                if (txtFdAtualizarCidade.getText().isEmpty()) {
                    lblErroAtualizaCidade.setText("Tem de preencher o Distrito no seu Registro");
                } else {
                    u.getLocalizacao().setCidade(txtFdAtualizarCidade.getText());
                    lblErroAtualizaCidade.setText("");
                }

                if (txtFdAtualizarCP.getText().isEmpty()) {
                    lblErroAtualizaCP.setText("Tem de preencher o Código Postal no seu Registro");
                } else if (!this.regexDados.isValidCP(txtFdAtualizarCP.getText())) {
                    lblErroAtualizaCP.setText("Por favor, preencha o Código Postal Corretamente");
                } else {
                    u.getLocalizacao().setCodigoPostal(txtFdAtualizarCP.getText());
                    lblErroAtualizaCP.setText("");
                }

                if (txtFdAtualizarLocalidade.getText().isEmpty()) {
                    lblErroAtualizaLocalidade.setText("Tem de preencher a Localidade no seu Registro");
                } else {
                    u.getLocalizacao().setLocalidade(txtFdAtualizarLocalidade.getText());
                    lblErroAtualizaLocalidade.setText("");
                }

                if (txtFdAtualizarRua.getText().isEmpty()) {
                    lblErroAtualizaRua.setText("Tem de preencher a Rua no seu Registro");
                } else {
                    u.getLocalizacao().setRua(txtFdAtualizarRua.getText());
                    lblErroAtualizaRua.setText("");
                }

                if (txtFdAtualizarNPorta.getText().isEmpty()) {
                    lblErroAtualizaNPorta.setText("Tem de preencher o Número da Porta no seu Registro");
                } else if (verificaNumPorta == 0) {
                    lblErroAtualizaNPorta.setText("Preencha corretamente o Número da Porta no seu Registro");
                } else {
                    u.getLocalizacao().setNumeroPorta(Integer.valueOf(txtFdAtualizarNPorta.getText()));
                    lblErroAtualizaNPorta.setText("");
                }

                if (txtFdIdEstado.getText().equalsIgnoreCase("ATIVO")) {
                    u.setEstadoUtilizador(EstadoUtilizador.ATIVO);
                } else if (txtFdIdEstado.getText().equalsIgnoreCase("INATIVO")) {
                    u.setEstadoUtilizador(EstadoUtilizador.INATIVO);
                }

                if (txtFdIdTipo.getText().equalsIgnoreCase("ADMIN")) {
                    u.setTipoUtilizador(TipoUtilizador.ADMIN);
                } else if (txtFdIdTipo.getText().equalsIgnoreCase("FUNCIONARIO")) {
                    u.setTipoUtilizador(TipoUtilizador.FUNCIONARIO);
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
        this.doacaoDao = new DoacaoDao(entityManager);
        this.roupa_doacaoDao = new Roupa_DoacaoDao(entityManager);
        this.roupaDao = new RoupaDao(entityManager);
        this.encomendaDao = new EncomendaDao(entityManager);

        this.listaRoupaParaCardSotck = this.roupaDao.buscarTodas();

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
        initializeNodes();
    }

    private void initializeNodes() {

        //TABLEVIEW DOACOES
        tableColumnIdDoacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaDoacoes, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaDoacoes, Integer> linhaDoacoesIntegerCellDataFeatures) {
                return linhaDoacoesIntegerCellDataFeatures.getValue().getIdDoacao();
            }
        });

        tableColumnNomeCliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaDoacoes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaDoacoes, String> linhaDoacoesStringCellDataFeatures) {
                return linhaDoacoesStringCellDataFeatures.getValue().getUsername();
            }
        });

        tableColumnTipoRoupaDoacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaDoacoes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaDoacoes, String> linhaDoacoesStringCellDataFeatures) {
                return linhaDoacoesStringCellDataFeatures.getValue().getTipoRoupa();
            }
        });

        tableColumnTamanhoRoupaDoacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaDoacoes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaDoacoes, String> linhaDoacoesStringCellDataFeatures) {
                return linhaDoacoesStringCellDataFeatures.getValue().getTamanhoRoupa();
            }
        });

        tableColumnQtdDoacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaDoacoes, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaDoacoes, Integer> linhaDoacoesIntegerCellDataFeatures) {
                return linhaDoacoesIntegerCellDataFeatures.getValue().getQuantidade();
            }
        });

        tableColumnDataDoacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaDoacoes, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaDoacoes, String> linhaDoacoesStringCellDataFeatures) {
                return linhaDoacoesStringCellDataFeatures.getValue().getDataDoacao();
            }
        });

        //TABLE VIEW PEDIDOS
        tcIdPedidoDestinatario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameCliente();
            }
        });

        tcIdPedidoTipoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTipoRoupa();
            }
        });

        tcIdPedidoTamanhoRoupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getTamanhoRoupa();
            }
        });

        tcIdPedidoQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<LinhaEncomendas, Integer> linhaEncomendasIntegerCellDataFeatures) {
                return linhaEncomendasIntegerCellDataFeatures.getValue().getQuantidade();
            }
        });

        //TABLE VIEW ENTREGAS
        tCNomeForn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getUsernameFonecedor();
            }
        });

        // todo falta a data

        tcIdDataPedido.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getDataDePedido();
            }
        });

        tCEstado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getEstado();
            }
        });

        //TABLE VIEW FORNECEDOR
        tCIdFornecedor.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
        tCNomeFornecedor.setCellValueFactory(new PropertyValueFactory<>("nome"));

        //TABLE VIEW FUNCIONARIO
        tCIdFuncionarios.setCellValueFactory(new PropertyValueFactory<>("idUtilizador"));
        tCUsernameFunc.setCellValueFactory(new PropertyValueFactory<>("username"));
        tCNomeFunc.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tCIdEstadoFunc.setCellValueFactory(new PropertyValueFactory<>("estadoUtilizador"));

        // TODO => tornar javafx responsivo
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
        tableColumnAcoesDoacao.setCellFactory(param -> new TableCell<LinhaDoacoes, LinhaDoacoes>() {
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

    //PEDIDOS => ENTREGAS
    public void listarEncomendas() {
        List<LinhaEncomendas> listTodos = this.encomendaDao.buscarTodasEncomendas();
        encomendaObservableList = FXCollections.observableArrayList(listTodos);
        tvPedidos.setItems(encomendaObservableList);
        tvEntregas.setItems(encomendaObservableList);

        initEditButtonEnomendas();
    }

    private void initEditButtonEnomendas() {
        tcIdPedidoAcoes.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tcIdPedidoAcoes.setCellFactory(param -> new TableCell<LinhaEncomendas, LinhaEncomendas>() {
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

    public void voltarTabFornecdor() {
        abasTabPaneId.getSelectionModel().select(tabIdFornecedor);
    }

    //FUNCIONARIOS
    public void listaFuncionarios() {
        List<Utilizador> utilizadorList = this.utilizadorDao.buscarTodos();
        List<Utilizador> listaFuncionarios = new ArrayList<>();

        for (Utilizador u : utilizadorList) {
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
        txtFdIdTipo.setText(String.valueOf(obj.getTipoUtilizador()));
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
