/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyalitypointmanagement.NewCustomer;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import dataprovider.IPAddress;
import loyalitypointmanagement.applicationhelper.AppHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import loyalitypointmanagement.Dashboard.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.nashorn.internal.runtime.options.Option;
import loyalitypointmanagement.lostmycard.LostCardController;
import static loyaltypointmanagement.sms.api.TestSMSApi.ACCOUNT_SID;
import static loyaltypointmanagement.sms.api.TestSMSApi.AUTH_TOKEN;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author partha
 */
public class NewCustomerController implements Initializable {

    private Label label;
    @FXML
    private VBox rect_panel;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_phone;
    @FXML
    private TextField txt_email;
    @FXML
    private TextArea txt_address;
    @FXML
    private Button btn_submit;
    private String ipAddress = "0.0.0.0";

    private static final String REG_URL = "http://senindia.co.in/kioskadmin/APIv1.0/mem_reg_api_action.jsp    ";

    JSONObject jobj;
    public static final String ACCOUNT_SID = "AC6f0edff72532f2d6969e31dd52ca74d2";
    public static final String AUTH_TOKEN = "57541a7cc331fe7a4b9cd5714db98929";

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void submitRegistration(ActionEvent event) throws JSONException, TwilioRestException {
        String result = "";
        ipAddress = new IPAddress().getIPAddress();
        HashMap<String, String> params = new HashMap<>();
        params.put("mem_name", txt_name.getText().toString());
        params.put("phone_no", txt_phone.getText().toString());
        params.put("email_id", txt_email.getText().toString());
        params.put("address", txt_address.getText().toString());
        params.put("kiosk_ip", ipAddress);
        result = new AppHelper().performPostCall(REG_URL, params);
        JSONObject jsonObject = new JSONObject(result);
        String msg = jsonObject.getString("msg");
        System.out.println(msg);

        if (msg.equals("1")) {
            try {
                //new LoyalityPointManagement().showDialog();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New Customer Confirmation");
                alert.setHeaderText("");
                alert.getDialogPane().setPrefSize(600.0, 300.0);
                String s = "Congratulations you have been registered. \nPlease check your phone for updates and free loyalty points.";
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
                params_sms.add(new BasicNameValuePair("To", "+1" + txt_phone.getText().toString())); // Replace with a valid phone number
                params_sms.add(new BasicNameValuePair("Body", "Congratulations you have been registered.!\n You have got 200 points for new registration."));
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
                    //stage.setResizable(false);
                    //stage.setFullScreen(false);
                    stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
                    stage.show();
                    //((Node) (event.getSource())).getScene().getWindow().hide();
                    txt_address.getScene().getWindow().hide();
                }

            } catch (IOException ex) {
                Logger.getLogger(NewCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

        }
    }

    @FXML
    private void Back_Dashboard(MouseEvent event) throws IOException {

        Parent root;
        root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/Dashboard/FXMLDocument.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dash Board");
        stage.setMaximized(true);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, rect_panel.getScene().getWidth(), rect_panel.getScene().getHeight()));
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
