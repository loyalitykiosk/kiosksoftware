/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loyalty.controllers;

import com.loyalty.dto.CardDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author parwez
 */
public class ExistingCustomerController extends AbstractController<CardDTO>  {

    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    @FXML
    private Label cust_name;
    @FXML
    private Label cust_email;
    @FXML
    private Label cust_phone;
    @FXML
    private Label cust_point;
    @FXML
    private Label cust_status;
    @FXML
    private Label cust_type;

    @FXML
    private Button btn_submit;

    @FXML
    public void ok(ActionEvent event) {
        closeWindow();
    }

    @Override
    public void windowShown() {
        schedule(()->closeWindow());
        cust_name.setText(getModel().getOwnerName()+" "+getModel().getOwnerSurname());
        cust_email.setText(getModel().getEmail());
        cust_phone.setText(getModel().getSmsNumber());
        cust_point.setText(getModel().getBalance().toString());
        cust_status.setText(getModel().getStatus());
        cust_type.setText(getModel().getType());
    }

    @Override
    public void windowHide() {
        stopTimer();
    }

    @Override
    public void closeWindow() {
        rect_panel.getScene().getWindow().hide();
    }
}
