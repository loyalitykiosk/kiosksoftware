/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.loyalty.controllers;

import com.loyalty.dto.CardDTO;
import com.loyalty.dto.CheckInDTO;
import com.loyalty.exception.KioskException;
import com.loyalty.ui.FXMLBean;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.http.HttpException;

import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author parwez
 */
public class CheckInController extends AbstractController<CheckInDTO> {
    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    @FXML
    private TextField hiddenCardField;

    @Override
    public void windowShown() {
        schedule(()->closeWindow());
        rect_panel.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                if (hiddenCardField.getText().length() == 0) {
                    getDialogManager().showInformation("Card number should have at least 1 symbol");
                    closeWindow();
                    return;
                }
                try {
                    stopTimer();
                    final CardDTO cardDTO = getService().checkIn(hiddenCardField.getText());
                    FXMLBean existingCustomer = getContext().getBean("existingcustomer", FXMLBean.class);
                    existingCustomer.getController().setModel(cardDTO);
                    existingCustomer.getRegion().showAndWait();
                } catch (KioskException ke) {
                    getDialogManager().showInformation(ke.getMessage());
                } catch (IOException ioe){
                    getDialogManager().showInformation("No Connection with server.");
                }
                finally {
                    closeWindow();
                }
            }else if (event.getCode().isDigitKey() || event.getCode().isLetterKey()){
               hiddenCardField.setText(hiddenCardField.getText()+event.getText());
            }
        });
    }

    @FXML
    private void back(Event event) {
        closeWindow();
    }

    @Override
    public void windowHide() {
        hiddenCardField.setText("");
        stopTimer();
    }

    @Override
    public void closeWindow() {
        rect_panel.getScene().getWindow().hide();
    }
}
