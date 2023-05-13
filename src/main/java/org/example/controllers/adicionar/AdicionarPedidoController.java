package org.example.controllers.adicionar;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.*;
import org.example.models.*;
import org.example.models.enums.EstadoEncomenda;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.example.models.enums.TipoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;

public class AdicionarPedidoController implements Initializable {
    EntityManager entityManager;
    Encomenda encomenda;
    EncomendaDao encomendaDao;
    UtilizadorDao utilizadorDao;
    FornecedorDao fornecedorDao;
    Roupa roupa;
    RoupaDao roupaDao;
    LinhaEncomenda linhaEncomenda;
    LinhaEncomendaDao linhaEncomendaDao;
    GoToUtil goToUtil;

    @FXML
    private Button btnIdAdicionar;
    @FXML
    private Button btnIdVoltar;
    @FXML
    private ChoiceBox<TipoRoupa> cBIdTipoRoupa;
    @FXML
    private ChoiceBox<TamanhoRoupa> cBIdTamanhoRoupa;
    @FXML
    private Label labelIdErroDataPedido;
    @FXML
    private Label labelIdErroEstadoEnc;
    @FXML
    private Label labelIdErroFornecedor;
    @FXML
    private Label labelIdErroNomeCliente;
    @FXML
    private Label labelIdErroQuantidade;
    @FXML
    private Label labelIdErroTamanho;
    @FXML
    private Label labelIdErroTipoRoupa;
    @FXML
    private TextField txtFIdFornecedor;
    @FXML
    private TextField txtFdIdNomeCliente;
    @FXML
    private TextField txtFdIdQtd;
    @FXML
    private TextField txtFdIdTamanhoRoupa;
    @FXML
    private MenuItem mIIdEstadoEmPreparacao;

    @FXML
    private MenuItem mIIdEstadoEnviado;

    @FXML
    private MenuItem mIIdEstadoFinalizado;
    @FXML
    private TextField txtFIdAdicionaEstado;
    @FXML
    void mIEstadoEmPreparacao(ActionEvent event) {
        txtFIdAdicionaEstado.setText(mIIdEstadoEmPreparacao.getText());
    }

    @FXML
    void mIEstadoEnviado(ActionEvent event) {
        txtFIdAdicionaEstado.setText(mIIdEstadoEnviado.getText());
    }

    @FXML
    void mIEstadoFinalizado(ActionEvent event) {
        txtFIdAdicionaEstado.setText(mIIdEstadoFinalizado.getText());
    }

    @FXML
    void btnAdicionar(ActionEvent event) {
        int verificaQtd = 0;

        try {
            verificaQtd = Integer.parseInt(txtFdIdQtd.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        if (txtFdIdNomeCliente.getText().isEmpty()) {
            labelIdErroNomeCliente.setText("Tem de preencher o Nome de Utilizador.");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()) == null) {
            labelIdErroNomeCliente.setText("Este Utilizador não existe!");
        } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
            labelIdErroNomeCliente.setText("Este Utilizador não é um Cliente!!! Introduza novamente!");
        } else {
            this.encomenda.setUtilizador(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()));
            labelIdErroNomeCliente.setText("");
        }

        if (cBIdTipoRoupa.getValue() == null) {
            labelIdErroTipoRoupa.setText("Tem de preencher o Tipo de Roupa.");
        } else {
            this.roupa.setTipoRoupa(cBIdTipoRoupa.getValue());
            labelIdErroTipoRoupa.setText("");
        }

        if (cBIdTamanhoRoupa.getValue() == null) {
            labelIdErroTamanho.setText("Tem de preencher o Tipo de Roupa.");
        } else {
            this.roupa.setTamanhoRoupa(cBIdTamanhoRoupa.getValue());
            labelIdErroTamanho.setText("");
        }

        //TODO - FALTA A DATA

//        if (txtFdIdQtd.getText().equals("")) {
//            labelIdErroQuantidade.setText("Tem de preencher a Quantidade.");
//        } else if (verificaQtd == 0) {
//            labelIdErroQuantidade.setText("Preencha corretamente a Quantidade!");
//        } else {
//            //verificaQtd--;
//            this.roupa.setStock(Integer.valueOf(txtFdIdQtd.getText()));
//            //this.roupa.getStock();
//            labelIdErroNomeCliente.setText("");
//        }

//        public Integer getQtdStockEncomenda(){
//            //FAZER SELECT BD
////            Integer qtd = 0;
////            return;
//        }

        if (txtFIdFornecedor.getText().isEmpty()) {
            labelIdErroFornecedor.setText("Tem de preencher o Nome do Utilizador do Fornecedor.");
        } else if (this.fornecedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()) == null) {
            labelIdErroFornecedor.setText("Este Fornecedor não existe!");
        } else {
            this.encomenda.setFornecedor(this.fornecedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()));
            labelIdErroFornecedor.setText("");
        }

        if(txtFIdAdicionaEstado.getText().isEmpty()){
            labelIdErroEstadoEnc.setText("Tem de preencher o estado da Encomenda.");
        } else if (txtFIdAdicionaEstado.getText().equals("Em Preparacao")) {
            this.encomenda.setEstadoEncomenda(EstadoEncomenda.EMPREPARACAO);
            labelIdErroEstadoEnc.setText("");
        } else if (txtFIdAdicionaEstado.getText().equals("Enviado")) {
            this.encomenda.setEstadoEncomenda(EstadoEncomenda.ENVIADO);
            labelIdErroEstadoEnc.setText("");
        } else if (txtFIdAdicionaEstado.getText().equals("Finalizado")) {
            this.encomenda.setEstadoEncomenda(EstadoEncomenda.FINALIZADO);
            labelIdErroEstadoEnc.setText("");
        }

        if(labelIdErroNomeCliente.getText().equals("") && labelIdErroTipoRoupa.getText().equals("") && labelIdErroTamanho.getText().equals("")
                && labelIdErroFornecedor.getText().equals("") && labelIdErroEstadoEnc.getText().equals("")){
            this.linhaEncomenda.setEncomenda(this.encomenda);
            this.linhaEncomenda.setRoupa(this.roupa);
            this.encomendaDao.registar(this.encomenda);
            this.linhaEncomendaDao.registar(this.linhaEncomenda);
            this.roupaDao.registar(this.roupa);
            this.goToUtil.goToHomePageAdmin();
            Stage stage = (Stage) btnIdAdicionar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        this.goToUtil.goToHomePageAdmin();
        Stage stage = (Stage) btnIdVoltar.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.encomenda = new Encomenda();
        this.encomendaDao = new EncomendaDao(entityManager);
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.fornecedorDao = new FornecedorDao(entityManager);
        this.roupa = new Roupa();
        this.roupaDao = new RoupaDao(entityManager);
        this.linhaEncomenda = new LinhaEncomenda();
        this.linhaEncomendaDao = new LinhaEncomendaDao(entityManager);
        this.goToUtil = new GoToUtil();

        //cBIdTipoRoupa.setItems(FXCollections.observableArrayList(this.tipoRoupas));
        cBIdTipoRoupa.getItems().addAll(TipoRoupa.values());
        cBIdTamanhoRoupa.getItems().addAll(TamanhoRoupa.values());
        //cBIdTipoRoupa.setValue(TipoRoupa.BLUSA);
        //System.out.println(tipoRoupas);
    }
}
