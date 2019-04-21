package docManager.controller;

import docManager.Main;
import docManager.model.DataAdditionally;
import docManager.model.MainData;
import docManager.util.ArrayUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Arrays;

public class CalculatorController {
    @FXML
    private TableView<DataAdditionally> tableView;
    @FXML
    private TableColumn<DataAdditionally, Number> priceColumn;
    @FXML
    private TableColumn<DataAdditionally, String> descriptionColumn;
    @FXML
    private Label priceLabel;
    @FXML
    private Label priceOnlyLabel;
    @FXML
    private TextField priceField;
    @FXML
    private TextField descriptionField;

    private Stage editStage;
    private MainData mainData;
    private DataAdditionally dataAdditionally;
    private ArrayUtil arrayUtil;
    private boolean okClicked = false;

    // Ссылка на главное приложение.
    private Main main;

    @FXML
    private void initialize() {
       priceColumn.setCellValueFactory(cellData -> cellData.getValue().getQwe());

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
    public void setMainData(MainData mainData, ArrayUtil arrayUtil, DataAdditionally dataAdditionally) {
        this.mainData = mainData;
        this.arrayUtil = arrayUtil;
        this.dataAdditionally = dataAdditionally;

        priceLabel.setText(Integer.toString(mainData.getPrice()));
        priceOnlyLabel.setText(Integer.toString((mainData.getPrice()-arrayUtil.sumArray(mainData.getSumСosts()))));
    }

        public void setMain(Main main) {
        this.main = main;

//         Добавление в таблицу данных из наблюдаемого списка
        tableView.setItems(main.getContractDataAdditionally());
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
     * Вызывается, когда пользователь кликнул по кнопке Ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            mainData.setSumСosts(Integer.parseInt(priceField.getText()));
            dataAdditionally.setQwe(Integer.parseInt(priceField.getText()));

            priceField.clear();


            System.out.println(Arrays.toString(mainData.getSumСosts()));
            System.out.println(dataAdditionally.getQwe());

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

    private boolean isInputValid() {
        String errorMessage = "";

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

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editStage);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пожалуйста, введите данные корректно!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
