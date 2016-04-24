    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyalitypointmanagement.ExistingCustomer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loyalitypointmanagement.Dashboard.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author parwez
 */
public class SwipeCardController implements Initializable {
    @FXML
    private VBox rect_panel;
    @FXML
    private Pane pane_existing_cust;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void show_Customer(MouseEvent event) {
        
        try {
            //new LoyalityPointManagement().showDialog();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("/loyalitypointmanagement/ExistingCustomer/existingcustomer.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Existing Customer");
            stage.setMaximized(true);
            stage.setScene(new Scene(root,pane_existing_cust.getScene().getWidth(),pane_existing_cust.getScene().getHeight()));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
