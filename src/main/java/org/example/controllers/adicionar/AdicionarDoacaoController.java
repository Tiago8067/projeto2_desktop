package org.example.controllers.adicionar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.controllers.LoginController;
import org.example.dao.*;
import org.example.models.Doacao;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;
import org.example.models.Utilizador;
import org.example.models.enums.*;
import org.example.modelsHelp.LinhaRoupa;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.Verificacoes;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDate;
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
    Verificacoes verificacoes;
    String guardaUsernameLogin = LoginController.usernameGuardado;

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
        } else if (this.verificacoes.verficaInteiro(verificaQtd, txtFdIdQtd.getText()) == 0) {
            labelIdErroQuantidade.setText("Preencha corretamente a Quantidade!");
        } else {
            this.roupa_doacao.setQuantidade(Integer.valueOf(txtFdIdQtd.getText()));
            labelIdErroQuantidade.setText("");
        }

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
            this.doacao.setDataDaDoacao(LocalDate.now());

            this.doacaoDao.registar(this.doacao);
            this.roupa_doacaoDao.registar(this.roupa_doacao);
            this.roupaDao.registar(this.roupa);

            for (Roupa roupas : this.roupaDao.buscarPorTipoTamanhoRoupa(this.roupa)) {
                roupas.setStock(retornaSoma());
                this.roupaDao.registar(roupas);
            }

            retornaParaHomePageCorreto(btnIdAdicionar);
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        retornaParaHomePageCorreto(btnIdVoltar);
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
        this.verificacoes = new Verificacoes();
    }

    private CategoriaRoupa adicionarAssociarCategoria() {
        if (cBIdTipoRoupa.getValue().equals(TipoRoupa.CALCOES) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.CALCAS) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.SAIA) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.MEIAS) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.MEIACALCA)) {
            return CategoriaRoupa.PARTEDEBAIXO;
        } /*else if (cBIdTipoRoupa.getValue().equals(TipoRoupa.BLUSA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.VESTIDO) || cBIdTipoRoupa.getValue().equals(TipoRoupa.SWEAT) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.T_SHIRT) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CAMISA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CASACO) || cBIdTipoRoupa.getValue().equals(TipoRoupa.COLETE)) {
            return CategoriaRoupa.PARTEDECIMA;
        } else {
            return CategoriaRoupa.ACESSORIOS;
        }*/else{
            return CategoriaRoupa.PARTEDECIMA;
        }
    }

    private String adicionarAssociarImagem() {
        return switch (cBIdTipoRoupa.getValue()) {
            case CALCAS -> "/images/calcas.jpg";
            case CALCOES -> "/images/calcoes.jpg";
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
        };
    }

    private void retornaParaHomePageCorreto(Button button) {
        for (Utilizador u : this.utilizadorDao.buscarTodos()) {
            if (u.getUsername().equals(guardaUsernameLogin)) {
                if (u.getTipoUtilizador().equals(TipoUtilizador.ADMIN)) {
                    this.goToUtil.goToHomePageAdmin();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                } else {
                    this.goToUtil.goToHomePageFuncionario();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    private Integer retornaSoma() {
        int soma = 0;
        for (LinhaRoupa linhaRoupa : this.roupaDao.buscarDadosParaStock()) {
            if (linhaRoupa.getTipoRoupa().equals(String.valueOf(cBIdTipoRoupa.getValue())) && linhaRoupa.getTamanhoRoupa().equals(String.valueOf(cBIdTamanhoRoupa.getValue()))) {
                soma = soma + linhaRoupa.getQuantidade();
            }
        }
        return soma;
    }
}
