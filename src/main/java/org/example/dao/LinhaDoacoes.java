package org.example.dao;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class LinhaDoacoes {
    private IntegerProperty idDoacao;
    StringProperty username;
    StringProperty tipoRoupa;
    StringProperty tamanhoRoupa;
    IntegerProperty quantidade;

    public LinhaDoacoes(int idDoacao, String username, String tipoRoupa, String tamanhoRoupa, int quantidade) {
//        this.idDoacao.setValue(idDoacao);
//        this.username.setValue(username);
//        this.tipoRoupa.setValue(tipoRoupa);
//        this.tamanhoRoupa.setValue(tamanhoRoupa);
//        this.quantidade.setValue(quantidade);
        this.idDoacao = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty();
        this.tipoRoupa = new SimpleStringProperty();
        this.tamanhoRoupa = new SimpleStringProperty();
        this.quantidade = new SimpleIntegerProperty();
    }

    public ObservableValue<Integer> getIdDoacao() {
        ObservableValue<Integer> valorIdDoacao = new ReadOnlyObjectWrapper<>(idDoacao.getValue());
        return valorIdDoacao;
    }

    public IntegerProperty idDoacaoProperty() {
        return idDoacao;
    }

    public void setIdDoacao(int idDoacao) {
        this.idDoacao.set(idDoacao);
    }

    public ObservableValue<String> getUsername() {
        ObservableValue<String> valorUsername = new ReadOnlyObjectWrapper<>(username.getValue());
        return valorUsername;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
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


}
