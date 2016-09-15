package com.loyalty.controllers;

import com.loyalty.DialogManager;
import com.loyalty.service.KioskService;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by kabachko on 6/28/2016.
 */
public interface FXController<T> extends ApplicationContextAware {

    void setService(KioskService remoteService);

    KioskService getService();

    void setDialogManager(DialogManager manager);

    DialogManager getDialogManager();

    void windowShown();

    void windowHide();

    void setModel(T object);

    T getModel();

    void closeWindow();
}
