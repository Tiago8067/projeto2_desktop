package org.example.controllers.adicionar;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.*;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
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
    RoupaDasEncomendas roupaDasEncomendas;
    RoupaDasEncomendasDao roupaDasEncomendasDao;

    private List<Roupa> verificaRoupaList;
    private List<Roupa> verificaTamanhoRoupaList;

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
    private Label lblAdicionaEstado;

    @FXML
    void mIEstadoEmPreparacao(ActionEvent event) {
        lblAdicionaEstado.setText(mIIdEstadoEmPreparacao.getText());
    }

    @FXML
    void mIEstadoEnviado(ActionEvent event) {
        lblAdicionaEstado.setText(mIIdEstadoEnviado.getText());
    }

    @FXML
    void mIEstadoFinalizado(ActionEvent event) {
        lblAdicionaEstado.setText(mIIdEstadoFinalizado.getText());
    }

    //todo verificar porque nao adiciona e repete quando o pedido e do mesmo tipo e tamanho
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
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE) && (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getEstadoUtilizador().equals(EstadoUtilizador.ATIVO))) {
            labelIdErroNomeCliente.setText("Este Cliente não está Ativo!!! Introduza novamente!");
        } else {
            this.encomenda.setUtilizador(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()));
            labelIdErroNomeCliente.setText("");
        }

        if (cBIdTipoRoupa.getValue() == null && cBIdTamanhoRoupa.getValue() == null) {
            labelIdErroTipoRoupa.setText("Tem de preencher o Tipo de Roupa.");
            labelIdErroTamanho.setText("Tem de preencher o Tamanho de Roupa.");
        } else if (!verificaTipoTamanhoStock()) {
            labelIdErroTipoRoupa.setText("Não está em Stock! ");
            labelIdErroTamanho.setText("Não está em Stock!");
        } else {
            //todo atualizar stock roupa
            this.roupaDasEncomendas.setTipoRoupa(cBIdTipoRoupa.getValue());
            this.roupaDasEncomendas.setTamanhoRoupa(cBIdTamanhoRoupa.getValue());
            labelIdErroTipoRoupa.setText("");
            labelIdErroTamanho.setText("");
        }

        //TODO - FALTA A DATA

        if (txtFdIdQtd.getText().isEmpty()) {
            labelIdErroQuantidade.setText("Tem de preencher a Quantidade.");
        } else if (verificaQtd == 0) {
            labelIdErroQuantidade.setText("Preencha corretamente a Quantidade!");
        } else if (!verificaQuantidadeStock()) {
            labelIdErroQuantidade.setText("Quantidade ultrapassa o total em Stock!");
        } else {
            this.linhaEncomenda.setQuantidade(Integer.valueOf(txtFdIdQtd.getText()));
            labelIdErroQuantidade.setText("");
        }

        if (txtFIdFornecedor.getText().isEmpty()) {
            labelIdErroFornecedor.setText("Tem de preencher o Nome do Utilizador do Fornecedor.");
        } else if (this.fornecedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()) == null) {
            labelIdErroFornecedor.setText("Este Fornecedor não existe!");
        } else {
            this.encomenda.setFornecedor(this.fornecedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()));
            labelIdErroFornecedor.setText("");
        }

        if (lblAdicionaEstado.getText().isEmpty()) {
            labelIdErroEstadoEnc.setText("Tem de preencher o estado da Encomenda.");
        } else if (lblAdicionaEstado.getText().equals("Em Preparacao")) {
            this.encomenda.setEstadoEncomenda(EstadoEncomenda.EMPREPARACAO);
            labelIdErroEstadoEnc.setText("");
        } else if (lblAdicionaEstado.getText().equals("Enviado")) {
            this.encomenda.setEstadoEncomenda(EstadoEncomenda.ENVIADO);
            labelIdErroEstadoEnc.setText("");
        } else if (lblAdicionaEstado.getText().equals("Finalizado")) {
            this.encomenda.setEstadoEncomenda(EstadoEncomenda.FINALIZADO);
            labelIdErroEstadoEnc.setText("");
        }

        if (labelIdErroNomeCliente.getText().equals("") && labelIdErroTipoRoupa.getText().equals("") &&
                labelIdErroTamanho.getText().equals("") && labelIdErroFornecedor.getText().equals("") &&
                labelIdErroEstadoEnc.getText().equals("")) {
            this.encomenda.setLinha_encomenda(this.linhaEncomenda);

            this.encomendaDao.registar(this.encomenda);
            this.linhaEncomendaDao.registar(this.linhaEncomenda);

            this.roupaDasEncomendas.setLinha_encomenda(this.linhaEncomenda);
            this.roupaDasEncomendasDao.registar(this.roupaDasEncomendas);


            Integer diferenca = 0;
            for (Roupa r : this.verificaRoupaList) {
                if (r.getTipoRoupa().equals(cBIdTipoRoupa.getValue()) && r.getTamanhoRoupa().equals(cBIdTamanhoRoupa.getValue())) {
                    diferenca = r.getStock();
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println(diferenca);
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    //r.setStock(r.getStock() - Integer.parseInt(txtFdIdQtd.getText()));
                    diferenca -= Integer.parseInt(txtFdIdQtd.getText());

                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println( diferenca);
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                    //r.setStock(diferenca);
                    //this.roupaDao.atualizar(r);
                    //this.roupaDao.atualizarRoupaEmPedidos(r.getIdRoupa(), diferenca);
                    //return Collections.singletonList(r);
                }
            }

            for (Roupa r : this.verificaRoupaList) {
                if (r.getTipoRoupa().equals(cBIdTipoRoupa.getValue()) && r.getTamanhoRoupa().equals(cBIdTamanhoRoupa.getValue())) {
                    this.roupaDao.atualizarRoupaEmPedidos(r.getIdRoupa(), diferenca);
                }
            }

            this.goToUtil.goToHomePageAdminDeTabPedidos();
            Stage stage = (Stage) btnIdAdicionar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        this.goToUtil.goToHomePageAdminDeTabPedidos();
        Stage stage = (Stage) btnIdVoltar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cBIdTipoRoupa.getItems().addAll(TipoRoupa.values());
        cBIdTamanhoRoupa.getItems().addAll(TamanhoRoupa.values());

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
        this.roupaDasEncomendas = new RoupaDasEncomendas();
        this.roupaDasEncomendasDao = new RoupaDasEncomendasDao(entityManager);

        this.verificaRoupaList = this.roupaDao.buscarTodas();
        this.verificaTamanhoRoupaList = this.roupaDao.buscarPorTamanhoRoupa(cBIdTamanhoRoupa.getValue());
    }

    private Boolean verificaTipoTamanhoStock() {
        for (Roupa r : this.verificaRoupaList) {
            if (cBIdTipoRoupa.getValue().equals(r.getTipoRoupa()) && cBIdTamanhoRoupa.getValue().equals(r.getTamanhoRoupa())) {
                return true;
            }
        }
        return false;
    }

    private Boolean verificaQuantidadeStock() {
        for (Roupa r : this.verificaRoupaList) {
            if (cBIdTipoRoupa.getValue().equals(r.getTipoRoupa()) && cBIdTamanhoRoupa.getValue().equals(r.getTamanhoRoupa()) && Integer.parseInt(txtFdIdQtd.getText()) < r.getStock()) {
                return true;
            }
        }
        return false;
    }

}
