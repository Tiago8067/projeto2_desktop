package org.example.controllers.adicionar;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.dao.EncomendaDao;
import org.example.dao.FornecedorDao;
import org.example.dao.UtilizadorDao;
import org.example.models.*;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.example.models.enums.TipoUtilizador;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;

public class AdicionarPedidoController implements Initializable {
    EntityManager entityManager;
    Encomenda encomenda;
    EncomendaDao encomendaDao;
    UtilizadorDao utilizadorDao;
    FornecedorDao forncedorDao;
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
    void btnAdicionar(ActionEvent event) {
        int verificaQtd = 0;

        try {
            verificaQtd = Integer.parseInt(txtFdIdQtd.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        if (txtFdIdNomeCliente.getText().isEmpty()) {
            labelIdErroNomeCliente.setText("Tem de preencher o Nome Completo.");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()) == null) {
            labelIdErroNomeCliente.setText("Este utilizador não existe!");
        } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
            labelIdErroNomeCliente.setText("Este utilizador não é um Cliente!!! Introduza novamente!");
        } else {
            this.encomenda.setUtilizador(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()));
            labelIdErroNomeCliente.setText("");
        }

        if (cBIdTipoRoupa.getValue() == null) {
            labelIdErroNomeCliente.setText("Tem de preencher o Tipo de Roupa.");
        } else {
            //this.encomenda;v

            labelIdErroNomeCliente.setText("");
            //TODO - FAZER CRUD DOACOES PRIMEIRO POR CAUSA DA QUANTIDADE
        }

        if (cBIdTamanhoRoupa.getValue() == null) {
            labelIdErroNomeCliente.setText("Tem de preencher o Tipo de Roupa.");
        } else {
            //this.encomenda;
            labelIdErroNomeCliente.setText("");
        }

        if (txtFdIdQtd.getText().equals("")) {
            labelIdErroQuantidade.setText("Tem de preencher a Quantidade.");
        } else if (verificaQtd == 0) {
            labelIdErroQuantidade.setText("Preencha corretamente a Quantidade!");
        } else {
            //this.encomenda.setLinhas();
            labelIdErroNomeCliente.setText("");
        }
        txtFdIdQtd.getText();

        if (txtFIdFornecedor.getText().isEmpty()) {
            labelIdErroFornecedor.setText("Tem de preencher o Nome Completo do Fornecedor.");
        } else if (this.forncedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()) == null) {
            labelIdErroFornecedor.setText("Este Fornecedor não existe!");
        } else {
            this.encomenda.setFornecedor(this.forncedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()));
            labelIdErroFornecedor.setText("");
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        System.out.println(cBIdTamanhoRoupa.getValue());

        Doacao doacao = new Doacao();
        doacao.getRoupa_doacaos();
        Roupa_Doacao roupa_doacao = new Roupa_Doacao();
        roupa_doacao.getRoupa().getTipoRoupa();

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.getLocalizacao();

        this.encomenda.getLinhas();

        LinhaEncomenda linhaEncomenda = new LinhaEncomenda();
        linhaEncomenda.getRoupa().getCategoriaRoupa();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.encomenda = new Encomenda();
        this.encomendaDao = new EncomendaDao(entityManager);
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.forncedorDao = new FornecedorDao(entityManager);

        //cBIdTipoRoupa.setItems(FXCollections.observableArrayList(this.tipoRoupas));
        cBIdTipoRoupa.getItems().addAll(TipoRoupa.values());
        cBIdTamanhoRoupa.getItems().addAll(TamanhoRoupa.values());
        //cBIdTipoRoupa.setValue(TipoRoupa.BLUSA);
        //System.out.println(tipoRoupas);
    }
}
