//package org.example.util;
//
//import javafx.event.ActionEvent;
//import javafx.scene.control.Button;
//
//public class FiltraCards {
//    public FiltraCards(){
//
//    }
//
//    gridPane = new GridPane();
//    //idCardStock
//
//    private void addButton(String text, String id, int colIndex, int rowIndex) {
//        Button btnParteCima = new Button(text);
//        btnParteCima.setUserData(id); // Associate the ID with the button
//
//        btnParteCima.setOnAction(this::handleButtonClick);
//
//        gridPane.add(btnParteCima, colIndex, rowIndex);
//    }
//
//    private void handleButtonClick(ActionEvent event) {
//        Button btnParteCima = (Button) event.getSource();
//        String buttonId = (String) btnParteCima.getUserData();
//
//        System.out.println("Button ID: " + buttonId);
//        // Perform actions based on the button ID
//    }
//}
