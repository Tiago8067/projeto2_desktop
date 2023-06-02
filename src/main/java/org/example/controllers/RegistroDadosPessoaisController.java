package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.dao.UtilizadorDao;
import org.example.models.Utilizador;
import org.example.models.enums.EstadoUtilizador;
import org.example.util.GoToUtil;
import org.example.util.JPAUtil;
import org.example.util.RegexDados;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.scene.input.MouseEvent;
import java.util.ResourceBundle;

public class RegistroDadosPessoaisController implements Initializable {
    EntityManager entityManager;
    UtilizadorDao utilizadorDao;
    Utilizador utilizador;
    RegexDados regexDados;
    GoToUtil goToUtil;

    @FXML
    private Button btnRegistrarSeguinteId;
    @FXML
    private Hyperlink hyperlinkLoginId;
    @FXML
    private Label labelIdErroContacto;
    @FXML
    private Label labelIdErroNIF;
    @FXML
    private Label labelIdErroNomeCompleto;
    @FXML
    private Label labelIdErroNumCC;
    @FXML
    private Label labeldErroDataNasc;
    @FXML
    private TextField txtFdRegistroContactoId;
    @FXML
    private DatePicker txtFdRegistroDataNascId;
    @FXML
    private TextField txtFdRegistroNIFD;
    @FXML
    private TextField txtFdRegistroNccId;
    @FXML
    private TextField txtFdRegistroNomeCompletoId;

    @FXML
    void btnRegistrarSeguinteDD(ActionEvent event) {
        int verificaNumCC = 0;
        int verificaNIF = 0;
        int verificaContacto = 0;

        LocalDate verficaData = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            verficaData = LocalDate.parse(txtFdRegistroDataNascId.getEditor().getText(), formatter);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }

        try {
            verificaNumCC = Integer.parseInt(txtFdRegistroNccId.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaNIF = Integer.parseInt(txtFdRegistroNIFD.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        try {
            verificaContacto = Integer.parseInt(txtFdRegistroContactoId.getText());
        } catch (NumberFormatException numberFormatException) {
            System.out.println(numberFormatException.getMessage());
        }

        if (txtFdRegistroNomeCompletoId.getText().isEmpty()) {
            labelIdErroNomeCompleto.setText("Tem de preencher o Nome Completo!");
        } else {
            this.utilizador.setNome(txtFdRegistroNomeCompletoId.getText());
            labelIdErroNomeCompleto.setText("");
        }

        if (txtFdRegistroNccId.getText().isEmpty()) {
            labelIdErroNumCC.setText("Tem de preencher o Número do CC!");
        } else if (verificaNumCC == 0) {
            labelIdErroNumCC.setText("Preencha corretamente o Número CC!");
        } else {
            this.utilizador.setNumeroCc(Integer.valueOf(txtFdRegistroNccId.getText()));
            labelIdErroNumCC.setText("");
        }

        if (txtFdRegistroNIFD.getText().isEmpty()) {
            labelIdErroNIF.setText("Tem de preencher o NIF!");
        } else if (verificaNIF == 0) {
            labelIdErroNIF.setText("Preencha corretamente o NIF!");
        } else {
            this.utilizador.setNif(Integer.valueOf(txtFdRegistroNIFD.getText()));
            labelIdErroNIF.setText("");
        }

        if (txtFdRegistroContactoId.getText().isEmpty()) {
            labelIdErroContacto.setText("Tem de preencher o Contacto!");
        } else if (verificaContacto == 0) {
            labelIdErroContacto.setText("Preencha corretamente o Contacto!");
        } else {
            this.utilizador.setContacto(Integer.valueOf(txtFdRegistroContactoId.getText()));
            labelIdErroContacto.setText("");
        }

        // TODO=> falta a data


//        String data = String.valueOf(txtFdRegistroDataNascId.getValue());
//        txtFdRegistroDataNascId.setOnAction(eventDate -> {
////            LocalDate localDate = txtFdRegistroDataNascId.getValue();
////
////            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
////
////            // Format the selected date using the formatter
////            String formattedDate = localDate.format(formatter);
////            System.out.println("Selected date: " + formattedDate);
//            if(data == null || data.isEmpty()){ //.isEmpty() ou .isBlank() ou == null
////                System.out.println("Validation error: Date field is empty.");
////                data.trim().equals("");
//                labeldErroDataNasc.setText("Tem de preencher a Data de Nascimento.");
//                System.out.println("Date field is empty.");
//            } else {
//                try {
//                    LocalDate.parse(data);
//                    this.utilizador.setDataNascimento(data.toString());
//                    System.out.println("Date is valid.");
//                } catch (DateTimeParseException e) {
//                    labeldErroDataNasc.setText("Por favor, preencha a Data no formato(dd/MM/yyyy)!");
//                    System.out.println(e.getMessage());
//                    System.out.println("Invalid date.");
//                }
//            }
//        });


        System.out.println(txtFdRegistroDataNascId.getEditor().getText());
//        System.out.println(txtFdRegistroDataNascId.getValue().toString());
//        System.out.println(txtFdRegistroDataNascId.promptTextProperty().toString()); //.isEmpty();
//        System.out.println(txtFdRegistroDataNascId.editorProperty().toString()); //.isEmpty();
//        System.out.println(txtFdRegistroDataNascId.getEditor().toString());


//        String data = String.valueOf(txtFdRegistroDataNascId.getValue());

        /*try {
           txtFdRegistroDataNascId.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                   if (regexDados.validateJavaDate(txtFdRegistroDataNascId.getValue().toString())) {
                       System.out.println("Tu selecionaste a data : " + txtFdRegistroDataNascId.getValue());
                   } else {
                       System.out.println("Tu selecionaste a data : " + txtFdRegistroDataNascId.getValue());
                   }
               }
           });
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }*/

        String data = txtFdRegistroDataNascId.getEditor().getText();
        System.out.println(data);


        if (data.isEmpty()) {
            labeldErroDataNasc.setText("Tem de preencher a Data de Nascimento.");
        } else if (!this.regexDados.validateJavaDate(data) && verficaData == null) {
            labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato(dd/MM/yyyy)!");
        } else {
            this.utilizador.setDataNascimento(data);
            labeldErroDataNasc.setText("");
        }


        /*
        try {
            if (data.isEmpty()) {
                labeldErroDataNasc.setText("Tem de preencher a Data de Nascimento.");
            } else {
                this.utilizador.setDataNascimento(data);
                labeldErroDataNasc.setText("");
            }
        } catch (DateTimeParseException ex) {
            labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato(dd/MM/yyyy)!");
            System.out.println(ex.getMessage());
        }

         */


//        txtFdRegistroDataNascId.promptTextProperty().toString().isEmpty();
        /*
        try {
            if (data.equals(txtFdRegistroDataNascId.valueProperty().get().equals(""))) { //&& txtFdRegistroDataNascId.isEditable()
                //valueProperty().toString().isEmpty() passa na mesma mas fica a branco
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //, Locale.US
//                // Converting given date string to LocalDate
//                LocalDate dateTime = LocalDate.parse(data, dtf);
//                txtFdRegistroDataNascId.setValue(null);
                labeldErroDataNasc.setText("Tem de preencher a Data de Nascimento.");
                //return;
            }

//        } else if (!this.regexDados.validateJavaDate(data)) {
//            labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato(dd/MM/yyyy)!");
//        }
            else {
//            LocalDate selectedDate = txtFdRegistroDataNascId.getValue();
                this.utilizador.setDataNascimento(data); //Date.valueOf(txtFdRegistroDataNascId.getValue()) // data.toString()
//            this.utilizador.setDataNascimento(Date.valueOf(txtFdRegistroDataNascId.getEditor().getText()));
                System.out.println("-------------------------------");
                System.out.println("-------------------------------");
                System.out.println("-------------------------------");
                System.out.println(txtFdRegistroDataNascId.getValue());
                System.out.println(txtFdRegistroDataNascId.getEditor().getText()); //usar este
                System.out.println("-------------------------------");
                System.out.println("-------------------------------");
                System.out.println("-------------------------------");
            }
        } catch (DateTimeParseException ex) {
            labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato(dd/MM/yyyy)!");
            System.out.println(ex.getMessage());
        }
         */


//
////        txtFdRegistroDataNascId.promptTextProperty().toString().isEmpty();
//        try {
//            if (data == null) { //&& txtFdRegistroDataNascId.isEditable()
//                //txtFdRegistroDataNascId.setValue(null);
//                labeldErroDataNasc.setText("Tem de preencher a Data de Nascimento.");
//                //return;
//            }
////        } else if (!this.regexDados.validateJavaDate(data)) {
////            labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato(dd/MM/yyyy)!");
////        }
//            else {
////            LocalDate selectedDate = txtFdRegistroDataNascId.getValue();
//                this.utilizador.setDataNascimento(data); //Date.valueOf(txtFdRegistroDataNascId.getValue())
//                System.out.println(data);
////            this.utilizador.setDataNascimento(Date.valueOf(txtFdRegistroDataNascId.getEditor().getText()));
//                System.out.println("-------------------------------");
//                System.out.println("-------------------------------");
//                System.out.println("-------------------------------");
//                System.out.println(txtFdRegistroDataNascId.getValue());
//                System.out.println(txtFdRegistroDataNascId.getEditor().getText()); //usar este
//                System.out.println("-------------------------------");
//                System.out.println("-------------------------------");
//                System.out.println("-------------------------------");
//            }
//        } catch (DateTimeParseException ex) {
//            labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato(dd/MM/yyyy)!");
//            System.out.println(ex.getMessage());
//        }
//


//        this.utilizador.setDataNascimento(txtFdRegistroDataNascId.getEditor().getText());

//        if (txtFdRegistroDataNascId.getValue() == null) { //&& txtFdRegistroDataNascId.isEditable()
//            labeldErroDataNasc.setText("Tem de preencher a Data de Nascimento.");
//        } else if (!this.regexDados.validateJavaDate(String.valueOf(txtFdRegistroDataNascId.getValue()))) {
//            labeldErroDataNasc.setText("Por favor, preencha a Data Corretamente no formato(dd/MM/yyyy)!");
//        } else{
////            this.utilizador.setDataNascimento(Date.valueOf(txtFdRegistroDataNascId.getValue()));
//            this.utilizador.setDataNascimento(Date.valueOf(txtFdRegistroDataNascId.getEditor().getText()));
//        }

        if (labelIdErroNomeCompleto.getText().equals("") && labelIdErroNumCC.getText().equals("") && labelIdErroNIF.getText().equals("") &&
                labelIdErroContacto.getText().equals("") && labeldErroDataNasc.getText().equals("")) {
            this.utilizador.setEstadoUtilizador(EstadoUtilizador.PENDENTE);
            this.utilizadorDao.registrar(utilizador);
            this.goToUtil.goToRegistroDadosLocalizacao();
            Stage stage = (Stage) btnRegistrarSeguinteId.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void hyperlinkLogin(ActionEvent event) {
        this.goToUtil.goToLogin();
        Stage stage = (Stage) hyperlinkLoginId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void verificaData(KeyEvent event) {
        /*
        txtFdRegistroDataNascId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (regexDados.validateJavaDate(txtFdRegistroDataNascId.getValue().toString())) {
                    System.out.println("Tu selecionaste a data : " + txtFdRegistroDataNascId.getValue());
                } else {
                    System.out.println("Tu selecionaste a data : " + txtFdRegistroDataNascId.getValue());
                }
            }
        });

         */

        LocalDate verficaData = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            verficaData = LocalDate.parse(txtFdRegistroDataNascId.getEditor().getText(), formatter);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }

        if (verficaData == null) {
            System.out.println("erro");
        } else {
            System.out.println("certo");
        }
    }

    @FXML
    void verificaDataClicked(MouseEvent event) {
        LocalDate verficaData = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            verficaData = LocalDate.parse(txtFdRegistroDataNascId.getEditor().getText(), formatter);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }

        if (verficaData == null) {
            System.out.println("erro");
        } else {
            System.out.println("certo");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManager = JPAUtil.getEntityManager();
        this.utilizadorDao = new UtilizadorDao(entityManager);
        this.utilizador = new Utilizador();
        this.regexDados = new RegexDados();
        this.goToUtil = new GoToUtil();
    }
}
