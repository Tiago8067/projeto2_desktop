package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.models.enums.EstadoUtilizador;
import org.example.models.enums.TipoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private MenuItem idgotoLoginPage;


    @FXML
    private TableView<Utilizador> tableViewFuncionarios;

    @FXML
    private TableColumn<Utilizador, Integer> tableColumnId;

    @FXML
    private TableColumn<Utilizador, String> tableColumnNome;

    @FXML
    private TableColumn<Utilizador, EstadoUtilizador> tableColumnEstado;

    @FXML
    private TableColumn<Utilizador, Utilizador> tableColumnAcoes;

    private ObservableList<Utilizador> observableListFuncionarios;

    //Tentativa 2
    //EntityManager entityManager; // = JPAUtil.getEntityManager();
    //UtilizadorDao utilizadorDao; // = new UtilizadorDao(entityManager);
    EntityManager entityManager = JPAUtil.getEntityManager();
    UtilizadorDao utilizadorDao = new UtilizadorDao(entityManager);
    GoToUtil goToUtil = new GoToUtil();

    @FXML
    void gotoLoginPage(ActionEvent event) {
        this.goToUtil.goToLogin();
        //Stage stage = (Stage)idgotoLoginPage.getScene().getWindow();
        //stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //https://stackoverflow.com/questions/20594392/unable-to-get-scene-from-menuitem-in-javafx
        //Para fechar o menuitem em "SAIR"
        Stage stage = (Stage)idgotoLoginPage.getParentPopup().getOwnerWindow();
        stage.close();
    }

    /*
    Tentiva 2
    public void setUtilizadorDao(EntityManager entityManager ,UtilizadorDao utilizadorDao) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
    }
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idUtilizador"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("estadoUtilizador"));
    }

    public void listaFuncionarios() {
        List<Utilizador> utilizadorList = this.utilizadorDao.buscarTodos();
        //System.out.println(listaFuncionarios);
        List<Utilizador> listaFuncionarios = new ArrayList<>();
        //observableListFuncionarios = FXCollections.observableArrayList(listaFuncionarios);
        for (Utilizador u: utilizadorList) {
            //System.out.println(u.getTipoUtilizador());
          if (u.getTipoUtilizador().equals(TipoUtilizador.FUNCIONARIO)) {
              //tableViewFuncionarios.setItems(observableListFuncionarios);
              //System.out.println(u.getTipoUtilizador());
              //System.out.println(tableViewFuncionarios);
              listaFuncionarios.add(u);
          }
        }

        observableListFuncionarios = FXCollections.observableArrayList(listaFuncionarios);
        tableViewFuncionarios.setItems(observableListFuncionarios);
        //if (this.utilizadorDao.buscarTodos())
        //observableListFuncionarios = FXCollections.observableArrayList(listaFuncionarios);
        //tableViewFuncionarios.setItems(observableListFuncionarios);
        //System.out.println(observableListFuncionarios);
    }
}
