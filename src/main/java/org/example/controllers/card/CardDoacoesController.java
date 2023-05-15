package org.example.controllers.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Roupa;
import org.example.models.Roupa_Doacao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

    public void setCardDoacoes(Roupa roupa) throws FileNotFoundException {
        //Roupa_Doacao roupa_doacao = new Roupa_Doacao();
        //Image image = new Image("resources/images/calcas.jpg"); //roupa.getImageSrc())
        //nputStream stream = new FileInputStream(roupa.getImageSrc());
        Image image = new Image(roupa.getImageSrc());
        idImageView.setImage(image);
        idLblTitulo.setText(String.valueOf(roupa.getTipoRoupa()));
        idLblSubTitulo.setText(String.valueOf(roupa.getCategoriaRoupa()));
        IdLblTamanho.setText(String.valueOf(roupa.getTamanhoRoupa()));
        IdLblQtd.setText(String.valueOf(roupa.getStock()));
    }
}
