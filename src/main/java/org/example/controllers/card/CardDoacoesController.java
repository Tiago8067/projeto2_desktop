package org.example.controllers.card;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.modelsHelp.LinhaRoupa;
import org.example.dao.RoupaDao;
import org.example.dao.Roupa_DoacaoDao;
import org.example.models.Roupa;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardDoacoesController implements Initializable {
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

    EntityManager entityManager;
    Roupa_DoacaoDao roupa_doacaoDao;
    RoupaDao roupaDao;

    public void setCardDoacoes(Roupa roupa) throws FileNotFoundException {
        Image image = new Image(roupa.getImageSrc());
        idImageView.setImage(image);
        idLblTitulo.setText(String.valueOf(roupa.getTipoRoupa()));
        idLblSubTitulo.setText(String.valueOf(roupa.getCategoriaRoupa()));
        IdLblTamanho.setText(String.valueOf(roupa.getTamanhoRoupa()));
        IdLblQtd.setText(String.valueOf(roupa.getStock()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.roupa_doacaoDao = new Roupa_DoacaoDao(entityManager);
        this.roupaDao = new RoupaDao(entityManager);
    }
}
