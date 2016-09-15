package com.loyalty.ui;

import com.loyalty.controllers.FXController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Region;
import javafx.stage.*;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * Spring aware loader of javaFx fxml files.
 */
public class FXMLBean {

//    private static final Logger LOG = LoggerFactory.getLogger(FXMLBean.class);

    private FXController controller;

    private FXMLLoader fxmlLoader;

    private Resource fxml;
//    private File fxml;

    private Stage region;

    private String title;

    /**
     * @return GUI component loaded from {@link #fxml} file.
     */
    public Stage getRegion() {
        return region;
    }

    /**
     * @return title of the dialog window.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title for the dialog window.
     * 
     * @param title couple of word describe dialog.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return controller of this dialog.
     */
    public FXController getController() {
        return controller;
    }

    /**
     * Sets controller for dialog.
     * 
     * @param controller process actions of the form.
     * @throws IllegalArgumentException if {@code controller} is null
     */
    @Required
    public void setController(FXController controller) {
        this.controller = controller;
    }

    /**
     * Returns loader of the form.
     * 
     * @return loader which is used to load form fxml file.
     */
    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    /**
     * Sets loader.
     * 
     * @param fxmlLoader which is used to load form fxml file.
     * @throws IllegalArgumentException if {@code fxmlLoader} is null
     */
    @Required
    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * XML File with GUI component definition.
     * 
     * @return file keeps layout of the GUI component.
     */
    public Resource getFxml() {
        return fxml;
    }

    /**
     * Sets the file where layout of the GUI component is kept.
     * 
     * @param fxml xml file with definition of GUI component
     * @throws IllegalArgumentException if {@code fxml} is null
     */
    @Required
    public void setFxml(Resource fxml) {
        this.fxml = fxml;
    }

    /**
     * Loads fxml file with javaFx {@link FXMLLoader}.
     *
     * @throws IOException if file could not be read
     */
    public void loadFxml() throws IOException {
        fxmlLoader.setLocation(fxml.getURL());
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(fxml.getInputStream()));
        region = new Stage();
        region.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> controller.windowShown());
        region.addEventHandler(WindowEvent.WINDOW_HIDDEN,event -> controller.windowHide());
        region.setScene(scene);
        region.setFullScreenExitHint("");
        region.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        region.setAlwaysOnTop(true);
        region.setMaximized(true);
        region.initStyle(StageStyle.UNDECORATED);
        region.initModality(Modality.APPLICATION_MODAL);
    }
}
