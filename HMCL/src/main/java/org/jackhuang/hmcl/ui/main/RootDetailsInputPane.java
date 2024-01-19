package org.jackhuang.hmcl.ui.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.jackhuang.hmcl.auth.authlibinjector.AuthlibInjectorServer;
import org.jackhuang.hmcl.task.Schedulers;
import org.jackhuang.hmcl.task.Task;
import org.jackhuang.hmcl.ui.animation.ContainerAnimations;
import org.jackhuang.hmcl.ui.animation.TransitionPane;
import org.jackhuang.hmcl.ui.construct.DialogAware;
import org.jackhuang.hmcl.ui.construct.DialogCloseEvent;
import org.jackhuang.hmcl.ui.construct.SpinnerPane;
import org.jackhuang.hmcl.util.Lang;
import org.jackhuang.hmcl.util.io.NetworkUtils;
import top.dooper.top.auth.AuthManagementServer;
import top.dooper.top.exception.ServerErrorException;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import static org.jackhuang.hmcl.setting.ConfigHolder.config;
import static org.jackhuang.hmcl.ui.FXUtils.onEscPressed;
import static org.jackhuang.hmcl.util.Logging.LOG;
import static org.jackhuang.hmcl.util.i18n.I18n.i18n;

public final class RootDetailsInputPane extends TransitionPane implements DialogAware {
    private final Label lblServerUrl;
    private final Label lblServerName;
    private final Label lblCreationWarning;
    private final Label lblServerWarning;
    private final JFXTextField txtServerUrl;
    private final JFXDialogLayout addServerPane;
    private final JFXDialogLayout confirmServerPane;
    private final SpinnerPane nextPane;
    private final JFXButton btnAddNext;

    private AuthManagementServer serverBeingAdded;
    public RootDetailsInputPane() {

        addServerPane = new JFXDialogLayout();
        addServerPane.setHeading(new Label(i18n("root.main.set")));
        {
            txtServerUrl = new JFXTextField();
            txtServerUrl.setPromptText(i18n("root.main.api"));
            txtServerUrl.setOnAction(e -> onAddNext());

            lblCreationWarning = new Label();
            lblCreationWarning.setWrapText(true);
            HBox actions = new HBox();
            {
//                JFXButton cancel = new JFXButton(i18n("button.cancel"));
//                cancel.getStyleClass().add("dialog-accept");
//                cancel.setOnAction(e -> onAddCancel());

                nextPane = new SpinnerPane();
                nextPane.getStyleClass().add("small-spinner-pane");
                btnAddNext = new JFXButton(i18n("wizard.next"));
                btnAddNext.getStyleClass().add("dialog-accept");
                btnAddNext.setOnAction(e -> onAddNext());
                nextPane.setContent(btnAddNext);

                actions.getChildren().setAll(
//                        cancel,
                        nextPane);
            }

            addServerPane.setBody(txtServerUrl);
            addServerPane.setActions(lblCreationWarning, actions);
        }

        confirmServerPane = new JFXDialogLayout();
        confirmServerPane.setHeading(new Label(i18n("root.main.set")));
        {
            GridPane body = new GridPane();
            body.setStyle("-fx-padding: 15 0 0 0;");
            body.setVgap(15);
            body.setHgap(15);
            {
                body.getColumnConstraints().setAll(
                        Lang.apply(new ColumnConstraints(), c -> c.setMaxWidth(100)),
                        new ColumnConstraints()
                );

                lblServerUrl = new Label();
                GridPane.setColumnIndex(lblServerUrl, 1);
                GridPane.setRowIndex(lblServerUrl, 0);

                lblServerName = new Label();
                GridPane.setColumnIndex(lblServerName, 1);
                GridPane.setRowIndex(lblServerName, 1);

                lblServerWarning = new Label(i18n("account.injector.http"));
                lblServerWarning.setStyle("-fx-text-fill: red;");
                GridPane.setColumnIndex(lblServerWarning, 0);
                GridPane.setRowIndex(lblServerWarning, 2);
                GridPane.setColumnSpan(lblServerWarning, 2);

                body.getChildren().setAll(
                        Lang.apply(new Label(i18n("account.injector.server_url")), l -> {
                            GridPane.setColumnIndex(l, 0);
                            GridPane.setRowIndex(l, 0);
                        }),
                        Lang.apply(new Label(i18n("account.injector.server_name")), l -> {
                            GridPane.setColumnIndex(l, 0);
                            GridPane.setRowIndex(l, 1);
                        }),
                        lblServerUrl, lblServerName, lblServerWarning
                );
            }

            JFXButton prevButton = new JFXButton(i18n("wizard.prev"));
            prevButton.getStyleClass().add("dialog-cancel");
            prevButton.setOnAction(e -> onAddPrev());

//            JFXButton cancelButton = new JFXButton(i18n("button.cancel"));
//            cancelButton.getStyleClass().add("dialog-cancel");
//            cancelButton.setOnAction(e -> onAddCancel());

            JFXButton finishButton = new JFXButton(i18n("wizard.finish"));
            finishButton.getStyleClass().add("dialog-accept");
            finishButton.setOnAction(e -> onAddFinish());

            confirmServerPane.setBody(body);
            confirmServerPane.setActions(
                    prevButton,
//                    cancelButton,
                    finishButton);
        }

        this.setContent(addServerPane, ContainerAnimations.NONE.getAnimationProducer());

        lblCreationWarning.maxWidthProperty().bind(((FlowPane) lblCreationWarning.getParent()).widthProperty());
        btnAddNext.disableProperty().bind(txtServerUrl.textProperty().isEmpty());
        nextPane.hideSpinner();

        onEscPressed(this, this::onAddCancel);
    }

    @Override
    public void onDialogShown() {
        txtServerUrl.requestFocus();
    }

    private String resolveFetchExceptionMessage(Throwable exception) {
        if (exception instanceof SSLException) {
            return i18n("account.failed.ssl");
        } else if (exception instanceof IOException) {
            return i18n("root.main.failed");
        } else if (exception instanceof JsonSyntaxException) {
            return i18n("root.main.errorServer");
        } else if (exception instanceof ServerErrorException) {
            return i18n("root.main.errorServer");
        } else {
            return exception.getClass().getName() + ": " + exception.getLocalizedMessage();
        }
    }

    private void onAddCancel() {
        fireEvent(new DialogCloseEvent());
    }

    private void onAddNext() {
        if (btnAddNext.isDisabled())
            return;

        lblCreationWarning.setText("");

        String url = txtServerUrl.getText();

        nextPane.showSpinner();
        addServerPane.setDisable(true);

        Task.runAsync(() -> {
            serverBeingAdded = AuthManagementServer.locateServer(url);
        }).whenComplete(Schedulers.javafx(), exception -> {
            addServerPane.setDisable(false);
            nextPane.hideSpinner();

            if (exception == null) {
                lblServerName.setText(serverBeingAdded.getServerName());
                lblServerUrl.setText(serverBeingAdded.getUrl());

                lblServerWarning.setVisible("http".equals(NetworkUtils.toURL(serverBeingAdded.getUrl()).getProtocol()));

                this.setContent(confirmServerPane, ContainerAnimations.SWIPE_LEFT.getAnimationProducer());
            } else {
                LOG.log(Level.WARNING, "Failed to resolve management server: " + url, exception);
                lblCreationWarning.setText(resolveFetchExceptionMessage(exception));
            }
        }).start();

    }

    private void onAddPrev() {
        this.setContent(addServerPane, ContainerAnimations.SWIPE_RIGHT.getAnimationProducer());
    }

    private void onAddFinish() {
        String programDirectory = System.getProperty("user.dir");
        String hmclFolderPath = programDirectory + File.separator + "HMCL";

        File hmclFolder = new File(hmclFolderPath);
        if (!hmclFolder.exists()) {
            if (hmclFolder.mkdir()) {
                System.out.println("HMCL folder created.");
            } else {
                System.err.println("Failed to create HMCL folder.");
                return;
            }
        }

        String hotConfigFilePath = hmclFolderPath + File.separator + "HotConfig.json";

        File hotConfigFile = new File(hotConfigFilePath);
        if (!hotConfigFile.exists()) {
            try {
                if (hotConfigFile.createNewFile()) {
                    System.out.println("HotConfig.json file created.");
                } else {
                    System.err.println("Failed to create HotConfig.json file.");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(hotConfigFilePath)) {
            gson.toJson(serverBeingAdded, writer);
            System.out.println("HotConfig.json file updated with new content.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        fireEvent(new DialogCloseEvent());
    }

}
