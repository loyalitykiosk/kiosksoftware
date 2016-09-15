/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loyalty;

import com.loyalty.service.HttpClient;
import com.loyalty.ui.FXMLBean;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.http.HttpException;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 *
 * @author partha
 */
public class LoyalityPointManagement extends Application {

    ConfigurableApplicationContext context;

        @Override
    public void init() throws Exception {
        super.init();
        context = new ClassPathXmlApplicationContext("application.xml");
    }

    @Override
    public void stop() throws Exception {
        if (context != null && context.isActive()){
            context.close();
        }
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws Exception {
        login(stage);
        FXMLBean dashboard = context.getBean("dashboard", FXMLBean.class);
        dashboard.getRegion().show();
    }

    private void login(Stage stage) {
        HttpClient client = context.getBean("httpClient", HttpClient.class);
        try {
            client.login();
        } catch (IOException|HttpException e) {
            dialogManager().showInformation("No Connection with server");
            Platform.exit();
            System.exit(-1);
        }
    }

    private DialogManager dialogManager() {
        try {
            return context.getBean(DialogManager.class);
        } catch (BeansException e) {
            return new DialogManager();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
