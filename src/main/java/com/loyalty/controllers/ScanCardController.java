/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loyalty.controllers;

import com.loyalty.dto.CardDTO;
import com.loyalty.exception.KioskException;
import com.loyalty.ui.FXMLBean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * FXML Controller class
 *
 * @author parwez
 */
public class ScanCardController  extends  AbstractController<Void> {

    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    @FXML
    private TextField txt_memberid;
    @FXML
    private Button btn_submit;
    @FXML
    private TextField hiddenCardField;

    String name, phone, address, point;
    HashMap<String, String> mem_details;
    public String membr;

    @FXML
    private void viewMemberDetails(ActionEvent event) {

//        String result = "";
//        HashMap<String, String> params = new HashMap<>();
//        params.put("mem_id", txt_memberid.getText().toString());
//
//        result = new AppHelper().performPostCall(MEMBER_DETAILS, params);
//        JSONObject jsonObject = new JSONObject(result);
//        System.out.println("" + jsonObject);
//        String msg = jsonObject.getString("msg");
//        if (msg.equals("1")) {
//            name = jsonObject.getString("fullname");
//            address = jsonObject.getString("present_address");
//            point = jsonObject.getString("points");
//            phone = jsonObject.getString("phone_no");
//            System.out.println(name);
////            mem_details = new HashMap<String,String>();
////            mem_details.put("name", name);
////            mem_details.put("address", address);
////            mem_details.put("point", point);
////            mem_details.put("phone", phone);
//            membr = name + "~" + address + "~" + point + "~" + phone;
//
//            //Alert alert = new Alert(Alert.AlertType.INFORMATION);
////        alert.setTitle("Alert");
////        alert.setHeaderText("");
////        alert.getDialogPane().setPrefSize(600.0, 300.0);
////        String s = "Welcome Mr/Ms "+name+  ". We appreciate your business. Please Contact to our Admin";
////        alert.setContentText(s);
////        DialogPane dialogPane = alert.getDialogPane();
////        dialogPane.getStylesheets().add(
////                getClass().getResource("newCustomerCSS.dashboard").toExternalForm());
////        dialogPane.getStyleClass().add("myDialog");
////        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
////        buttonBar.setStyle("-fx-font-size: 24px;"
////                + "-fx-background-color: indianred;");
////        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family: \"Andalus\";"));
//            //Optional<ButtonType> result1 = alert.showAndWait();
//            // if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {
////                    Parent root;
////                    root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
////                    Stage stage = new Stage();
////                    stage.setTitle("Dash Board");
////                    stage.setMaximized(true);
////                    stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
////                    stage.show();
////                    //((Node) (event.getSource())).getScene().getWindow().hide();
////                    txt_memberid.getScene().getWindow().hide();
//            // }
//            try {
//                //new LoyalityPointManagement().showDialog();
//                CustomerDetailsGetSet cust_details = new CustomerDetailsGetSet(name, address, phone, point);
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/loyalitypointmanagement/ExistingCustomer/existingcustomer.fxml"));
//                loader.load();
//                Parent root = loader.getRoot();
//                Stage stage = new Stage();
//                //Stage stage = (Stage) root.getScene().getWindow();
//                stage.setTitle("Existing Customer");
//                stage.setMaximized(true);
//                stage.setFullScreen(true);
//                stage.initStyle(StageStyle.UNDECORATED);
//                stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
//                ExistingcustomerController existController = loader.getController();
//                existController.setCust_info(cust_details);
//                stage.show();
//                ((Node) (event.getSource())).getScene().getWindow().hide();
//
//            } catch (IOException ex) {
//                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            // try {
//            //new LoyalityPointManagement().showDialog();
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Existing Customer");
//            alert.setHeaderText("");
//            alert.getDialogPane().setPrefSize(600.0, 300.0);
//            String s = "Sorry! No Customer found.";
//            alert.setContentText(s);
//
//            DialogPane dialogPane = alert.getDialogPane();
//            dialogPane.getStylesheets().add(
//                    getClass().getResource("newCustomerCSS.dashboard").toExternalForm());
//            dialogPane.getStyleClass().add("myDialog");
//            ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
//            buttonBar.setStyle("-fx-font-size: 24px;"
//                    + "-fx-background-color: indianred;");
//            buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family: \"Andalus\";"));
//
//            Optional<ButtonType> result1 = alert.showAndWait();
////                if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {
////                    Parent root;
////                    root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
////                    Stage stage = new Stage();
////                    stage.setTitle("Dash Board");
////                    stage.setMaximized(true);
////                    stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
////                    stage.show();
////                    //((Node) (event.getSource())).getScene().getWindow().hide();
////                    txt_memberid.getScene().getWindow().hide();
////                }
//
//            //  }
//        }
    }

    @FXML
    private void Back_Dashboadrd(MouseEvent event) {
        ((Stage)rect_panel.getScene().getWindow()).hide();
    }

    @FXML
    private void back(Event event) {
        closeWindow();
    }

//    public void submit(Event event) {
//        if (txt_memberid.getText().length() != 10) {
//            getDialogManager().showInformation("Card number should have 10 symbols");
//            return;
//        }
//        CardDTO cardDTO = null;
//        try {
//            cardDTO = getService().cardDetails(txt_memberid.getText());
//            FXMLBean existingCustomer = getContext().getBean("existingcustomer", FXMLBean.class);
//            existingCustomer.getController().setModel(cardDTO);
//            existingCustomer.getRegion().showAndWait();
//            closeWindow();
//        } catch (KioskException ke) {
//            getDialogManager().showInformation(ke.getMessage());
//        }catch (IOException ioe) {
//            getDialogManager().showError(ioe, "Communication error", ButtonType.OK);
//        }
//    }

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
                    final CardDTO cardDTO = getService().cardDetails(hiddenCardField.getText());
                    FXMLBean existingCustomer = getContext().getBean("existingcustomer", FXMLBean.class);
                    existingCustomer.getController().setModel(cardDTO);

                    existingCustomer.getRegion().showAndWait();
                } catch (KioskException ke) {
                    getDialogManager().showInformation(ke.getMessage());
                } catch (IOException ioe){
                    //getDialogManager().showError(ioe, "Can't send request to server.", ButtonType.OK);
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
