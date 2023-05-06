package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.models.enums.EstadoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroDadosPessoaisController implements Initializable {
    @FXML
    private Button btnRegistrarSeguinteId;

    @FXML
    private Hyperlink hyperlinkLoginId;

    @FXML
    private Label labelIdErroContacto;

    @FXML
    private Label labelIdErroNIF;

    @FXML
    private Label labelIdErroNomeCompleto;

    @FXML
    private Label labelIdErroNumCC;

    @FXML
    private Label labeldErroDataNasc;

    @FXML
    private TextField txtFdRegistroContactoId;

    @FXML
    private DatePicker txtFdRegistroDataNascId;

    @FXML
    private TextField txtFdRegistroNIFD;

    @FXML
    private TextField txtFdRegistroNccId;

    @FXML
    private TextField txtFdRegistroNomeCompletoId;

    EntityManager entityManager = JPAUtil.getEntityManager();
    UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
    Utilizador utilizador = new Utilizador();
    RegexDados regexDados = new RegexDados();
    GoToUtil goToUtil = new GoToUtil();

    @FXML
     void btnRegistrarSeguinteDD(ActionEvent event) {
        if (txtFdRegistroNomeCompletoId.getText().isEmpty()){
            labelIdErroNomeCompleto.setText("Tem de preencher o Nome Comleto no seu Registro");
        } else {
            this.utilizador.setNome(txtFdRegistroNomeCompletoId.getText());
            labelIdErroNomeCompleto.setText("");
        }

        // TODO=> falta a data

        if (txtFdRegistroNccId.getText().isEmpty()){
            labelIdErroNumCC.setText("Tem de preencher o Número do CC no seu Registro");
        } else {
            this.utilizador.setNumeroCc(Integer.valueOf(txtFdRegistroNccId.getText()));
            labelIdErroNumCC.setText("");
        }

        if (txtFdRegistroNIFD.getText().isEmpty()){
            labelIdErroNIF.setText("Tem de preencher o NIF no seu Registro");
        } else {
            this.utilizador.setNif(Integer.valueOf(txtFdRegistroNIFD.getText()));
            labelIdErroNIF.setText("");
        }

        if (txtFdRegistroContactoId.getText().isEmpty()){
            labelIdErroContacto.setText("Tem de preencher o Contacto no seu Registro");
        } else {
            this.utilizador.setContacto(Integer.valueOf(txtFdRegistroContactoId.getText()));
            labelIdErroContacto.setText("");
        }

        if (labelIdErroNomeCompleto.getText().equals("") && labelIdErroNumCC.getText().equals("") && labelIdErroNIF.getText().equals("") &&
                labelIdErroContacto.getText().equals("")) {
            this.utilizador.setEstadoUtilizador(EstadoUtilizador.PENDENTE);
            this.utilizadorDao.registrar(utilizador);
            this.goToUtil.goToRegistroDadosLocalizacao();
            Stage stage = (Stage) btnRegistrarSeguinteId.getScene().getWindow();
            stage.close();
        }
        //Integer idUtilizadorCompletoResgistro = this.utilizador.getIdUtilizador();
        //System.out.println(this.utilizador.getIdUtilizador());
        //System.out.println(getIdUtilizadorCompletoResgistro());


        //return this.utilizador.getIdUtilizador();
    }

    private Integer idUtilizadorCompletoResgistro; // = this.utilizador.getIdUtilizador();

    public Integer getIdUtilizadorCompletoResgistro() {
         return this.idUtilizadorCompletoResgistro = this.utilizador.getIdUtilizador();
    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {
        this.goToUtil.goToLogin();
        Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.goToUtil = new GoToUtil();
    }
}
