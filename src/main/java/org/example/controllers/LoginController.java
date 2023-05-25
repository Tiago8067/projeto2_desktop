package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.RoupaDao;
import org.example.dao.UtilizadorDao;
import org.example.models.Roupa;
import org.example.models.Utilizador;
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    Utilizador utilizador;
    GoToUtil goToUtil;
    RoupaDao roupaDao;

    private List<Roupa> listaRoupaParaCardSotck;
    @FXML
    private Button btnLoginId;
    @FXML
    private CheckBox checkBoxLoginGuardaPass;
    @FXML
    private Hyperlink hyperlinkLoginId;
    @FXML
    private Label labelErroLoginNomeEmail;
    @FXML
    private Label labelErroLoginPass;
    @FXML
    private TextField tfLoginNome;
    @FXML
    private TextField tfLoginPass;

    @FXML
    void btnLogin(ActionEvent event) {
        if (tfLoginNome.getText().isEmpty()) {
            labelErroLoginNomeEmail.setText("Tem de inserir o seu Nome de Utilizador(campo Obrigatorio!!!)");
        } else if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()) == null) {
            labelErroLoginNomeEmail.setText("O Nome de Utilizador não existe. Insira o seu Nome de Utilizador.");
        } else {
            labelErroLoginNomeEmail.setText("");
        }

        try {
            if (tfLoginPass.getText().isEmpty()) {
                labelErroLoginPass.setText("Tem de inserir a sua Palavra-passe(campo Obrigatorio!!!)");
            } else if (!this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getPassword().equals(tfLoginPass.getText())) {
                labelErroLoginPass.setText("A Palavra-passe está Incorreta. Tente Novamente.");
            } else {
                labelErroLoginPass.setText("");
            }
        } catch (NullPointerException e) {
            System.out.println("ERRO: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().setPrefSize(450, 150);
            alert.setContentText("Este Utilizador não existe!");
            alert.show();
        }

        if (labelErroLoginNomeEmail.getText().equals("") && labelErroLoginPass.getText().equals("")) {
            if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getEstadoUtilizador().equals(EstadoUtilizador.PENDENTE)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().setPrefSize(450, 150);
                alert.setContentText("O seu Estado está Pendente. Aguarde pela aprovação de um admin!");
                alert.show();
            } else if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getEstadoUtilizador().equals(EstadoUtilizador.INATIVO)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().setPrefSize(450, 150);
                alert.setContentText("O seu Estado está Inativo. Á espera de moderação de um admin!");
                alert.show();
            } else if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getEstadoUtilizador().equals(EstadoUtilizador.ATIVO)) {
                if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getTipoUtilizador().equals(TipoUtilizador.ADMIN)) {
                    /*this.goToUtil.goToHomePageAdmin();*/
                    String username = tfLoginNome.getText();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/homePage.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
                        stage.show();

                        HomePageController homePageController = fxmlLoader.getController();
                        homePageController.retornaUsernameLogin(username);
                        homePageController.listarDoacoes();
                        homePageController.listaFornecedor();
                        homePageController.listaFuncionarios();
                        homePageController.listarEncomendas();
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getCause();
                    }

                    Stage stage = (Stage) btnLoginId.getScene().getWindow();
                    stage.close();
                }

                if (this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()).getTipoUtilizador().equals(TipoUtilizador.FUNCIONARIO)) {
                    this.goToUtil.goToHomePageFuncionario();
                    Stage stage = (Stage) btnLoginId.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {
        this.goToUtil.goToRegistroDadosPessoas();
        Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.utilizador = new Utilizador();
        this.goToUtil = new GoToUtil();
        this.roupaDao = new RoupaDao(entityManager);
    }

    public void testaRetornoUsername() {
        System.out.println(this.utilizadorDao.buscarUtilizadorPorUsername(tfLoginNome.getText()));
    }
}
