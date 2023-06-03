package org.example.modelsHelp;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;

public class LinhaDoacoes {
    private IntegerProperty idDoacao;
    private StringProperty username;
    private StringProperty tipoRoupa;
    private StringProperty tamanhoRoupa;
    private IntegerProperty quantidade;
    private StringProperty dataDoacao;

    public LinhaDoacoes() {

    }

    public LinhaDoacoes(int idDoacao, String username, String tipoRoupa, String tamanhoRoupa, int quantidade, String dataDoacao) {
        this.idDoacao = new SimpleIntegerProperty(idDoacao);
        this.username = new SimpleStringProperty(username);
        this.tipoRoupa = new SimpleStringProperty(tipoRoupa);
        this.tamanhoRoupa = new SimpleStringProperty(tamanhoRoupa);
        this.quantidade = new SimpleIntegerProperty(quantidade);
        this.dataDoacao = new SimpleStringProperty(dataDoacao);
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

    public ObservableValue<String> getDataDoacao() {
        ObservableValue<String> valorData = new ReadOnlyObjectWrapper<>(dataDoacao.getValue());
        return valorData;
    }

    public StringProperty dataDoacaoProperty() {
        return dataDoacao;
    }

    public void setDataDoacao(String dataDoacao) {
        this.dataDoacao.set(dataDoacao);
    }
}
