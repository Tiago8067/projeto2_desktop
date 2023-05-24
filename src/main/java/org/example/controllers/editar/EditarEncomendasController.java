package org.example.controllers.editar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.*;
import org.example.models.*;
import org.example.models.enums.*;
import org.example.modelsHelp.LinhaEncomendas;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditarEncomendasController implements Initializable {
    LinhaEncomendas linhaEncomendas;
    GoToUtil goToUtil;
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    RoupaDao roupaDao;
    FornecedorDao fornecedorDao;
    EncomendaDao encomendaDao;
    LinhaEncomendaDao linhaEncomendaDao;
    LinhaEncomenda linhaEncomenda;
    RoupaDasEncomendasDao roupaDasEncomendasDao;
    private List<Roupa> verificaRoupaList;
    @FXML
    private Button btnIdAdicionar;
    @FXML
    private Button btnIdVoltar;
    @FXML
    private Button btnApagar;
    @FXML
    private ChoiceBox<TamanhoRoupa> cBIdTamanhoRoupa;
    @FXML
    private ChoiceBox<TipoRoupa> cBIdTipoRoupa;
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
    private Label lblAdicionaEstado;
    @FXML
    private MenuItem mIIdEstadoEmPreparacao;
    @FXML
    private MenuItem mIIdEstadoEnviado;
    @FXML
    private MenuItem mIIdEstadoFinalizado;
    @FXML
    private TextField txtFIdFornecedor;
    @FXML
    private TextField txtFdIdNomeCliente;
    @FXML
    private TextField txtFdIdQtd;

    @FXML
    void btnApagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem a certeza que quer Eliminar esta Doacao?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            for (Encomenda e : this.encomendaDao.buscarTodas()) {
                if (e.getIdEncomenda() == this.linhaEncomendas.getIdEncomenda().getValue()) {
                    this.encomendaDao.apagarEncomenda(e.getIdEncomenda());
                }
            }

            for (RoupaDasEncomendas r : this.roupaDasEncomendasDao.buscarTodas()) {
                if (r.getLinha_encomenda().getIdLinhaEncomenda() == this.encomendaDao.buscarPorId(this.linhaEncomendas.getIdEncomenda().getValue()).getLinha_encomenda().getIdLinhaEncomenda()) {
                    this.roupaDasEncomendasDao.apagarRoupaDasEncomendas(r.getIdRoupaDasEncomendas());
                }
            }

            for (LinhaEncomenda ld : this.linhaEncomendaDao.buscarTodas()) {
                /*this.linhaEncomendas.getIdLinhaEncomenda().getValue())*/
                if (ld.getIdLinhaEncomenda() == this.encomendaDao.buscarPorId(this.linhaEncomendas.getIdEncomenda().getValue()).getLinha_encomenda().getIdLinhaEncomenda()) {
                    this.linhaEncomendaDao.apagarLinhaEncomenda(ld.getIdLinhaEncomenda());
                }
            }
            /*for (Roupa r : this.roupaDao.buscarTodas()) {
                if (r.getLinha_encomenda().getIdLinhaEncomenda() == this.encomendaDao.buscarPorId(this.linhaEncomendas.getIdEncomenda().getValue()).getLinha_encomenda().getIdLinhaEncomenda()) {
                    r.setLinha_encomenda(null);
                    this.roupaDao.registar(r);
                }
            }*/

            this.goToUtil.goToHomePageAdminDeTabPedidos();
            Stage stage = (Stage) btnApagar.getScene().getWindow();
            stage.close();
        }
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
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE) && (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getEstadoUtilizador().equals(EstadoUtilizador.ATIVO))) {
            labelIdErroNomeCliente.setText("Este Cliente não está Ativo!!! Introduza novamente!");
        } else {
            labelIdErroNomeCliente.setText("");
        }

        //todo - falta vberificar o estado do cliente

        if (cBIdTipoRoupa.getValue() == null && cBIdTamanhoRoupa.getValue() == null) {
            labelIdErroTipoRoupa.setText("Tem de preencher o Tipo de Roupa.");
            labelIdErroTamanho.setText("Tem de preencher o Tamanho de Roupa.");
        } else if (!verificaTipoTamanhoStock()) {
            labelIdErroTipoRoupa.setText("Não está em Stock! ");
            labelIdErroTamanho.setText("Não está em Stock!");
        } else {
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
            labelIdErroQuantidade.setText("");
        }

        if (txtFIdFornecedor.getText().isEmpty()) {
            labelIdErroFornecedor.setText("Tem de preencher o Nome do Utilizador do Fornecedor.");
        } else if (this.fornecedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()) == null) {
            labelIdErroFornecedor.setText("Este Fornecedor não existe!");
        } else {
            labelIdErroFornecedor.setText("");
        }

        if (lblAdicionaEstado.getText().isEmpty()) {
            labelIdErroEstadoEnc.setText("Tem de preencher o estado da Encomenda.");
        } else {
            labelIdErroEstadoEnc.setText("");
        }

        if (labelIdErroNomeCliente.getText().equals("") && labelIdErroTipoRoupa.getText().equals("") &&
                labelIdErroTamanho.getText().equals("") && labelIdErroFornecedor.getText().equals("") &&
                labelIdErroEstadoEnc.getText().equals("") && labelIdErroQuantidade.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Tem a certeza que deseja Editar esta Doacao?");
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.get() == ButtonType.OK) {

                for (Encomenda e : this.encomendaDao.buscarTodas()) {
                    if (e.getIdEncomenda() == this.linhaEncomendas.getIdEncomenda().getValue()) {
                        if (lblAdicionaEstado.getText().equals("Em Preparacao")) {
                            this.encomendaDao.atualizarEncomenda(String.valueOf(EstadoEncomenda.EMPREPARACAO), e.getIdEncomenda());
                        } else if (lblAdicionaEstado.getText().equals("Enviado")) {
                            this.encomendaDao.atualizarEncomenda(String.valueOf(EstadoEncomenda.ENVIADO), e.getIdEncomenda());
                        } else if (lblAdicionaEstado.getText().equals("Finalizado")) {
                            this.encomendaDao.atualizarEncomenda(String.valueOf(EstadoEncomenda.FINALIZADO), e.getIdEncomenda());
                        }
                        this.encomendaDao.atualizarFornecedorEncomenda(this.fornecedorDao.buscarFornecedorPorNome(txtFIdFornecedor.getText()).getIdFornecedor(), e.getIdEncomenda());
                        this.encomendaDao.atualizarUtilizadorEncomenda(this.utilizadorDao.buscarUtilizadorPorUsername(txtFdIdNomeCliente.getText()).getIdUtilizador(), e.getIdEncomenda());
                    }
                }

                for (LinhaEncomenda ld : this.linhaEncomendaDao.buscarTodas()) {
                    if (ld.getIdLinhaEncomenda() == this.linhaEncomendas.getIdLinhaEncomenda().getValue()) {
                        this.linhaEncomendaDao.atualizarLinha_Encomenda(Integer.parseInt(txtFdIdQtd.getText()), ld.getIdLinhaEncomenda());
                    }
                }

                for (RoupaDasEncomendas r : this.roupaDasEncomendasDao.buscarTodas()) {
                    if (r.getIdRoupaDasEncomendas() == this.linhaEncomendas.getIdRoupa().getValue()) {
                        this.roupaDasEncomendasDao.atualizarRoupaDasEncomendas(String.valueOf(cBIdTipoRoupa.getValue()), String.valueOf(cBIdTamanhoRoupa.getValue()), r.getIdRoupaDasEncomendas());
                    }
                }

                /*todo atualizar stock*/

                /*for (Roupa r: this.roupaDao.buscarTodas()) {
                    if(r.getIdRoupa() == this.linhaEncomendas.getIdRoupa().getValue()){
                        this.roupaDao.atualizarRoupaEncomendas(this.linhaEncomendas.getIdLinhaEncomenda().getValue(), r.getIdRoupa());
                    }
                }*/

                this.goToUtil.goToHomePageAdminDeTabPedidos();
                Stage stage = (Stage) btnIdAdicionar.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        this.goToUtil.goToHomePageAdminDeTabPedidos();
        Stage stage = (Stage) btnIdVoltar.getScene().getWindow();
        stage.close();
    }

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


    public void setEncomenda(LinhaEncomendas linhaEncomendas) {
        this.linhaEncomendas = linhaEncomendas;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cBIdTipoRoupa.getItems().addAll(TipoRoupa.values());
        cBIdTamanhoRoupa.getItems().addAll(TamanhoRoupa.values());

        this.entityManager = JPAUtil.getEntityManager();
        this.linhaEncomendas = new LinhaEncomendas();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.roupaDao = new RoupaDao(entityManager);
        this.goToUtil = new GoToUtil();
        this.verificaRoupaList = this.roupaDao.buscarTodas();
        this.fornecedorDao = new FornecedorDao(entityManager);
        this.encomendaDao = new EncomendaDao(entityManager);
        this.linhaEncomendaDao = new LinhaEncomendaDao(entityManager);
        this.linhaEncomenda = new LinhaEncomenda();
        this.roupaDasEncomendasDao = new RoupaDasEncomendasDao(entityManager);
    }

    public void passarDadosEncomendasEditar() {
        txtFdIdNomeCliente.setText(String.valueOf(this.linhaEncomendas.getUsernameCliente().getValue()));
        cBIdTipoRoupa.setValue(TipoRoupa.valueOf(this.linhaEncomendas.getTipoRoupa().getValue()));
        cBIdTamanhoRoupa.setValue(TamanhoRoupa.valueOf(this.linhaEncomendas.getTamanhoRoupa().getValue()));
        txtFdIdQtd.setText(String.valueOf(this.linhaEncomendas.getQuantidade().getValue()));
        txtFIdFornecedor.setText(String.valueOf(this.linhaEncomendas.getUsernameFonecedor().getValue()));
        lblAdicionaEstado.setText(String.valueOf(this.linhaEncomendas.getEstado().getValue()));
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
