package docManager.controller;

import docManager.Main;
import docManager.model.MainData;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCostController {
    @FXML
    private TextField summField;
    @FXML
    private TextArea descriptionField;

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
            mainData.setCosts(summField.getText(), descriptionField.getText());

            summField.clear();
            descriptionField.clear(); //

            editStage.close();
            okClicked = true;
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
