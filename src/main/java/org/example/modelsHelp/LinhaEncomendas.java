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

    public LinhaEncomendas() {
    }

    public LinhaEncomendas(int idEncomenda, int idLinhaEncomenda, int idRoupa, String usernameCliente, String usernameFonecedor, String tipoRoupa, String tamanhoRoupa, int quantidade, String estado) {
        this.idEncomenda = new SimpleIntegerProperty(idEncomenda);
        this.idLinhaEncomenda = new SimpleIntegerProperty(idLinhaEncomenda);
        this.idRoupa = new SimpleIntegerProperty(idRoupa);
        this.usernameCliente = new SimpleStringProperty(usernameCliente);
        this.usernameFonecedor = new SimpleStringProperty(usernameFonecedor);
        this.tipoRoupa = new SimpleStringProperty(tipoRoupa);
        this.tamanhoRoupa = new SimpleStringProperty(tamanhoRoupa);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.estado = new SimpleStringProperty(estado);
    }

    public ObservableValue<Integer> getIdEncomenda() {
        ObservableValue<Integer> valorIdEncomenda = new ReadOnlyObjectWrapper<>(idEncomenda.getValue());
        return valorIdEncomenda;
    }

    public IntegerProperty idEncomendaProperty() {
        return idEncomenda;
    }

    public void setIdEncomenda(int idEncomenda) {
        this.idEncomenda.set(idEncomenda);
    }

    public ObservableValue<Integer> getIdLinhaEncomenda() {
        ObservableValue<Integer> valorIdLinhaEncomenda = new ReadOnlyObjectWrapper<>(idLinhaEncomenda.getValue());
        return valorIdLinhaEncomenda;
    }

    public IntegerProperty idLinhaEncomendaProperty() {
        return idLinhaEncomenda;
    }

    public void setIdLinhaEncomenda(int idLinhaEncomenda) {
        this.idLinhaEncomenda.set(idLinhaEncomenda);
    }

    public ObservableValue<Integer> getIdRoupa() {
        ObservableValue<Integer> valorIdRoupa = new ReadOnlyObjectWrapper<>(idRoupa.getValue());
        return valorIdRoupa;
    }

    public IntegerProperty idRoupaProperty() {
        return idRoupa;
    }

    public void setIdRoupa(int idRoupa) {
        this.idRoupa.set(idRoupa);
    }

    public ObservableValue<String> getUsernameCliente() {
        ObservableValue<String> valorUsernameCliente = new ReadOnlyObjectWrapper<>(usernameCliente.getValue());
        return valorUsernameCliente;
    }

    public StringProperty usernameClienteProperty() {
        return usernameCliente;
    }

    public void setUsernameCliente(String usernameCliente) {
        this.usernameCliente.set(usernameCliente);
    }

    public ObservableValue<String> getUsernameFonecedor() {
        ObservableValue<String> valorUsernameFonecedor = new ReadOnlyObjectWrapper<>(usernameFonecedor.getValue());
        return valorUsernameFonecedor;
    }

    public StringProperty usernameFonecedorProperty() {
        return usernameFonecedor;
    }

    public void setUsernameFonecedor(String usernameFonecedor) {
        this.usernameFonecedor.set(usernameFonecedor);
    }

    public ObservableValue<String> getTipoRoupa() {
        ObservableValue<String> valorTipoRoupa = new ReadOnlyObjectWrapper<>(tipoRoupa.getValue());
        return valorTipoRoupa;
    }

    public StringProperty tipoRoupaProperty() {
        return tipoRoupa;
    }

    public void setTipoRoupa(String tipoRoupa) {
        this.tipoRoupa.set(tipoRoupa);
    }

    public ObservableValue<String> getTamanhoRoupa() {
        ObservableValue<String> valorTamanhoRoupa = new ReadOnlyObjectWrapper<>(tamanhoRoupa.getValue());
        return valorTamanhoRoupa;
    }

    public StringProperty tamanhoRoupaProperty() {
        return tamanhoRoupa;
    }

    public void setTamanhoRoupa(String tamanhoRoupa) {
        this.tamanhoRoupa.set(tamanhoRoupa);
    }

    public ObservableValue<Integer> getQuantidade() {
        ObservableValue<Integer> valorQuantidade = new ReadOnlyObjectWrapper<>(quantidade.getValue());
        return valorQuantidade;
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public ObservableValue<String> getEstado() {
        ObservableValue<String> valorEstado = new ReadOnlyObjectWrapper<>(estado.getValue());
        return valorEstado;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }
}
