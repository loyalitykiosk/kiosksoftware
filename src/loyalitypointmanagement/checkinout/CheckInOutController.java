/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyalitypointmanagement.checkinout;

import dataprovider.CustomerDetailsGetSet;
import dataprovider.IPAddress;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loyalitypointmanagement.Dashboard.FXMLDocumentController;
import loyalitypointmanagement.ExistingCustomer.ExistingcustomerController;
import loyalitypointmanagement.applicationhelper.AppHelper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author partha
 */
public class CheckInOutController implements Initializable {

    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    @FXML
    private TextField txt_memberid;
    @FXML
    private Button btn_submit;
    private String member_id;
    private String name, address, phone, point;
    private static final String CUSTOMER_DETAILS = "http://senindia.co.in/kioskadmin/APIv1.0/member_details_action.jsp";
    private static final String CUSTOMER_LOGIN = "http://senindia.co.in/kioskadmin/APIv1.0/member_check_in_login_action.jsp";
    private static final String CHKSTATUS = "http://senindia.co.in/kioskadmin/APIv1.0/member_login_check.jsp";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void viewMemberDetails(ActionEvent event) throws JSONException {
        System.out.println("working");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Check-in or Check-out Confirmation");
        alert.setHeaderText("");
        alert.getDialogPane().setPrefSize(600.0, 300.0);
        String s = "Please select your option from below.";
        alert.setContentText(s);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("newCustomerCSS.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        //ButtonType checkinButton = new ButtonType("Check-in");
        //ButtonType checkoutButton = new ButtonType("Check-out");
        //ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        ButtonType checkinButton = new ButtonType("Check-in");
        ButtonType checkoutButton = new ButtonType("Check-out");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-font-size: 24px;"
                + "-fx-background-color: indianred;");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family: \"Andalus\";"));

        alert.getButtonTypes().setAll(checkinButton,  cancelButton);
        
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == checkinButton) {
            if (!txt_memberid.getText().toString().equals("")) {
                try {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("mem_id", txt_memberid.getText().toString());

                    String response = new AppHelper().performPostCall(CHKSTATUS, params);
                    JSONObject jobject = new JSONObject(response);
                    System.out.println(jobject.getString("msg"));
                    if (jobject.getString("msg").equals("0")) {
                        HashMap<String, String> paramss = new HashMap<String, String>();
                        paramss.put("mem_id", txt_memberid.getText().toString());
                        String responses = new AppHelper().performPostCall(CUSTOMER_DETAILS, paramss);
                        JSONObject jobjects = new JSONObject(responses);
                        if (jobjects.getString("msg").equals("1")) {
                            name = jobjects.getString("fullname");
                            address = jobjects.getString("present_address");
                            phone = jobjects.getString("phone_no");
                            point = jobjects.getString("points");
                        }
                        CustomerDetailsGetSet cust_details = new CustomerDetailsGetSet(name, address, phone, point);
                        String ip = new IPAddress().getIPAddress();
                        HashMap<String, String> paramc = new HashMap<String, String>();
                        paramc.put("mem_id", txt_memberid.getText().toString());
                        paramc.put("kiosk_id", ip);
                        paramc.put("status", "Check In");
                        paramc.put("points", "20");
                        String res = new AppHelper().performPostCall(CUSTOMER_LOGIN, paramc);

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/loyalitypointmanagement/ExistingCustomer/existingcustomer.fxml"));
                        loader.load();
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setMaximized(true);
                        stage.setTitle("Existing Customer");
                        stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
                        ExistingcustomerController existController = loader.getController();
                        existController.setCust_info(cust_details);
                        stage.show();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } else {
                        System.out.println("sss");
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Alert");
                        alerta.setHeaderText("");
                        alerta.getDialogPane().setPrefSize(600.0, 300.0);
                        String sa = "You have already check-in. Please Check-out first";
                        alerta.setContentText(sa);
                        DialogPane dialogPanea = alerta.getDialogPane();
                        dialogPanea.getStylesheets().add(
                                getClass().getResource("newCustomerCSS.css").toExternalForm());
                        dialogPanea.getStyleClass().add("myDialog");
                        ButtonBar buttonBara = (ButtonBar) alerta.getDialogPane().lookup(".button-bar");
                        buttonBara.setStyle("-fx-font-size: 24px;"
                                + "-fx-background-color: indianred;");
                        buttonBara.getButtons().forEach(b -> b.setStyle("-fx-font-family: \"Andalus\";"));
                        Optional<ButtonType> result1 = alerta.showAndWait();
                        if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else if (result.get() == checkoutButton) {
            if (!txt_memberid.getText().toString().equals("")) {
                try {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("mem_id", txt_memberid.getText().toString());
                    String response = new AppHelper().performPostCall(CHKSTATUS, params);
                    JSONObject jobject = new JSONObject(response);
                    System.out.println(jobject.getString("msg"));
                    if (jobject.getString("msg").equals("1")) {

                        HashMap<String, String> paramss = new HashMap<String, String>();
                        paramss.put("mem_id", txt_memberid.getText().toString());
                        String responses = new AppHelper().performPostCall(CUSTOMER_DETAILS, paramss);
                        JSONObject jobjects = new JSONObject(responses);
                        if (jobjects.getString("msg").equals("1")) {
                            name = jobjects.getString("fullname");
                            address = jobjects.getString("present_address");
                            phone = jobjects.getString("phone_no");
                            point = jobjects.getString("points");
                        }
                        CustomerDetailsGetSet cust_details = new CustomerDetailsGetSet(name, address, phone, point);
                        String ip = new IPAddress().getIPAddress();
                        HashMap<String, String> paramc = new HashMap<String, String>();
                        paramc.put("mem_id", txt_memberid.getText().toString());
                        paramc.put("kiosk_id", ip);
                        paramc.put("status", "Check Out");
                        String res = new AppHelper().performPostCall(CUSTOMER_LOGIN, paramc);

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/loyalitypointmanagement/ExistingCustomer/existingcustomer.fxml"));
                        loader.load();
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setMaximized(true);
                        stage.setTitle("Existing Customer");
                        stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
                        ExistingcustomerController existController = loader.getController();
                        existController.setCust_info(cust_details);
                        stage.show();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } else {
                        System.out.println("sss");
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Alert");
                        alerta.setHeaderText("");
                        alerta.getDialogPane().setPrefSize(600.0, 300.0);
                        String sa = "Please Check-In first to continue.";
                        alerta.setContentText(sa);
                        DialogPane dialogPanea = alerta.getDialogPane();
                        dialogPanea.getStylesheets().add(
                                getClass().getResource("newCustomerCSS.css").toExternalForm());
                        dialogPanea.getStyleClass().add("myDialog");
                        ButtonBar buttonBara = (ButtonBar) alerta.getDialogPane().lookup(".button-bar");
                        buttonBara.setStyle("-fx-font-size: 24px;"
                                + "-fx-background-color: indianred;");
                        buttonBara.getButtons().forEach(b -> b.setStyle("-fx-font-family: \"Andalus\";"));
                        Optional<ButtonType> result1 = alerta.showAndWait();
                        if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {

                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

        }
    }

    @FXML
    private void BackDashBoard(MouseEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dash Board");
            stage.setMaximized(true);
            stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(CheckInOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
