package docManager.controller;

import docManager.Main;
//import docManager.model.DataAdditionally;
import docManager.model.MainData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddLinkController {
    @FXML
    private TextField linkDocField;
    @FXML
    private TextField nameDocField;

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
            mainData.setNameLink(linkDocField.getText(), nameDocField.getText());
            editStage.close();
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
     * Открываем проводник, что бы выбрать документ, которого
     * собираемся добавить ссылку
     */
    @FXML
    private void addFile(){
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "All files" , "*.*"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(main.getMenuBar());

        if (file != null) {
            linkDocField.setText(file.toString());
        }
    }

    /**
     * Проверяем правильность ввода
     * @return
     */
    //TODO
    private boolean isInputValid() {

        return true;
    }
}
