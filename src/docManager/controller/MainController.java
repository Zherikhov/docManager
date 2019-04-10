package docManager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import docManager.Main;
import docManager.model.MainData;
import docManager.util.DateUtil;

import java.time.LocalDate;

public class MainController {
    @FXML
    private TableView<MainData> contractTable;
    @FXML
    private TableView<MainData> contractTable2;
    @FXML
    private TableColumn<MainData, String> numberContractColumn;
    @FXML
    private TableColumn<MainData, LocalDate> dateExecutionContractColumn;
    @FXML
    private TableColumn<MainData, LocalDate> timeContractColumn;

    @FXML
    private Label numberContractLabel;
    @FXML
    private Label dateContractLabel;
    @FXML
    private Label counterpartyLabel;
    @FXML
    private Label subjectContractLabel;
    @FXML
    private Label dateExecutionContractLabel;
    @FXML
    private Label timeContractLabel;
    @FXML
    private Label priceLabel;


    // Ссылка на главное приложение.
    private Main main;

    /**
     * Конструктор.
     */
    public MainController() {
    }

    /**
     * Инициализация класса-контроллера.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы.
        numberContractColumn.setCellValueFactory(cellData -> cellData.getValue().numberContractProperty());
        dateExecutionContractColumn.setCellValueFactory(cellData -> cellData.getValue().dateExecutionContractProperty());
        timeContractColumn.setCellValueFactory(cellData -> cellData.getValue().timeContractProperty());

        // Очистка дополнительной информации об адресате.
        showContractDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        contractTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showContractDetails(newValue));
    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param main
     */
    public void setMain(Main main) {
        this.main = main;

        // Добавление в таблицу данных из наблюдаемого списка
        contractTable.setItems(main.getContractData());
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об адресате.
     * Если указанный адресат = null, то все текстовые поля очищаются.
     *
     * @param mainData — адресат типа Person или null
     */
    private void showContractDetails(MainData mainData) {
        if (mainData != null) {
            // Заполняем метки информацией из объекта MainData.
            numberContractLabel.setText(mainData.getNumberContract());
            counterpartyLabel.setText(mainData.getCounterparty());
            subjectContractLabel.setText(mainData.getSubjectContract());
            priceLabel.setText(Integer.toString(mainData.getPrice()));

            dateContractLabel.setText(DateUtil.format(mainData.getDateContract()));
            dateExecutionContractLabel.setText(DateUtil.format(mainData.getDateExecutionContract()));
            timeContractLabel.setText(DateUtil.format(mainData.getTimeContract()));


        } else {
            // Если Data = null, то убираем весь текст.
            numberContractLabel.setText("-");
            dateContractLabel.setText("-");
            counterpartyLabel.setText("-");
            subjectContractLabel.setText("-");
            dateExecutionContractLabel.setText("-");
            timeContractLabel.setText("-");
            priceLabel.setText("-");
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке удаления.
     */
    @FXML
    private void handleDeleteContract() {
        int selectedIndex = contractTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            contractTable.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getMenuBar());
            alert.setTitle("Ошибка");
            alert.setHeaderText("Документ не выбран.");
            alert.setContentText("Пожалуйста выделите документ, который хотите удалить.");

            alert.showAndWait();
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке Создать...
     * Открывает диалоговое окно с дополнительной информацией нового адресата.
     */
    @FXML
    private void handleNewPerson() {
        MainData tempData = new MainData();
        boolean okClicked = main.showDataEditDialog(tempData);
        if (okClicked) {
            main.getContractData().add(tempData);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Изменить...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditPerson() {
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showDataEditDialog(selectedData);
            if (okClicked) {
//                showContractDetails(selectedData);
                System.out.println("OK");
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getMenuBar());
            alert.setTitle("Ошибка");
            alert.setHeaderText("Договор не выбран.");
            alert.setContentText("Пожалуйста, выберите необходимый документ для правки.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleCalculator() {
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showCalculatorDialog(selectedData);
            if (okClicked) {
                showContractDetails(selectedData);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getMenuBar());
            alert.setTitle("Ошибка");
            alert.setHeaderText("Договор не выбран.");
            alert.setContentText("Пожалуйста, выберите необходимый документ для правки.");

            alert.showAndWait();
        }
    }
}
