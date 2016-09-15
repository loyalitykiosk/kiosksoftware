package com.loyalty.controllers;

import com.loyalty.DialogManager;
import com.loyalty.service.KioskService;
import javafx.application.Platform;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by kabachko on 6/26/2016.
 */
public abstract class AbstractController<T extends Object> implements FXController<T> {

    private ScheduledFuture future;
    private DialogManager dialogManager;

    private KioskService service;

    private ApplicationContext context;

    private ScheduledExecutorService scheduledExecutorService;

    private T model;

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public void setDialogManager(DialogManager dialogManager) {
        this.dialogManager = dialogManager;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public void setService(KioskService remoteService) {
        this.service = remoteService;
    }

    @Override
    public KioskService getService() {
        return service;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public ApplicationContext getContext() {
        return context;
    }

    protected ScheduledFuture<?> schedule(Runnable r) {
        future = getScheduledExecutorService().schedule(() ->
             Platform.runLater(r), 30, TimeUnit.SECONDS);
        return future;
    }

    protected void stopTimer(){
        if (future != null && !future.isDone()){
            future.cancel(false);
        }
    }
}
