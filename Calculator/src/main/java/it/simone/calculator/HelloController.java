package it.simone.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML private Button minusButton;
    @FXML private Button perButton;
    @FXML private Button plusButton;
    @FXML private Button divisionButton ;

    @FXML private TextField partialTextField ;
    @FXML private TextField resultTextField ;

    private static final String NOT_RESULT_CONSTANT = "NOT_RESULT" ;


    private String firstOperand = "";
    private String secondOperand = "";
    private String currentResult = NOT_RESULT_CONSTANT ;
    private Button requestedOperation ;


    @FXML
    public void onNumberButtonClicked(ActionEvent event) {

        Button clickedButton = (Button) event.getSource() ;
        String buttonNumber = clickedButton.getText() ;
        if (requestedOperation == null) {
            firstOperand = firstOperand + buttonNumber ;
            partialTextField.setText(firstOperand);
        }
        else {
            secondOperand = secondOperand + buttonNumber ;
            partialTextField.setText(secondOperand);
        }
    }

    @FXML
    public void onOperationButtonClicked(ActionEvent event) {
        Button operation = (Button) event.getSource() ;
        if (requestedOperation == null) {
            if (currentResult.compareTo(NOT_RESULT_CONSTANT) != 0 && firstOperand.isEmpty()) {
                firstOperand = currentResult ;
            }
            requestedOperation = operation ;
        }
        else {
            onEqualsButtonClicked();
            onOperationButtonClicked(event);
        }

    }

    @FXML
    public void onClearButtonClicked() {
        firstOperand = "" ;
        secondOperand = "" ;
        currentResult = NOT_RESULT_CONSTANT ;
        partialTextField.setText("");
        resultTextField.setText("");
        requestedOperation = null ;
     }

    @FXML
    public void onEqualsButtonClicked() {
        try {
            Double doubleFirstOperand = Double.parseDouble(firstOperand) ;
            Double doubleSecondOperand = Double.parseDouble(secondOperand) ;
            Double result ;
            if (requestedOperation == plusButton) result = doubleFirstOperand + doubleSecondOperand ;
            else if (requestedOperation == minusButton) result = doubleFirstOperand - doubleSecondOperand ;
            else if (requestedOperation == perButton) result = doubleFirstOperand * doubleSecondOperand ;
            else if (requestedOperation == divisionButton) result = doubleFirstOperand / doubleSecondOperand ;
            else return ;

            onClearButtonClicked();

            currentResult = String.valueOf(result);
            resultTextField.setText(currentResult);
        }
        catch(NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "ERRORE NEI NUMERI DIGITATI: RIPROVARE") ;
            alert.showAndWait() ;
            onClearButtonClicked();
        }
        catch(ArithmeticException arithmeticException) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "IMPOSSIBILE DIVIDERE PER ZERO") ;
            alert.showAndWait() ;
            onClearButtonClicked();
        }

    }

    @FXML
    public void onChangeSignClicked() {
        if (requestedOperation == null) {
            firstOperand = (firstOperand.contains("-")) ? firstOperand.substring(1) : "-" + firstOperand ;
            partialTextField.setText(firstOperand);
        }
        else {
            secondOperand = (secondOperand.contains("-")) ? secondOperand.substring(1) : "-" + secondOperand ;
            partialTextField.setText(secondOperand);
        }
    }

    @FXML
    public void onPointButtonClicked() {
        if (requestedOperation == null) {
            firstOperand = (firstOperand.contains(".") || firstOperand.length() == 0) ? firstOperand : firstOperand + "." ;
            partialTextField.setText(firstOperand);
        }
        else {
            secondOperand = (secondOperand.contains(".") || secondOperand.length() == 0) ? secondOperand : secondOperand + "." ;
            partialTextField.setText(secondOperand);
        }
    }

    @FXML
    public void onCancButtonClicked() {
        if (requestedOperation == null) {
            firstOperand = (firstOperand.length() > 0 ) ? firstOperand.substring(0, firstOperand.length() - 1) : firstOperand ;
            partialTextField.setText(firstOperand);
        }
        else {
            secondOperand = (secondOperand.length() > 0 ) ? secondOperand.substring(0, secondOperand.length() - 1) : secondOperand ;
            partialTextField.setText(secondOperand);
        }
    }
}