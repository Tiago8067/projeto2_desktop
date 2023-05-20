package org.example.controllers.editar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dadosTableView.LinhaDoacoes;
import org.example.dao.DoacaoDao;
import org.example.dao.RoupaDao;
import org.example.dao.Roupa_DoacaoDao;
import org.example.dao.UtilizadorDao;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;
import org.example.models.Utilizador;
import org.example.models.enums.TamanhoRoupa;
import org.example.models.enums.TipoRoupa;
import org.example.models.enums.TipoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditarDoacoesController implements Initializable {
    LinhaDoacoes linhaDoacoes;
    GoToUtil goToUtil;
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    DoacaoDao doacaoDao;
    Roupa_DoacaoDao roupa_doacaoDao;
    RoupaDao roupaDao;
    @FXML
    private TextField txtFdUpdateUsernameCliente;
    @FXML
    private ChoiceBox<TipoRoupa> cBUpdateTipoRoupa;
    @FXML
    private ChoiceBox<TamanhoRoupa> cBUpdateTamanhoRoupa;
    @FXML
    private TextField txtFdUpdateQtd;
    @FXML
    private Label lblErroNomeClienteUpdate;
    @FXML
    private Label lblErroTipoRoupaUpdate;
    @FXML
    private Label lblErroTamanhoUpdate;
    @FXML
    private Label lblErroQuantidadeUpdate;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnApagar;
    @FXML
    private Button btnVoltar;

    @FXML
    void btnAtualizar(ActionEvent event) {
        int verificaQtd = 0;

        try {
            verificaQtd = Integer.parseInt(txtFdUpdateQtd.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        if (txtFdUpdateUsernameCliente.getText().isEmpty()) {
            lblErroNomeClienteUpdate.setText("Tem de preencher o Nome Completo.");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdUpdateUsernameCliente.getText()) == null) {
            lblErroNomeClienteUpdate.setText("Este utilizador não existe!");
        } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdUpdateUsernameCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
            lblErroNomeClienteUpdate.setText("Este utilizador não é um Cliente!!! Introduza novamente!");
        } else {
            //this.linhaDoacoes.setUsername(txtFdUpdateUsernameCliente.getText());
            lblErroNomeClienteUpdate.setText("");
        }

        if (txtFdUpdateQtd.getText().equals("")) {
            lblErroQuantidadeUpdate.setText("Tem de preencher a Quantidade.");
        } else if (verificaQtd == 0) {
            lblErroQuantidadeUpdate.setText("Preencha corretamente a Quantidade!");
        } else {
            //this.linhaDoacoes.setQuantidade(Integer.parseInt(txtFdUpdateQtd.getText()));
            lblErroQuantidadeUpdate.setText("");
        }

        // TODO - Falta a Data de Doação

        if (cBUpdateTipoRoupa.getValue() == null) {
            lblErroTipoRoupaUpdate.setText("Tem de preencher o Tipo de Roupa.");
        } else {
            //this.linhaDoacoes.setTipoRoupa(String.valueOf(cBUpdateTipoRoupa.getValue()));
            lblErroTipoRoupaUpdate.setText("");
        }

        if (cBUpdateTamanhoRoupa.getValue() == null) {
            lblErroTamanhoUpdate.setText("Tem de preencher o Tamanho de Roupa.");
        } else {
            //this.linhaDoacoes.setTamanhoRoupa(String.valueOf(cBUpdateTamanhoRoupa.getValue()));
            lblErroTamanhoUpdate.setText("");
        }

        if (lblErroNomeClienteUpdate.getText().equals("") && lblErroTipoRoupaUpdate.getText().equals("") &&
                lblErroTamanhoUpdate.getText().equals("") && lblErroQuantidadeUpdate.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Tem a certeza que deseja Editar esta Doacao?");
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.get() == ButtonType.OK) {
//                for (Utilizador u: this.utilizadorDao.buscarTodos()) {
//                    if (u.getUsername().equals(this.linhaDoacoes.getUsername().getValue())){
//                        this.utilizadorDao.atualizarUtilizador(u.getIdUtilizador() ,txtFdUpdateUsernameCliente.getText());
//                    }
//                }

                for (Roupa_Doacao rd : this.roupa_doacaoDao.buscarTodas()) {
                    if (rd.getId_roupa_doacao() == this.doacaoDao.buscarPorId(this.linhaDoacoes.getIdDoacao().getValue()).getRoupa_doacao().getId_roupa_doacao()) {
                        this.roupa_doacaoDao.atualizarRoupa_Doacao(rd.getId_roupa_doacao(), Integer.parseInt(txtFdUpdateQtd.getText()));
                    }
                }

                for (Roupa r: this.roupaDao.buscarTodas()) {
//                    if(r.getTipoRoupa().equals(this.linhaDoacoes.getTipoRoupa().getValue()) && r.getTamanhoRoupa().equals(this.linhaDoacoes.getTamanhoRoupa().getValue())){
                    if(r.getRoupa_doacao().getId_roupa_doacao() == this.doacaoDao.buscarPorId(this.linhaDoacoes.getIdDoacao().getValue()).getRoupa_doacao().getId_roupa_doacao()){
                        this.roupaDao.atualizarRoupa(r.getIdRoupa(), String.valueOf(cBUpdateTipoRoupa.getValue()), String.valueOf(cBUpdateTamanhoRoupa.getValue()));

                    }
                }


                this.goToUtil.goToHomePageAdmin();
                Stage stage = (Stage) btnAtualizar.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    void btnApagar(ActionEvent event) {
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        this.goToUtil.goToHomePageAdmin();
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

    public void setDoacao(LinhaDoacoes linhaDoacoes) {
        this.linhaDoacoes = linhaDoacoes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cBUpdateTipoRoupa.getItems().addAll(TipoRoupa.values());
        cBUpdateTamanhoRoupa.getItems().addAll(TamanhoRoupa.values());

        this.linhaDoacoes = new LinhaDoacoes();
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.roupaDao = new RoupaDao(entityManager);
        this.doacaoDao = new DoacaoDao(entityManager);
        this.roupa_doacaoDao = new Roupa_DoacaoDao(entityManager);
        this.goToUtil = new GoToUtil();

    }

    public void passarDadosDoacoesEditar() {
        txtFdUpdateUsernameCliente.setText(String.valueOf(this.linhaDoacoes.getUsername().getValue()));
        txtFdUpdateQtd.setText(String.valueOf(this.linhaDoacoes.getQuantidade().getValue()));
        cBUpdateTipoRoupa.setValue(TipoRoupa.valueOf(this.linhaDoacoes.getTipoRoupa().getValue()));
        cBUpdateTamanhoRoupa.setValue(TamanhoRoupa.valueOf(this.linhaDoacoes.getTamanhoRoupa().getValue()));
    }

}
