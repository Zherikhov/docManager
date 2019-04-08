package docManager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import docManager.model.MainData;
import docManager.util.DateUtil;

/**
 * Окно для изменения информации документов.
 */
public class ContractEditController {

    @FXML
    private TextField numberContractField;
    @FXML
    private TextField dateContractField;
    @FXML
    private TextField counterpartyField;
    @FXML
    private TextField subjectContractField;
    @FXML
    private TextField dateExecutionContractField;
    @FXML
    private TextField timeContractField;
    @FXML
    private TextField priceField;


    private Stage editStage;
    private MainData mainData;
    private boolean okClicked = false;


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

        numberContractField.setText(mainData.getNumberContract());

        dateContractField.setText(DateUtil.format(mainData.getDateContract()));
        dateContractField.setPromptText("дд.мм.гггг");

        counterpartyField.setText(mainData.getCounterparty());
        subjectContractField.setText(mainData.getSubjectContract());

        dateExecutionContractField.setText(DateUtil.format(mainData.getDateExecutionContract()));
        dateExecutionContractField.setPromptText("дд.мм.гггг");

        timeContractField.setText(DateUtil.format(mainData.getTimeContract()));
        timeContractField.setPromptText("дд.мм.гггг");

        priceField.setText(Integer.toString(mainData.getPrice()));
    }

    /**
     * Returns true, если пользователь кликнул OK, в другом случае false.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            mainData.setNumberContract(numberContractField.getText());
            mainData.setDateContract(DateUtil.parse(dateContractField.getText()));
            mainData.setCounterparty(counterpartyField.getText());
            mainData.setSubjectContract(subjectContractField.getText());
            mainData.setDateExecutionContract(DateUtil.parse(dateExecutionContractField.getText()));
            mainData.setTimeContract(DateUtil.parse(timeContractField.getText()));
            mainData.setPrice(Integer.parseInt(priceField.getText()));

            okClicked = true;
            editStage.close();
        }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleCancel() {
        editStage.close();
    }

    /**
     * Проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (numberContractField.getText() == null || numberContractField.getText().length() == 0) {
            errorMessage += "Поле не заполнено - № договора!\n";
        }
        if (counterpartyField.getText() == null || counterpartyField.getText().length() == 0) {
            errorMessage += "Поле не заполнено - Контрагент!\n";
        }
        if (subjectContractField.getText() == null || subjectContractField.getText().length() == 0) {
            errorMessage += "Поле не заполнено - Предмет договора!\n";
        }

        if (dateExecutionContractField.getText() == null || dateExecutionContractField.getText().length() == 0) {
            errorMessage += "Поле не заполнено - Дата заключения договора!\n";
        } else {
            if (!DateUtil.validDate(dateExecutionContractField.getText())) {
                errorMessage += "Неверный формат ввода. Используйте формат - дд.мм.гггг!\n";
            }
        }

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "Поле не заполнено - Цена договора!\n";
        } else {
            // пытаемся преобразовать код в int.
            try {
                Integer.parseInt(priceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Неверный формат ввода - Цена договора!\n";
            }
        }

        if (timeContractField.getText() == null || timeContractField.getText().length() == 0) {
            errorMessage += "Поле не заполнено - Срок действия договора!\n";
        }

        if (dateContractField.getText() == null || dateContractField.getText().length() == 0) {
            errorMessage += "Поле не заполнено - Дата заключения договора!\n";
        } else {
            if (!DateUtil.validDate(dateContractField.getText())) {
                errorMessage += "Неверный формат ввода. Используйте формат - дд.мм.гггг!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(editStage);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пожалуйста, введите данные корректно!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
