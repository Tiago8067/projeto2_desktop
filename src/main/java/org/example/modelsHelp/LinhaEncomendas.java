package org.example.modelsHelp;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class LinhaEncomendas {
    private IntegerProperty idEncomenda;
    private IntegerProperty idLinhaEncomenda;
    private IntegerProperty idRoupa;
    private StringProperty usernameCliente;
    private StringProperty usernameFonecedor;
    private StringProperty tipoRoupa;
    private StringProperty tamanhoRoupa;
    private IntegerProperty quantidade;
    private StringProperty estado;
    private StringProperty dataDePedido;
    private StringProperty dataDeEntrega;

    public LinhaEncomendas() {
    }

    public LinhaEncomendas(int idEncomenda, int idLinhaEncomenda, int idRoupa, String usernameCliente, String usernameFonecedor, String tipoRoupa,
                           String tamanhoRoupa, int quantidade, String estado, String dataDePedido, String dataDeEntrega) {
        this.idEncomenda = new SimpleIntegerProperty(idEncomenda);
        this.idLinhaEncomenda = new SimpleIntegerProperty(idLinhaEncomenda);
        this.idRoupa = new SimpleIntegerProperty(idRoupa);
        this.usernameCliente = new SimpleStringProperty(usernameCliente);
        this.usernameFonecedor = new SimpleStringProperty(usernameFonecedor);
        this.tipoRoupa = new SimpleStringProperty(tipoRoupa);
        this.tamanhoRoupa = new SimpleStringProperty(tamanhoRoupa);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.estado = new SimpleStringProperty(estado);
        this.dataDePedido = new SimpleStringProperty(dataDePedido);
        this.dataDeEntrega = new SimpleStringProperty(dataDeEntrega);
    }

    public ObservableValue<Integer> getIdEncomenda() {
        ObservableValue<Integer> valorIdEncomenda = new ReadOnlyObjectWrapper<>(idEncomenda.getValue());
        return valorIdEncomenda;
    }

    public ObservableValue<Integer> getIdLinhaEncomenda() {
        ObservableValue<Integer> valorIdLinhaEncomenda = new ReadOnlyObjectWrapper<>(idLinhaEncomenda.getValue());
        return valorIdLinhaEncomenda;
    }

    public ObservableValue<Integer> getIdRoupa() {
        ObservableValue<Integer> valorIdRoupa = new ReadOnlyObjectWrapper<>(idRoupa.getValue());
        return valorIdRoupa;
    }

    public ObservableValue<String> getUsernameCliente() {
        ObservableValue<String> valorUsernameCliente = new ReadOnlyObjectWrapper<>(usernameCliente.getValue());
        return valorUsernameCliente;
    }

    public ObservableValue<String> getUsernameFonecedor() {
        ObservableValue<String> valorUsernameFonecedor = new ReadOnlyObjectWrapper<>(usernameFonecedor.getValue());
        return valorUsernameFonecedor;
    }

    public ObservableValue<String> getTipoRoupa() {
        ObservableValue<String> valorTipoRoupa = new ReadOnlyObjectWrapper<>(tipoRoupa.getValue());
        return valorTipoRoupa;
    }

    public ObservableValue<String> getTamanhoRoupa() {
        ObservableValue<String> valorTamanhoRoupa = new ReadOnlyObjectWrapper<>(tamanhoRoupa.getValue());
        return valorTamanhoRoupa;
    }

    public ObservableValue<Integer> getQuantidade() {
        ObservableValue<Integer> valorQuantidade = new ReadOnlyObjectWrapper<>(quantidade.getValue());
        return valorQuantidade;
    }

    public ObservableValue<String> getEstado() {
        ObservableValue<String> valorEstado = new ReadOnlyObjectWrapper<>(estado.getValue());
        return valorEstado;
    }

    public ObservableValue<String> getDataDePedido() {
        ObservableValue<String> valorDataPedido = new ReadOnlyObjectWrapper<>(dataDePedido.getValue());
        return valorDataPedido;
    }

    public ObservableValue<String> getDataDeEntrega() {
        ObservableValue<String> valorDataEntrega = new ReadOnlyObjectWrapper<>(dataDeEntrega.getValue());
        return valorDataEntrega;
    }
}
