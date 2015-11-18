/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyalitypointmanagement.Dashboard;

import dataprovider.IPAddress;
import java.util.HashMap;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import loyalitypointmanagement.applicationhelper.AppHelper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author partha
 */
public class LoyalityPointManagement extends Application {

    private String ipAddress = "0.0.0.0";
    private final static String ALLOCATE_IP_WITH_KIOSK_URL = "http://senindia.co.in/kioskadmin/APIv1.0/allocate_ip_with_kiosk_action.jsp";
    private String response = "";

    @Override
    public void start(Stage stage) throws Exception {

        TextInputDialog dialog = new TextInputDialog("License Key");
        dialog.setTitle("License");
        dialog.setHeaderText("Please Enter Your License Key");
         //dialog.setContentText("Please enter your name:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your name: " + result.get());
            ipAddress = new IPAddress().getIPAddress();
            HashMap<String, String> paramsAlloc = new HashMap<String, String>();
            paramsAlloc.put("allocate_ip", ipAddress);
            paramsAlloc.put("license_no", result.get());
            response = new AppHelper().performPostCall(ALLOCATE_IP_WITH_KIOSK_URL, paramsAlloc);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String msg = jsonObject.getString("msg");
                if (msg.equals("1")) {
                    
                    System.out.println("ALLOCATED");
                    Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.setTitle("Dashboard");
                    stage.show();
                } else if (msg.equals("2")) {
                    System.out.println("ALREADY ALLOCATED");
                    Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.setTitle("Dashboard");
                    stage.show();
                    //System.exit(0);
                } else if (msg.equals("3")) {
                    System.out.println("aaaa");
                    /*System.out.println("ALLOCATED");
                     Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                     Scene scene = new Scene(root);
                     stage.setScene(scene);
                     stage.setMaximized(true);
                     stage.setTitle("Dashboard");
                     stage.show();*/
                    System.exit(0);

                } else {
                    System.out.println("ERROR");
                    
                   // System.exit(1);
                }
                System.out.println(msg);
            } catch (JSONException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
        /* Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setMaximized(true);
         stage.setTitle("Dashboard");
         stage.show();
         Platform.runLater(
         new Runnable() {
         @Override
         public void run() {
         // Update the text node with calculated results
         System.out.println("tses");
         }
         });*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void showDialog() {
        Stage dialogstage = new Stage();
        dialogstage.initModality(Modality.NONE);
        dialogstage.setScene(new Scene(VBoxBuilder.create().children(new Text("It's working")).alignment(Pos.CENTER).build(), 200, 200));
        dialogstage.show();
    }

}
