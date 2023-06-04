package org.example.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.example.controllers.HomePageController;
import org.example.controllers.HomePageFuncionarioController;
import org.example.controllers.PerfilPageController;
import org.example.controllers.editar.EditarClienteController;
import org.example.controllers.editar.EditarDoacoesController;
import org.example.controllers.editar.EditarEncomendasController;
import org.example.controllers.editar.EditarFornecedorController;
import org.example.models.Utilizador;
import org.example.modelsHelp.LinhaDoacoes;
import org.example.models.Fornecedor;
import org.example.modelsHelp.LinhaEncomendas;

@NoArgsConstructor
public class GoToUtil {
    public void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToRegistro() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/registro.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToRegistroDadosPessoas() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/registroDadosPessoais.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToRegistroDadosLocalizacao() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/registroDadosLocalizacao.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToHomePageAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            HomePageController homePageController = fxmlLoader.getController();
            homePageController.listarDoacoes();
            homePageController.listaFornecedor();
            homePageController.listaFuncionarios();
            homePageController.listarEncomendas();
            homePageController.listaClientes();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToHomePageAdminDeTabFornecedor() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            HomePageController homePageController = fxmlLoader.getController();
            homePageController.listarDoacoes();
            homePageController.listaFornecedor();
            homePageController.voltarTabFornecdor();
            homePageController.listaFuncionarios();
            homePageController.listarEncomendas();
            homePageController.listaClientes();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToHomePageAdminDeTabPedidos() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            HomePageController homePageController = fxmlLoader.getController();
            homePageController.listarDoacoes();
            homePageController.listaFornecedor();
            homePageController.voltarTabPedidos();
            homePageController.listaFuncionarios();
            homePageController.listarEncomendas();
            homePageController.listaClientes();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToHomePageAdminDeTabClientes() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            HomePageController homePageController = fxmlLoader.getController();
            homePageController.listarDoacoes();
            homePageController.listaFornecedor();
            homePageController.voltarTabClientes();
            homePageController.listaFuncionarios();
            homePageController.listarEncomendas();
            homePageController.listaClientes();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToHomePageFuncionario() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/funcionario/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            HomePageFuncionarioController homePageFuncionarioController = fxmlLoader.getController();
            homePageFuncionarioController.listarDoacoes();
            homePageFuncionarioController.listaFornecedor();
            homePageFuncionarioController.listarEncomendas();
            homePageFuncionarioController.listaClientes();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToHomePageFuncionarioDeTabPedidos() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/funcionario/homePage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            HomePageFuncionarioController homePageFuncionarioController = fxmlLoader.getController();
            homePageFuncionarioController.listarDoacoes();
            homePageFuncionarioController.listaFornecedor();
            homePageFuncionarioController.listarEncomendas();
            homePageFuncionarioController.listaClientes();
            homePageFuncionarioController.voltarTabPedidos();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToAddFornecedor() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/adicionar/adicionarFornecedor.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToEditFornecedor(Fornecedor obj) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/editar/editarFornecedor.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            EditarFornecedorController editarFornecedorController = fxmlLoader.getController();
            editarFornecedorController.setFornecedor(obj);
            editarFornecedorController.passarDadosFornecedorEditar();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToEditCliente(Utilizador obj) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/editar/editarCliente.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            EditarClienteController editarClienteController = fxmlLoader.getController();
            editarClienteController.setCliente(obj);
            editarClienteController.passarDadosClienteEditar();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToAddPedido() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/adicionar/adicionarPedido.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void goToAddDoacao() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/adicionar/adicionarDoacoes.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void gotoEditarDoacoes(LinhaDoacoes obj) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/editar/editarDoacao.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            EditarDoacoesController editarDoacoesController = fxmlLoader.getController();
            editarDoacoesController.setDoacao(obj);
            editarDoacoesController.passarDadosDoacoesEditar();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void gotoEditarEncomendas(LinhaEncomendas obj) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/editar/editarEncomenda.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            EditarEncomendasController editarEncomendasController = fxmlLoader.getController();
            editarEncomendasController.setEncomenda(obj);
            editarEncomendasController.passarDadosEncomendasEditar();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void gotoPerfilPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/perfilPage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
            stage.show();

            PerfilPageController perfilPageController = fxmlLoader.getController();
            perfilPageController.retornaUsernameLogin();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
