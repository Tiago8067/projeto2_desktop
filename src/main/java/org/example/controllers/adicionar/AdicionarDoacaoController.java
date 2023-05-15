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
import org.example.models.enums.CategoriaRoupa;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.example.models.enums.TipoUtilizador;
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

        if (this.roupaList.size() == 0) {
            if (txtFdIdNomeCliente.getText().isEmpty()) {
                labelIdErroNomeCliente.setText("Tem de preencher o Nome Completo.");
            } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()) == null) {
                labelIdErroNomeCliente.setText("Este utilizador não existe!");
            } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
                labelIdErroNomeCliente.setText("Este utilizador não é um Cliente!!! Introduza novamente!");
            } else {
                this.doacao.setUtilizador(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()));
                labelIdErroNomeCliente.setText("");
            }

            if (txtFdIdQtd.getText().equals("")) {
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

            this.roupa.setStock(Integer.valueOf(txtFdIdQtd.getText()));

            if (labelIdErroNomeCliente.getText().equals("") && labelIdErroTipoRoupa.getText().equals("") &&
                    labelIdErroTamanho.getText().equals("") && labelIdErroQuantidade.getText().equals("")) {
                this.roupa_doacao.setRoupa(this.roupa);
                this.roupa_doacao.setDoacao(this.doacao);

                this.doacaoDao.registar(this.doacao);
                this.roupa_doacaoDao.registar(this.roupa_doacao);
                this.roupaDao.registar(this.roupa);
                this.goToUtil.goToHomePageAdmin();
                Stage stage = (Stage) btnIdAdicionar.getScene().getWindow();
                stage.close();
            }
        } else {
            for (Roupa r: this.roupaList) {
                if (r.getTipoRoupa().equals(cBIdTipoRoupa.getValue()) && r.getTamanhoRoupa().equals(cBIdTamanhoRoupa.getValue())) {
                    if (txtFdIdNomeCliente.getText().isEmpty()) {
                        labelIdErroNomeCliente.setText("Tem de preencher o Nome Completo.");
                    } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()) == null) {
                        labelIdErroNomeCliente.setText("Este utilizador não existe!");
                    } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
                        labelIdErroNomeCliente.setText("Este utilizador não é um Cliente!!! Introduza novamente!");
                    } else {
                        this.doacao.setUtilizador(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()));
                        labelIdErroNomeCliente.setText("");
                    }

                    if (txtFdIdQtd.getText().equals("")) {
                        labelIdErroQuantidade.setText("Tem de preencher a Quantidade.");
                    } else if (verificaQtd == 0) {
                        labelIdErroQuantidade.setText("Preencha corretamente a Quantidade!");
                    } else {
                        this.roupa_doacao.setQuantidade(Integer.valueOf(txtFdIdQtd.getText()));
                        labelIdErroQuantidade.setText("");
                    }

                    // TODO - Falta a Data de Doação

                    r.setStock(r.getStock() + this.roupa_doacao.getQuantidade());
                    //TODO - TABELA STOCK A PARTE (IDEIA)

                    if (labelIdErroNomeCliente.getText().equals("") && labelIdErroQuantidade.getText().equals("")) {
                        this.roupa_doacao.setRoupa(r);
                        this.roupa_doacao.setDoacao(this.doacao);

                        this.doacaoDao.registar(this.doacao);
                        this.roupa_doacaoDao.registar(this.roupa_doacao);
                        this.roupaDao.registar(r);
                        //return; // duplica a linha na roupa
                        //this.roupaDao.atualizarStock(r.getIdRoupa(), Integer.valueOf(txtFdIdQtd.getText()));
                    }
                    break;
                    //todo - resolver depoism situacao da duplicacao
                } else {
                    if (txtFdIdNomeCliente.getText().isEmpty()) {
                        labelIdErroNomeCliente.setText("Tem de preencher o Nome Completo.");
                    } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()) == null) {
                        labelIdErroNomeCliente.setText("Este utilizador não existe!");
                    } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
                        labelIdErroNomeCliente.setText("Este utilizador não é um Cliente!!! Introduza novamente!");
                    } else {
                        this.doacao.setUtilizador(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()));
                        labelIdErroNomeCliente.setText("");
                    }

                    if (txtFdIdQtd.getText().equals("")) {
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

                    this.roupa.setStock(Integer.valueOf(txtFdIdQtd.getText()));

                    if (labelIdErroNomeCliente.getText().equals("") && labelIdErroTipoRoupa.getText().equals("") &&
                            labelIdErroTamanho.getText().equals("") && labelIdErroQuantidade.getText().equals("")) {
                        this.roupa_doacao.setRoupa(this.roupa);
                        this.roupa_doacao.setDoacao(this.doacao);

                        this.doacaoDao.registar(this.doacao);
                        this.roupa_doacaoDao.registar(this.roupa_doacao);
                        this.roupaDao.registar(this.roupa);
                    }
                }
            }
            this.goToUtil.goToHomePageAdmin();
            Stage stage = (Stage) btnIdAdicionar.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        System.out.println(this.roupaList);
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
    }

    private CategoriaRoupa adicionarAssociarCategoria() {
        if (cBIdTipoRoupa.getValue().equals(TipoRoupa.CALCOES) || cBIdTipoRoupa.getValue().equals(TipoRoupa.BERMUDAS) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CALCAS) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.SAIA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.MEIAS) || cBIdTipoRoupa.getValue().equals(TipoRoupa.MEIACALCA)) {
            //this.roupa.setCategoriaRoupa(CategoriaRoupa.PARTEDEBAIXO);
            return CategoriaRoupa.PARTEDEBAIXO;
        } else if (cBIdTipoRoupa.getValue().equals(TipoRoupa.BLUSA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.VESTIDO) || cBIdTipoRoupa.getValue().equals(TipoRoupa.SWEAT) ||
                cBIdTipoRoupa.getValue().equals(TipoRoupa.T_SHIRT) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CAMISA) || cBIdTipoRoupa.getValue().equals(TipoRoupa.CASACO) || cBIdTipoRoupa.getValue().equals(TipoRoupa.COLETE)) {
            //this.roupa.setCategoriaRoupa(CategoriaRoupa.PARTEDECIMA);
            return CategoriaRoupa.PARTEDECIMA;
        } else {
            //this.roupa.setCategoriaRoupa(CategoriaRoupa.ACESSORIOS);
            return CategoriaRoupa.ACESSORIOS;
        }
    }

    private String adicionarAssociarImagem() {
        switch (cBIdTipoRoupa.getValue()) {
            case CALCAS:
                //this.roupa.setImageSrc("/resources/images/calcas.jpg");
                return "/images/calcas.jpg";
            //break;
            case CALCOES:
                //this.roupa.setImageSrc("/resources/images/calcoes.jpg");
                return "/images/calcoes.jpg";
            //break;
            case BERMUDAS:
                //this.roupa.setImageSrc("/resources/images/bermudas.jpg");
                return "/images/bermudas.jpg";
            //break;
            case VESTIDO:
                //this.roupa.setImageSrc("/resources/images/vestido.jpg");
                return "/images/vestido.jpg";
            //break;
            case SAIA:
                //this.roupa.setImageSrc("/resources/images/saia.jpg");
                return "/images/saia.jpg";
            //break;
            case BLUSA:
                //this.roupa.setImageSrc("/resources/images/blusa.jpg");
                return "/images/blusa.jpg";
            //break;
            case SWEAT:
                //this.roupa.setImageSrc("/resources/images/sweat.jpg");
                return "/images/sweat.jpg";
            //break;
            case T_SHIRT:
                //this.roupa.setImageSrc("/resources/images/tshirt.jpg");
                return "/images/tshirt.jpg";
            //break;
            case CAMISA:
                //this.roupa.setImageSrc("/resources/images/camisa.jpg");
                return "/images/camisa.jpg";
            //break;
            case CASACO:
                //this.roupa.setImageSrc("/resources/images/casaco.jpg");
                return "/images/casaco.jpg";
            //break;
            case COLETE:
                //this.roupa.setImageSrc("/resources/images/colete.jpg");
                return "/images/colete.jpg";
            //break;
            case MEIACALCA:
                //this.roupa.setImageSrc("/resources/images/meia_calca.jpg");
                return "/images/meia_calca.jpg";
            //break;
            case MEIAS:
                //this.roupa.setImageSrc("/resources/images/meias.jpg");
                return "/images/meias.jpg";
            //break;
            case SAPATOSCLASSICO:
                //this.roupa.setImageSrc("/resources/images/sapato_classico.jpg");
                return "/images/sapato_classico.jpg";
            //break;
            case SAPATOSDESPORTIVO:
                //this.roupa.setImageSrc("/resources/images/sapato_desportivo.jpg");
                return "/images/sapato_desportivo.jpg";
            //break;
            case BOLSA:
                //this.roupa.setImageSrc("/resources/images/bolsa.jpg")
                return "/images/bolsa.jpg";
            //break;
        }
        return null;
    }

    private void registaRoupa() {

    }
}
