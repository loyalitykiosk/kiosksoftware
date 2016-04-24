/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyalitypointmanagement.ExistingCustomer;

import dataprovider.CustomerDetailsGetSet;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import loyalitypointmanagement.Dashboard.FXMLDocumentController;
import loyalitypointmanagement.Dashboard.LoyalityPointManagement;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author parwez
 */
public class ExistingcustomerController implements Initializable {

    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    @FXML
    private Label cust_name;
    @FXML
    private Label cust_addrs;
    @FXML
    private Label cust_phone;
    @FXML
    private Label cust_point;
    String name, address, phone, point;
    private CustomerDetailsGetSet cust_info;
    @FXML
    private Button btn_submit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setCust_info(CustomerDetailsGetSet cust_info) {
        this.cust_info = cust_info;
        cust_name.setText(cust_info.getCust_name());
        cust_addrs.setText(cust_info.getCust_address());
        cust_phone.setText(cust_info.getCust_phone());
        cust_point.setText(cust_info.getCust_point());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Customer Confirmation");
        alert.setHeaderText("");
        alert.getDialogPane().setPrefSize(600.0, 300.0);
        String s = "Welcome Mr/Ms " + cust_name.getText().toString() + ". We appreciate your business. Your current blanace is " + cust_point.getText().toString();
        alert.setContentText(s);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("newCustomerCSS.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-font-size: 24px;"
                + "-fx-background-color: indianred;");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family: \"Andalus\";"));
        Optional<ButtonType> result1 = alert.showAndWait();
        if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {
        }
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Delay 5sec");
                        //System.exit(0);
                        Application.launch(LoyalityPointManagement.class, (java.lang.String[]) null);
                    }
                }, 5000);
    }

    @FXML
    public void return_Dashboard(ActionEvent event) {
        try {
            //new LoyalityPointManagement().showDialog();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void backDashBoard() {
        System.out.println("method call Delay 5sec");
        this.btn_submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("method call h Delay 5sec");
                try {
                    Parent root;
                    root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Dashboard");
                    stage.setMaximized(true);
                    stage.setFullScreen(true);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }//end of handle
        }); //end of action event and set on action

    }
}
