package org.example.modelsHelp;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

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

    public ObservableValue<String> getUsername() {
        ObservableValue<String> valorUsername = new ReadOnlyObjectWrapper<>(username.getValue());
        return valorUsername;
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

    public ObservableValue<String> getDataDoacao() {
        ObservableValue<String> valorData = new ReadOnlyObjectWrapper<>(dataDoacao.getValue());
        return valorData;
    }
}
