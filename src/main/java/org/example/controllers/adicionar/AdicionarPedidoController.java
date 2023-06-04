package org.example.controllers.adicionar;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.controllers.LoginController;
import org.example.dao.*;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.Verificacoes;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDate;
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
    Verificacoes verificacoes;
    String guardaUsernameLogin = LoginController.usernameGuardado;

    private List<Roupa> verificaRoupaList;

    @FXML
    private Button btnIdAdicionar;
    @FXML
    private Button btnIdVoltar;
    @FXML
    private ChoiceBox<TipoRoupa> cBIdTipoRoupa;
    @FXML
    private ChoiceBox<TamanhoRoupa> cBIdTamanhoRoupa;
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
    void btnAdicionar(ActionEvent event) {
        int verificaQtd = 0;

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

        if (txtFdIdQtd.getText().isEmpty()) {
            labelIdErroQuantidade.setText("Tem de preencher a Quantidade.");
        } else if (this.verificacoes.verficaInteiro(verificaQtd, txtFdIdQtd.getText()) == 0) {
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

        if (labelIdErroNomeCliente.getText().equals("") && labelIdErroTipoRoupa.getText().equals("") &&
                labelIdErroTamanho.getText().equals("") && labelIdErroFornecedor.getText().equals("")) {
            this.encomenda.setLinha_encomenda(this.linhaEncomenda);
            this.encomenda.setDataDePedido(LocalDate.now());
            this.encomenda.setEstadoEncomenda(EstadoEncomenda.EMPREPARACAO);

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

            retornaParaHomePageCorreto();
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        retornaParaHomePageCorreto();
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
        this.verificacoes = new Verificacoes();

        this.verificaRoupaList = this.roupaDao.buscarTodas();
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

    private void retornaParaHomePageCorreto() {
        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(guardaUsernameLogin)) {
                if (u.getTipoUtilizador().equals(TipoUtilizador.ADMIN)) {
                    this.goToUtil.goToHomePageAdmin();
                    Stage stage = (Stage) btnIdVoltar.getScene().getWindow();
                    stage.close();
                } else {
                    this.goToUtil.goToHomePageFuncionario();
                    Stage stage = (Stage) btnIdVoltar.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }
}
