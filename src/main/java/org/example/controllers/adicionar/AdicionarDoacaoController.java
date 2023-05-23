package org.example.controllers.adicionar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dao.*;
import org.example.models.Doacao;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;
import org.example.models.enums.*;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdicionarDoacaoController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    Doacao doacao;
    Roupa_Doacao roupa_doacao;
    Roupa roupa;
    GoToUtil goToUtil;
    DoacaoDao doacaoDao;
    Roupa_DoacaoDao roupa_doacaoDao;
    RoupaDao roupaDao;
    private List<Roupa> roupaList;
    private List<Roupa> roupaListTipo;
    private List<Roupa> roupaListTamanho;
    private List<Roupa> atualizaStock;
    @FXML
    private Button btnIdAdicionar;
    @FXML
    private Button btnIdVoltar;
    @FXML
    private ChoiceBox<TamanhoRoupa> cBIdTamanhoRoupa;
    @FXML
    private ChoiceBox<TipoRoupa> cBIdTipoRoupa;
    @FXML
    private Label labelIdErroNomeCliente;
    @FXML
    private Label labelIdErroQuantidade;
    @FXML
    private Label labelIdErroTamanho;
    @FXML
    private Label labelIdErroTipoRoupa;
    @FXML
    private TextField txtFdIdNomeCliente;
    @FXML
    private TextField txtFdIdQtd;

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
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE) && (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getEstadoUtilizador().equals(EstadoUtilizador.ATIVO))) {
            labelIdErroNomeCliente.setText("Este Cliente não está Ativo!!! Introduza novamente!");
        } else {
            this.doacao.setUtilizador(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()));
            labelIdErroNomeCliente.setText("");
        }

        if (txtFdIdQtd.getText().isEmpty()) {
            labelIdErroQuantidade.setText("Tem de preencher a Quantidade.");
        } else if (verificaQtd == 0) {
            labelIdErroQuantidade.setText("Preencha corretamente a Quantidade!");
        } else {
            this.roupa_doacao.setQuantidade(Integer.valueOf(txtFdIdQtd.getText()));
            labelIdErroQuantidade.setText("");
        }

        // TODO - Falta a Data de Doação

        if (cBIdTipoRoupa.getValue() == null) {
            labelIdErroTipoRoupa.setText("Tem de preencher o Tipo de Roupa.");
        } else {
            this.roupa.setImageSrc(adicionarAssociarImagem());
            this.roupa.setCategoriaRoupa(adicionarAssociarCategoria());
            this.roupa.setTipoRoupa(cBIdTipoRoupa.getValue());
            labelIdErroTipoRoupa.setText("");
        }

        if (cBIdTamanhoRoupa.getValue() == null) {
            labelIdErroTamanho.setText("Tem de preencher o Tamanho de Roupa.");
        } else {
            this.roupa.setTamanhoRoupa(cBIdTamanhoRoupa.getValue());
            labelIdErroTamanho.setText("");
        }

        if (labelIdErroNomeCliente.getText().equals("") && labelIdErroTipoRoupa.getText().equals("") &&
                labelIdErroTamanho.getText().equals("") && labelIdErroQuantidade.getText().equals("")) {
            this.roupa.setRoupa_doacao(this.roupa_doacao);
            this.doacao.setRoupa_doacao(this.roupa_doacao);

            this.doacaoDao.registar(this.doacao);
            this.roupa_doacaoDao.registar(this.roupa_doacao);
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
        cBIdTipoRoupa.getItems().addAll(TipoRoupa.values());
        cBIdTamanhoRoupa.getItems().addAll(TamanhoRoupa.values());

        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.doacao = new Doacao();
        this.roupa_doacao = new Roupa_Doacao();
        this.roupa = new Roupa();
        this.goToUtil = new GoToUtil();
        this.doacaoDao = new DoacaoDao(entityManager);
        this.roupa_doacaoDao = new Roupa_DoacaoDao(entityManager);
        this.roupaDao = new RoupaDao(entityManager);
        this.roupaList = this.roupaDao.buscarTodas();
        this.roupaListTipo = this.roupaDao.buscarPorTipoRoupa(cBIdTipoRoupa.getValue());
        this.roupaListTamanho = this.roupaDao.buscarPorTamanhoRoupa(cBIdTamanhoRoupa.getValue());
        //this.atualizaStock = this.roupaDao.buscarPorTipoTamanhoRoupa(cBIdTipoRoupa.getValue(), cBIdTamanhoRoupa.getValue());
    }

    private CategoriaRoupa adicionarAssociarCategoria() {
        if (cBIdTipoRoupa.getValue().equals(TipoRoupa.CALCOES) || cBIdTipoRoupa.getValue().equals(TipoRoupa.BERMUDAS) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CALCAS) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.SAIA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.MEIAS) || cBIdTipoRoupa.getValue().equals(TipoRoupa.MEIACALCA)) {
            return CategoriaRoupa.PARTEDEBAIXO;
        } else if (cBIdTipoRoupa.getValue().equals(TipoRoupa.BLUSA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.VESTIDO) || cBIdTipoRoupa.getValue().equals(TipoRoupa.SWEAT) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.T_SHIRT) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CAMISA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CASACO) || cBIdTipoRoupa.getValue().equals(TipoRoupa.COLETE)) {
            return CategoriaRoupa.PARTEDECIMA;
        } else {
            return CategoriaRoupa.ACESSORIOS;
        }
    }

    private String adicionarAssociarImagem() {
        return switch (cBIdTipoRoupa.getValue()) {
            case CALCAS -> "/images/calcas.jpg";
            case CALCOES -> "/images/calcoes.jpg";
            case BERMUDAS -> "/images/bermudas.jpg";
            case VESTIDO -> "/images/vestido.jpg";
            case SAIA -> "/images/saia.jpg";
            case BLUSA -> "/images/blusa.jpg";
            case SWEAT -> "/images/sweat.jpg";
            case T_SHIRT -> "/images/tshirt.jpg";
            case CAMISA -> "/images/camisa.jpg";
            case CASACO -> "/images/casaco.jpg";
            case COLETE -> "/images/colete.jpg";
            case MEIACALCA -> "/images/meia_calca.jpg";
            case MEIAS -> "/images/meias.jpg";
            case SAPATOSCLASSICO -> "/images/sapato_classico.jpg";
            case SAPATOSDESPORTIVO -> "/images/sapato_desportivo.jpg";
            case BOLSA -> "/images/bolsa.jpg";
        };
    }
}
