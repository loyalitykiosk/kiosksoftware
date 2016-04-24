/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyalitypointmanagement.lostmycard;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loyalitypointmanagement.Dashboard.FXMLDocumentController;
import loyalitypointmanagement.applicationhelper.AppHelper;
import org.json.JSONException;
import org.json.JSONObject;
import dataprovider.CustomerDetailsGetSet;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
import loyalitypointmanagement.ExistingCustomer.ExistingcustomerController;
import static loyalitypointmanagement.NewCustomer.NewCustomerController.ACCOUNT_SID;
import static loyalitypointmanagement.NewCustomer.NewCustomerController.AUTH_TOKEN;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * FXML Controller class
 *
 * @author parwez
 */
public class LostCardController implements Initializable {

    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    @FXML
    private TextField txt_memberid;
    @FXML
    private Button btn_submit;
    private final static String MEMBER_DETAILS = "http://senindia.co.in/kioskadmin/APIv1.0/member_details_action.jsp";
    String name, phone, address, point;
    HashMap<String, String> mem_details;
    public String membr;

    public static final String ACCOUNT_SID = "AC6f0edff72532f2d6969e31dd52ca74d2";
    public static final String AUTH_TOKEN = "57541a7cc331fe7a4b9cd5714db98929";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ReturnDashboard(MouseEvent event) {
//        try {
//            //new LoyalityPointManagement().showDialog();
////        try{
////            Parent root;
////            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
////            Stage stage = new Stage();
////            stage.setTitle("Dashboard");
////            stage.setScene(new Scene(root,pane_existing_cust.getScene().getWidth(),pane_existing_cust.getScene().getHeight()));
////            stage.show();
////            ((Node)(event.getSource())).getScene().getWindow().hide();
////        } catch (IOException ex) {
////            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
////        }
//            Stage stage = new Stage();
//            new loyalitypointmanagement.Dashboard.LoyalityPointManagement().start(stage);
//            ((Node)(event.getSource())).getScene().getWindow().hide();
//        } catch (Exception ex) {
//            Logger.getLogger(LostCardController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            //new LoyalityPointManagement().showDialog();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lost My Card");
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.initStyle(StageStyle.UNDECORATED);
            //stage.setFullScreen(false);
            stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void viewMemberDetails(ActionEvent event) throws JSONException, TwilioRestException, IOException {
        String result = "";
        HashMap<String, String> params = new HashMap<>();
        params.put("mem_id", txt_memberid.getText().toString());
        System.out.println(txt_memberid.getText().toString());
        result = new AppHelper().performPostCall(MEMBER_DETAILS, params);
        JSONObject jsonObject = new JSONObject(result);
        System.out.println("" + jsonObject);
        String msg = jsonObject.getString("msg");
        if (msg.equals("1")) {
            name = jsonObject.getString("fullname");
            address = jsonObject.getString("present_address");
            point = jsonObject.getString("points");
            phone = jsonObject.getString("phone_no");
            System.out.println(name);
//            mem_details = new HashMap<String,String>();
//            mem_details.put("name", name);
//            mem_details.put("address", address);
//            mem_details.put("point", point);
//            mem_details.put("phone", phone);
            membr = name + "~" + address + "~" + point + "~" + phone;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("");
            alert.getDialogPane().setPrefSize(600.0, 300.0);
            String s = "Welcome Mr/Ms " + name + ". We appreciate your business. Please Contact to our Admin";
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

            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
            // Build the parameters 
            List<NameValuePair> params_sms = new ArrayList<NameValuePair>();
            params_sms.add(new BasicNameValuePair("From", "+12028313299"));
            params_sms.add(new BasicNameValuePair("To", "+91" + phone)); // Replace with a valid phone number
            params_sms.add(new BasicNameValuePair("Body", s));
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params_sms);
            System.out.println(message.getSid());

            if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Dash Board");
                stage.setMaximized(true);
                stage.setFullScreen(true);
                stage.initStyle(StageStyle.UNDECORATED);
                //stage.setFullScreen(false);
                stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                txt_memberid.getScene().getWindow().hide();
            }

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
//                stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
//                ExistingcustomerController existController = loader.getController();
//                existController.setCust_info(cust_details);
//                stage.show();
//                ((Node) (event.getSource())).getScene().getWindow().hide();
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else {
            // try {
            //new LoyalityPointManagement().showDialog();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Customer Confirmation");
            alert.setHeaderText("");
            alert.getDialogPane().setPrefSize(600.0, 300.0);
            String s = "Sorry! No Customer found.";
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
//                if ((result1.isPresent()) && (result1.get() == ButtonType.OK)) {
//                    Parent root;
//                    root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
//                    Stage stage = new Stage();
//                    stage.setTitle("Dash Board");
//                    stage.setMaximized(true);
//                    stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
//                    stage.show();
//                    //((Node) (event.getSource())).getScene().getWindow().hide();
//                    txt_memberid.getScene().getWindow().hide();
//                }

            //  }
        }
    }

    @FXML
    private void Back_Dashboadrd(MouseEvent event) {
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dash Board");
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.initStyle(StageStyle.UNDECORATED);
            //stage.setFullScreen(false);
            stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(LostCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
