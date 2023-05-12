package org.example.controllers.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.LinhaEncomenda;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;

public class CardDoacoesController {
    @FXML
    private Label IdLblQtd;

    @FXML
    private Label IdLblTamanho;

    @FXML
    private ImageView idImageView;

    @FXML
    private Label idLblSubTitulo;

    @FXML
    private Label idLblTitulo;

    public void setCardDoacoes(Roupa roupa){
        Roupa_Doacao roupa_doacao = new Roupa_Doacao();
        Image image = new Image(getClass().getResourceAsStream(roupa.getImageSrc()));
        idImageView.setImage(image);
        idLblTitulo.setText(String.valueOf(roupa.getTipoRoupa()));
        idLblSubTitulo.setText(String.valueOf(roupa.getCategoriaRoupa()));
        IdLblTamanho.setText(String.valueOf(roupa.getTamanhoRoupa()));
        IdLblQtd.setText(String.valueOf(roupa_doacao.getQuantidade()));
    }
}
