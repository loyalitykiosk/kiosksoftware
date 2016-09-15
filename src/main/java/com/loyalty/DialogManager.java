package com.loyalty;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Allows to communicate with user by showing information, warning and
 * confirmation dialogs.
 */
public class DialogManager {

    /**
     * Defines a default width for dialog windows.
     */
    public static final int DEFAULT_DIALOG_WIDTH = 710;

    private static final int DEFAULT_STACKTRACE_HEIGHT = 200;

    private static final int DEFAULT_ERROR_MESSAGE_ROWS = 3;

    private int dialogWidth;

    /**
     * Constructs the manager with width {@link #DEFAULT_DIALOG_WIDTH}.
     */
    public DialogManager() {
        this(DEFAULT_DIALOG_WIDTH);
    }

    /**
     * Constructs the manager with provided preferred with of dialogs.
     * 
     * @param dialogWidth width for dialogs to be shown
     * @throws IllegalArgumentException if {@code dialogWidth} less or equal to
     * zero.
     */
    public DialogManager(int dialogWidth) {
        if (dialogWidth <= 0) {
            throw new IllegalArgumentException(
                    "Dialog width should be positive value.");
        }
        this.dialogWidth = dialogWidth;
    }

    /**
     * Shows confirmation dialog with provided text content on it and YES/NO
     * buttons and wait for user press one of them.
     * <p>
     * Should be called in JavaFX thread
     * </p>
     *
     * @param content text will be shown on dialog
     * @return true if user press YES.
     */
    public boolean showConfirmation(String content) {
        Alert alert = dialog(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.setContentText(content);
        alert.setTitle("Confirmation");
        if (alert.showAndWait().orElse(ButtonType.NO).equals(ButtonType.YES)) {
            return true;
        }
        return false;
    }

    /**
     * Shows information dialog with provided information message on it and wait
     * for user press OK.
     * <p>
     * Should be called in JavaFX thread
     * </p>
     * 
     * @param content text will be shown on dialog
     */
    public void showInformation(String content) {
        Alert alert = dialog(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.setTitle("Information");
        alert.showAndWait();
    }

    /**
     * Show error dialog with provided error message and expandable stacktrace
     * and wait for user press any of button.
     * <p>
     * Should be called in JavaFX thread
     * </p>
     *
     * @param ex error to be present
     * @param header text in header panel
     * @param buttons buttons to show in the bottom of dialog
     * @return pressed button or {@code null} if button was not pressed
     * @throws IllegalArgumentException if none {@code buttons} provided
     */
    public ButtonType showError(Throwable ex, String header, ButtonType... buttons) {
        Alert alert = dialog(Alert.AlertType.ERROR);
        alert.getDialogPane().setMaxHeight(Double.MAX_VALUE);
        alert.getDialogPane()
                .expandedProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            double height =
                                    alert.getDialogPane().getScene().getWindow()
                                            .getHeight();
                            double width =
                                    alert.getDialogPane().getScene().getWindow()
                                            .getWidth();
                            ((Stage) alert.getDialogPane().getScene().getWindow())
                                    .setMinWidth(width);
                            ((Stage) alert.getDialogPane().getScene().getWindow())
                                    .setMinHeight(newValue
                                            ? height
                                            : 0.0);
                            alert.getDialogPane().getScene().getWindow().sizeToScene();
                        });
        alert.setTitle("Error");
        TextArea errorText = new TextArea(header
                + ": " + ExceptionUtils.getRootCauseMessage(ex));
        errorText.setPrefRowCount(DEFAULT_ERROR_MESSAGE_ROWS);
        errorText.setWrapText(true);
        errorText.setEditable(false);
        errorText.getStyleClass().add("text-area-transparent");
        GridPane content = gridPane(errorText);
        content.add(errorText, 0, 0);
        alert.getDialogPane().setContent(content);
        String exceptionText = ExceptionUtils.getFullStackTrace(ex);
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(false);
        GridPane expContent = gridPane(textArea);
        expContent.setMinHeight(DEFAULT_STACKTRACE_HEIGHT);
        expContent.add(new Label("The exception stacktrace is:"), 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getButtonTypes().setAll(buttons);
        return alert.showAndWait().orElse(null);
    }

    private GridPane gridPane(Node node) {
        GridPane gridPane = new GridPane();
        GridPane.setVgrow(node, Priority.ALWAYS);
        GridPane.setHgrow(node, Priority.ALWAYS);
        return gridPane;
    }

    private Alert dialog(Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(null);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
                .add(new Image(getClass().getClassLoader().getResourceAsStream(
                        "icon.png")));
        alert.getDialogPane().getScene().getStylesheets().add("dialog-styles.css");
        ((Stage) alert.getDialogPane().getScene().getWindow())
                .setMinWidth(dialogWidth);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getClassLoader().getResource("dialog/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-font-size: 24px;"
                + "-fx-background-color: indianred;");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family: \"Andalus\";"));

        alert.getDialogPane().setPrefWidth(dialogWidth);
        alert.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.setResizable(false);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        return alert;
    }
}
