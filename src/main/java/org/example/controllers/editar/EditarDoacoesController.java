package org.example.controllers.editar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.controllers.LoginController;
import org.example.models.Utilizador;
import org.example.models.enums.*;
import org.example.modelsHelp.LinhaDoacoes;
import org.example.dao.DoacaoDao;
import org.example.dao.RoupaDao;
import org.example.dao.Roupa_DoacaoDao;
import org.example.dao.UtilizadorDao;
import org.example.models.Doacao;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;
import org.example.modelsHelp.LinhaRoupa;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.Verificacoes;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDate;
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
    Verificacoes verificacoes;
    String guardaUsernameLogin = LoginController.usernameGuardado;

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

        if (txtFdUpdateUsernameCliente.getText().isEmpty()) {
            lblErroNomeClienteUpdate.setText("Tem de preencher o Nome Completo.");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdUpdateUsernameCliente.getText()) == null) {
            lblErroNomeClienteUpdate.setText("Este utilizador não existe!");
        } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdUpdateUsernameCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE)) {
            lblErroNomeClienteUpdate.setText("Este utilizador não é um Cliente!!! Introduza novamente!");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(txtFdUpdateUsernameCliente.getText()).getTipoUtilizador().equals(TipoUtilizador.CLIENTE) && (!this.utilizadorDao.buscarUtilizadorPorUsername(txtFdUpdateUsernameCliente.getText()).getEstadoUtilizador().equals(EstadoUtilizador.ATIVO))) {
            lblErroNomeClienteUpdate.setText("Este Cliente não está Ativo!!! Introduza novamente!");
        } else {
            lblErroNomeClienteUpdate.setText("");
        }

        if (txtFdUpdateQtd.getText().isEmpty()) {
            lblErroQuantidadeUpdate.setText("Tem de preencher a Quantidade.");
        } else if (this.verificacoes.verficaInteiro(verificaQtd, txtFdUpdateQtd.getText()) == 0) {
            lblErroQuantidadeUpdate.setText("Preencha corretamente a Quantidade!");
        } else {
            lblErroQuantidadeUpdate.setText("");
        }

        if (cBUpdateTipoRoupa.getValue() == null) {
            lblErroTipoRoupaUpdate.setText("Tem de preencher o Tipo de Roupa.");
        } else {
            lblErroTipoRoupaUpdate.setText("");
        }

        if (cBUpdateTamanhoRoupa.getValue() == null) {
            lblErroTamanhoUpdate.setText("Tem de preencher o Tamanho de Roupa.");
        } else {
            lblErroTamanhoUpdate.setText("");
        }

        if (lblErroNomeClienteUpdate.getText().equals("") && lblErroTipoRoupaUpdate.getText().equals("") &&
                lblErroTamanhoUpdate.getText().equals("") && lblErroQuantidadeUpdate.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Tem a certeza que deseja Editar esta Doacao?");
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.get() == ButtonType.OK) {
                for (Doacao d : this.doacaoDao.buscarTodas()) {
                    if (d.getRoupa_doacao().getId_roupa_doacao() == this.doacaoDao.buscarPorId(this.linhaDoacoes.getIdDoacao().getValue()).getRoupa_doacao().getId_roupa_doacao()) {
                        this.doacaoDao.atualizarDoacao(d.getIdDoacao(), String.valueOf(LocalDate.now()));
                    }
                }

                for (Roupa_Doacao rd : this.roupa_doacaoDao.buscarTodas()) {
                    if (rd.getId_roupa_doacao() == this.doacaoDao.buscarPorId(this.linhaDoacoes.getIdDoacao().getValue()).getRoupa_doacao().getId_roupa_doacao()) {
                        this.roupa_doacaoDao.atualizarRoupa_Doacao(rd.getId_roupa_doacao(), Integer.parseInt(txtFdUpdateQtd.getText()));
                    }
                }

                for (Roupa r : this.roupaDao.buscarTodas()) {
                    if (r.getRoupa_doacao().getId_roupa_doacao() == this.doacaoDao.buscarPorId(this.linhaDoacoes.getIdDoacao().getValue()).getRoupa_doacao().getId_roupa_doacao()) {
                        this.roupaDao.atualizarRoupa(r.getIdRoupa(), String.valueOf(cBUpdateTipoRoupa.getValue()), String.valueOf(cBUpdateTamanhoRoupa.getValue()), String.valueOf(atualizarAssociarCategoria()), String.valueOf(atualizarAssociarImagem()));

                        for (Roupa roupas : this.roupaDao.buscarPorTipoTamanhoRoupa(r)) {
                            roupas.setStock(retornaSoma());
                            this.roupaDao.registar(roupas);
                        }
                    }
                }

                retornaParaHomePageCorreto(btnAtualizar);
            }
        }
    }

    @FXML
    void btnApagar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem a certeza que quer Eliminar esta Doacao?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.get() == ButtonType.OK) {
            for (Doacao d : this.doacaoDao.buscarTodas()) {
                if (d.getIdDoacao() == this.linhaDoacoes.getIdDoacao().getValue()) {
                    this.doacaoDao.apagarDoacaoPorId(d.getIdDoacao());
                }
            }

            for (Roupa r : this.roupaDao.buscarTodas()) {
                if (r.getRoupa_doacao().getId_roupa_doacao() == this.doacaoDao.buscarPorId(this.linhaDoacoes.getIdDoacao().getValue()).getRoupa_doacao().getId_roupa_doacao()) {
                    this.roupaDao.apagarRoupa(r.getIdRoupa());
                }
            }

            int diferenca = 0;
            for (Roupa_Doacao rd : this.roupa_doacaoDao.buscarTodas()) {
                if (rd.getId_roupa_doacao() == this.doacaoDao.buscarPorId(this.linhaDoacoes.getIdDoacao().getValue()).getRoupa_doacao().getId_roupa_doacao()) {
                    diferenca = rd.getQuantidade();
                    this.roupa_doacaoDao.apagarRoupa_DoacaoPorId(rd.getId_roupa_doacao());
                }
            }

            for (Roupa r : this.roupaDao.buscarTodas()) {
                if (r.getTipoRoupa().equals(cBUpdateTipoRoupa.getValue()) && r.getTamanhoRoupa().equals(cBUpdateTamanhoRoupa.getValue())) {
                    int stock = r.getStock();
                    stock = stock - diferenca;
                    r.setStock(stock);
                    this.roupaDao.registar(r);
                }
            }

            retornaParaHomePageCorreto(btnApagar);
        }
    }

    @FXML
    void btnVoltar(ActionEvent event) {
        retornaParaHomePageCorreto(btnVoltar);
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
        this.verificacoes = new Verificacoes();
    }

    public void passarDadosDoacoesEditar() {
        txtFdUpdateUsernameCliente.setText(String.valueOf(this.linhaDoacoes.getUsername().getValue()));
        txtFdUpdateQtd.setText(String.valueOf(this.linhaDoacoes.getQuantidade().getValue()));
        cBUpdateTipoRoupa.setValue(TipoRoupa.valueOf(this.linhaDoacoes.getTipoRoupa().getValue()));
        cBUpdateTamanhoRoupa.setValue(TamanhoRoupa.valueOf(this.linhaDoacoes.getTamanhoRoupa().getValue()));
    }

    private CategoriaRoupa atualizarAssociarCategoria() {
        if (cBUpdateTipoRoupa.getValue().equals(TipoRoupa.CALCOES) ||
                cBUpdateTipoRoupa.getValue().equals(TipoRoupa.CALCAS) ||
                cBUpdateTipoRoupa.getValue().equals(TipoRoupa.SAIA) ||
                cBUpdateTipoRoupa.getValue().equals(TipoRoupa.MEIAS) ||
                cBUpdateTipoRoupa.getValue().equals(TipoRoupa.MEIACALCA)) {
            return CategoriaRoupa.PARTEDEBAIXO;
        } else {
            return CategoriaRoupa.PARTEDECIMA;
        }
    }

    private String atualizarAssociarImagem() {
        return switch (cBUpdateTipoRoupa.getValue()) {
            case CALCAS -> "/images/images2/calcas.gif";
            case CALCOES -> "/images/images2/calcoes.gif";
            case VESTIDO -> "/images/images2/vestido.gif";
            case SAIA -> "/images/images2/saia2.jfif";
            case BLUSA -> "/images/images2/blusa.gif";
            case SWEAT -> "/images/images2/sweat.gif";
            case T_SHIRT -> "/images/images2/t-shirt.gif";
            case CAMISA -> "/images/images2/camisa.png";
            case CASACO -> "/images/images2/casaco.gif";
            case COLETE -> "/images/images2/colete.gif";
            case MEIACALCA -> "/images/images2/meia-calca.gif";
            case MEIAS -> "/images/images2/meias.gif";
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
            if (linhaRoupa.getTipoRoupa().equals(String.valueOf(cBUpdateTipoRoupa.getValue())) && linhaRoupa.getTamanhoRoupa().equals(String.valueOf(cBUpdateTamanhoRoupa.getValue()))) {
                soma = soma + linhaRoupa.getQuantidade();
            }
        }
        return soma;
    }
}
