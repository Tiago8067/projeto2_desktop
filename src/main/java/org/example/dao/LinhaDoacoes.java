package org.example.dao;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class LinhaDoacoes {
    IntegerProperty idDoacao;
    StringProperty username;
    StringProperty tipoRoupa;
    StringProperty tamanhoRoupa;
    IntegerProperty quantidade;

    public LinhaDoacoes(int idDoacao, String username, String tipoRoupa, String tamanhoRoupa, int quantidade) {
        this.idDoacao.setValue(idDoacao);
        this.username.setValue(username);
        this.tipoRoupa.setValue(tipoRoupa);
        this.tamanhoRoupa.setValue(tamanhoRoupa);
        this.quantidade.setValue(quantidade);
    }

    public ObservableValue<Integer> getIdDoacao() {
        ObservableValue<Integer> x = new ReadOnlyObjectWrapper<>(idDoacao.getValue());
        return x;
    }

    public IntegerProperty idDoacaoProperty() {
        return idDoacao;
    }

    public void setIdDoacao(int idDoacao) {
        this.idDoacao.set(idDoacao);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getTipoRoupa() {
        return tipoRoupa.get();
    }

    public StringProperty tipoRoupaProperty() {
        return tipoRoupa;
    }

    public void setTipoRoupa(String tipoRoupa) {
        this.tipoRoupa.set(tipoRoupa);
    }

    public String getTamanhoRoupa() {
        return tamanhoRoupa.get();
    }

    public StringProperty tamanhoRoupaProperty() {
        return tamanhoRoupa;
    }

    public void setTamanhoRoupa(String tamanhoRoupa) {
        this.tamanhoRoupa.set(tamanhoRoupa);
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public IntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }


}
