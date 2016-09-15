/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loyalty.controllers;

import com.loyalty.dto.KioskDTO;
import com.loyalty.dto.PromotionDTO;
import com.loyalty.exception.KioskException;
import com.loyalty.ui.FXMLBean;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.http.HttpException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author partha
 */
public class DashboardController extends AbstractController<Void> implements Initializable, ApplicationContextAware {

    private Label label;
    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;

    @FXML
    private Text lbl_promotion;
    @FXML
    private Pane pane_check;
    @FXML
    private Pane pane_new_cust;
    @FXML
    private Pane pane_lost_card;

    private  KioskDTO kioskDTO;

    private ApplicationContext context;
    private java.util.List<PromotionDTO> promotionDTOs;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            kioskDTO = getService().activate();
            getScheduledExecutorService().scheduleAtFixedRate(this::renewPromotions,0,10, TimeUnit.SECONDS);
            animate();
        } catch (KioskException ke){
            getDialogManager().showInformation(ke.getMessage());
        } catch (IOException ioe) {
            getDialogManager().showInformation("Client Application could not login to server. Check Your credentials and Kiosk License.");
            Platform.exit();
        }
//        //ipAddress = new IPAddress().getIPAddress();
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("ip", ipAddress);
//        params.put("status", "");
//        response = new AppHelper().performPostCall(KIOSK_REGISTER_URL, params);
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            String msg = jsonObject.getString("msg");
//            System.out.println(msg);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//
//        response_promotion = new AppHelper().performPostCall(GET_PROMOTION_OFFER_URL, params);
//        System.err.println(response_promotion);
//        if (response_promotion != null) {
//            try {
//                JSONArray jaaraa = new JSONArray(response_promotion);
//                // for (int i = 0; i < jaaraa.length(); i++) {
//                JSONObject object = jaaraa.getJSONObject(0);
//                String offername = object.getString("offername");
//                String promotionname = object.getString("promotionname");
//                String description = object.getString("description");
//                String expire = object.getString("expire");
//                //JSONObject JOBJ = new JSONObject(response_promotion[0]);
//                // System.out.println("parwez" + aa);
//                // }
//                lbl_promotion.setText(promotionname + " and " + offername + " be hurry offer valid till " + expire);
//                lbl_promotion.setStyle("-fx-background-color: #DF013A;-fx-background-radius: 10 10 10 10;-fx-height:90px;");
//                TranslateTransition transition = TranslateTransitionBuilder.create()
//                        .duration(new Duration(7500))
//                        .node(lbl_promotion)
//                        .interpolator(Interpolator.LINEAR)
//                        .cycleCount(Timeline.INDEFINITE)
//                        .build();
//
//                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//                int width = 1;
//                //gd.getDisplayMode().getWidth();
//
//                transition.setFromX(width);
//                transition.setToX(-width);
//                transition.play();
//
//            } catch (JSONException ex) {
//                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//        }
        System.out.println("initialized");
    }

    private void animate(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        TranslateTransition transition = new TranslateTransition(new Duration(30000),lbl_promotion);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setFromX(screenDimension.getWidth());
                transition.setToX(-screenDimension.getWidth());
        transition.play();
    }

    @FXML
    public void existCustomer(MouseEvent event) {
        if (!isActivated()){
            getDialogManager().showInformation("Kiosk not activated. Try Later");
            return;
        }
        FXMLBean scancard = context.getBean("scancard",FXMLBean.class);
        scancard.getRegion().show();
    }

    @FXML
    public void checkInOut(MouseEvent event) {
        if (!isActivated()){
            getDialogManager().showInformation("Kiosk not activated. Try Later");
            return;
        }
        FXMLBean scancard = context.getBean("checkin",FXMLBean.class);
        scancard.getRegion().show();
    }

    @FXML
    public void newCustomer(MouseEvent event) {
        if (!isActivated()){
            getDialogManager().showInformation("Kiosk not activated. Try Later");
            return;
        }
        getDialogManager().showInformation("Not Implemented Yet");
//        count++;
//        if (count == 1) {
//            try {
//                Parent root;
//                root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/NewCustomer/newCustomerFXML.fxml"));
//                Stage stage = new Stage();
//                stage.setTitle("New Customer Registration");
//                stage.setMaximized(true);
//                stage.setFullScreen(true);
//                stage.initStyle(StageStyle.UNDECORATED);
//                //stage.setResizable(false);
//                stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
//                stage.show();
//                ((Node) (event.getSource())).getScene().getWindow().hide();
//            } catch (IOException ex) {
//                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        /*try {
         Parent root;
         root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/NewCustomer/newCustomerFXML.fxml"));
         Stage stage = new Stage();
         stage.setTitle("New Customer Registration");
         stage.setMaximized(true);
         stage.setFullScreen(true);
         stage.initStyle(StageStyle.UNDECORATED);
         //stage.setResizable(false);
         stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
         stage.show();
         ((Node) (event.getSource())).getScene().getWindow().hide();
         } catch (IOException ex) {
         Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
         }*/
//        System.out.println("New Customer Clicked");
//        Stage stage = (Stage)pane_new_cust.getScene().getWindow();
//        FXMLBean newcustomer = context.getBean("newcustomer", FXMLBean.class);
//        Stage stage = new Stage();
//        stage.setTitle("New Customer Registration");
//        stage.setScene(new Scene(newcustomer.getRegion()));
//        stage.setMaximized(true);
////        stage.setFullScreen(true);
//        stage.setAlwaysOnTop(true);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.show();
//        newcustomer.getRegion().show();

//        stage.ful(true);
    }

    @FXML
    public void lostCard(MouseEvent event) {
        getDialogManager().showInformation("Not Implemented Yet");
//        //new LoyalityPointManagement().showDialog();
//        try {
//            //new LoyalityPointManagement().showDialog();
//            Parent root;
//            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/lostmycard/LostCard.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("Lost Card");
//            stage.setMaximized(true);
//            stage.setFullScreen(true);
//            stage.initStyle(StageStyle.UNDECORATED);
//            //stage.setResizable(false);
//            stage.setScene(new Scene(root, pane_existing_cust.getScene().getWidth(), pane_existing_cust.getScene().getHeight()));
//            stage.show();
//            ((Node) (event.getSource())).getScene().getWindow().hide();
//        } catch (IOException ex) {
//            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("Lost Card Clicked");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    @Override
    public void windowShown() {

    }

    @Override
    public void windowHide() {

    }

    @Override
    public void setModel(Void object) {

    }

    @Override
    public void closeWindow() {
        //Main window should never be closed
    }

    private boolean isActivated(){
        return kioskDTO != null;
    }

    private void renewPromotions(){
        String promotions = null;
        try {
            promotions = getService().getPromotions().stream().map(promo -> promo.getDescription()).collect(Collectors.joining("; "));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String finalPromotions = promotions;
        Platform.runLater(()->lbl_promotion.setText(finalPromotions));
    }
}
