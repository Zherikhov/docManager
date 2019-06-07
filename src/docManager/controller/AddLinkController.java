package docManager.controller;

import docManager.Main;
//import docManager.model.DataAdditionally;
import docManager.model.MainData;
import docManager.util.ArrayUtil;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AddLinkController {
    @FXML
    private TextField nameDocField;
    @FXML
    private Label linkLabel;

    private Stage editStage;
    private MainData mainData;
    private boolean okClicked = false;

    // Ссылка на главное приложение.
    private Main main;

    @FXML
    private void initialize() {

    }

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param editStage
     */
    public void setEditStage(Stage editStage) {
        this.editStage = editStage;
    }

    /**
     * Задаёт документ, информацию о которой будем менять.
     *
     * @param mainData
     */
    public void setMainData(MainData mainData) {
        this.mainData = mainData;
//        this.dataAdditionally = dataAdditionally;

    }

    public void setMain(Main main) {
        this.main = main;

    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            mainData.setNameLink(nameDocField.getText());
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleClose() {
        editStage.close();
    }

    /**
     * Проверяем правильность ввода
     * @return
     */
    private boolean isInputValid() {

        return true;
    }
}
