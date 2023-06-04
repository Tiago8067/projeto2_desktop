package org.example.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.controllers.card.CardDoacoesController;
import org.example.dao.*;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.modelsHelp.LinhaDoacoes;
import org.example.modelsHelp.LinhaEncomendas;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;

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
    private TableView<Fornecedor> tvFornecedor;
    @FXML
    private TableColumn<Fornecedor, Integer> tCIdFornecedor;
    @FXML
    private TableColumn<Fornecedor, String> tCNomeFornecedor;
    @FXML
    private TableColumn<Fornecedor, Fornecedor> tCAcoesFornecedor;

    //ÇLIENTES
    private ObservableList<Utilizador> observableListClientes;
    @FXML
    private Tab tabIdClientes;
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

        tcIdDataPedido.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getDataDePedido();
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

        tCDataEntrega.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LinhaEncomendas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LinhaEncomendas, String> linhaEncomendasStringCellDataFeatures) {
                return linhaEncomendasStringCellDataFeatures.getValue().getDataDeEntrega();
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
