/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyalitypointmanagement.Dashboard;

import dataprovider.IPAddress;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import loyalitypointmanagement.applicationhelper.AppHelper;
import loyalitypointmanagement.lostmycard.LostCardController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author partha
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    private String ipAddress = "0.0.0.0";
    private final static String KIOSK_REGISTER_URL = "http://senindia.co.in/kioskadmin/APIv1.0/reg_kiosk_new_action.jsp";
    private static final String GET_PROMOTION_OFFER_URL = "http://senindia.co.in/kioskadmin/APIv1.0/offerdetail.jsp";
    // private final static String ALLOCATE_IP_WITH_KIOSK_URL = "http://senindia.co.in/kioskadmin/APIv1.0/allocate_ip_with_kiosk_action.jsp";
    private String response = "";
    private String response_promotion = "";

    //private String responseallc = "";
    @FXML
    private Label lbl_promotion;
    @FXML
    private Pane pane_check;
    @FXML
    private Pane pane_new_cust;
    @FXML
    private Pane pane_lost_card;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ipAddress = new IPAddress().getIPAddress();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("ip", ipAddress);
        params.put("status", "");
        response = new AppHelper().performPostCall(KIOSK_REGISTER_URL, params);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String msg = jsonObject.getString("msg");
            System.out.println(msg);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        response_promotion = new AppHelper().performPostCall(GET_PROMOTION_OFFER_URL, params);
        System.err.println(response_promotion);
        if (response_promotion != null) {
            try {
                JSONArray jaaraa = new JSONArray(response_promotion);
                // for (int i = 0; i < jaaraa.length(); i++) {
                JSONObject object = jaaraa.getJSONObject(0);
                String offername = object.getString("offername");
                String promotionname = object.getString("promotionname");
                String description = object.getString("description");
                String expire = object.getString("expire");
                //JSONObject JOBJ = new JSONObject(response_promotion[0]);
                // System.out.println("parwez" + aa);
                // }
                lbl_promotion.setText(promotionname + " and " + offername + " be hurry offer valid till " + expire);
                lbl_promotion.setStyle("-fx-background-color: #DF013A;-fx-background-radius: 10 10 10 10;-fx-height:90px;");
                TranslateTransition transition = TranslateTransitionBuilder.create()
                        .duration(new Duration(7500))
                        .node(lbl_promotion)
                        .interpolator(Interpolator.LINEAR)
                        .cycleCount(Timeline.INDEFINITE)
                        .build();

                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = 1;
                        //gd.getDisplayMode().getWidth();

                transition.setFromX(width);
                transition.setToX(-width);
                transition.play();

            } catch (JSONException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }

    }

    @FXML
    public void existCustomer(MouseEvent event) {
        //new LoyalityPointManagement().showDialog();
        try {
            //new LoyalityPointManagement().showDialog();
            Parent root;
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/ExistingCustomer/existinitial.fxml"));
            //root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/lostmycard/LostCard.fxml"));
            //Stage stage = new Stage();
            stage.setTitle("Existing Customer");
            stage.setMaximized(true);
            stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void checkInOut(MouseEvent event) {
        try {
            //new LoyalityPointManagement().showDialog();
            Parent root;
            //root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/ExistingCustomer/SwipeCard.fxml"));
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/checkinout/CheckInOut.fxml"));
            Stage stage = new Stage();
            //Stage stage = (Stage) root.getScene().getWindow();
            stage.setTitle("Checkin/Checkout");

            stage.setMaximized(true);
            stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void newCustomer(MouseEvent event) {
        try {
            //new LoyalityPointManagement().showDialog();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/NewCustomer/newCustomerFXML.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Customer Registration");
            stage.setMaximized(true);
            stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void lostCard(MouseEvent event) {
        //new LoyalityPointManagement().showDialog();
        try {
            //new LoyalityPointManagement().showDialog();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/lostmycard/LostCard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lost Card");
            stage.setMaximized(true);
            stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
