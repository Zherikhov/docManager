package docManager.controller;

import docManager.util.AlertWindow;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import docManager.Main;
import docManager.model.Attachment;
import docManager.model.MainData;
import docManager.util.DateUtil;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class MainController {
    @FXML
    private TableView<MainData> contractTable;
    @FXML
    private TableColumn<MainData, String> numberContractColumn;
    @FXML
    private TableColumn<MainData, LocalDate> dateExecutionContractColumn;
    @FXML
    private TableColumn<MainData, LocalDate> timeContractColumn;
    @FXML
    private TableView<Attachment> linkTable;
    @FXML
    private TableColumn<Attachment, String> contractColumn;
    @FXML
    private TableColumn<Attachment, String> linkColumn;

    @FXML
    private TableView<Attachment> costsTable;
    @FXML
    private TableColumn<Attachment, String> descriptionColumn;
    @FXML
    private TableColumn<Attachment, String> summColumn;

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
    @FXML
    private Label priceOnlyLabel;


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
    private void initialize() throws InterruptedException, ParseException {

        // Инициализация таблицы.
        numberContractColumn.setCellValueFactory(cellData -> cellData.getValue().numberContractProperty());
        dateExecutionContractColumn.setCellValueFactory(cellData -> cellData.getValue().dateExecutionContractProperty());
        timeContractColumn.setCellValueFactory(cellData -> cellData.getValue().timeContractProperty());

        contractColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
        linkTable.setRowFactory(tv -> {
            TableRow<Attachment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    String selectedItem = linkTable.getSelectionModel().getSelectedItem().toString();
                    linkTable.getSelectionModel().getSelectedItem().openFile(selectedItem);
                }
            });
            return row;
        });

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        summColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        linkTable.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                int selectedIndex = linkTable.getSelectionModel().getSelectedIndex();
                AlertWindow.showAlertConfirmationRemove("Вы действительно хотите удалить ссылку?", linkTable, selectedIndex);
            }
        });

        costsTable.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                int selectedIndex = costsTable.getSelectionModel().getSelectedIndex();
                AlertWindow.showAlertConfirmationRemove("Вы действительно хотите удалить списание?", costsTable, selectedIndex);
            }
        });

        // Очистка дополнительной информации об адресате.
        showContractDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        contractTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showContractDetails(newValue));


        // Красим ячейки
        timeContractColumn.setCellFactory(column -> {
            return new TableCell<MainData, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) { //Если ячейка пустая
                        setText(null);
                        setStyle("");
                    } else { //Если ячейка не пустая

                        setTextFill(Color.BLACK);
                        setStyle(null);
                        setText(item.toString()); //Помещаем данные в ячейку

                        // Мы получаем здесь всю информацию о этой строки
                        MainData auxPerson = getTableView().getItems().get(getIndex());
//                        System.out.println(auxPerson.getCosts().toString());

                        // Меняем стиль если...
                        String qwe = CalendarUtil.getResult(auxPerson.getTimeContract(), auxPerson.getCurrentTime());
                        if (qwe.equals("P10D") || qwe.equals("P9D") || qwe.equals("P8D") || qwe.equals("P7D")
                                || qwe.equals("P6D") || qwe.equals("P5D") || qwe.equals("P4D") || qwe.equals("P3D")
                                || qwe.equals("P2D") || qwe.equals("P1D") || qwe.equals("P0D")) {
                            setTextFill(Color.RED);
                            setStyle("-fx-border-color: red");
                        }
                    }
                }
            };
        });
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


        contractTable.getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n != null) {
                linkTable.setItems(n.getNameLink());
                costsTable.setItems(n.getCosts());
            }
        });
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
            priceOnlyLabel.setText(Integer.toString((mainData.getPrice() - mainData.getCostsInteger())));

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
            priceOnlyLabel.setText("-");
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке удаления.
     */
    @FXML
    private void handleDeleteData() {
        int selectedIndex = contractTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            AlertWindow.showAlertConfirmationRemove("Вы действительно хотите удалить договор?",
                    contractTable, selectedIndex);
        } else {
            // Ничего не выбрано.
            AlertWindow.showAlertWarning("Договор не выбран!", null);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопке Создать...
     * Открывает диалоговое окно с дополнительной информацией нового адресата.
     */
    @FXML
    private void handleNewData() {
        MainData tempData = new MainData();
        boolean okClicked = main.showDataEditDialog(tempData);
        if (okClicked) {
            main.getContractData().add(tempData);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Изменить.
     * Открывает диалоговое окно для изменения выбранного договора.
     */
    @FXML
    private void handleEditData() {
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showDataEditDialog(selectedData);
            if (okClicked) {
                showContractDetails(selectedData);
            }

        } else {
            // Ничего не выбрано.
            AlertWindow.showAlertWarning("Договор не выбран!", null);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Добавить.
     * Открывает диалоговое окно для добавления ссылки на документ.
     */
    @FXML
    private void addLink() {
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showAddLink(selectedData);
            if (okClicked) {
                showContractDetails(selectedData);
            }
        } else {
            // Ничего не выбрано.
            AlertWindow.showAlertWarning("Договор не выбран!", null);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Расход.
     * Открывает диалоговое окно для добавления статьи расходов.
     */
    @FXML
    private void addCost() {
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showAddCost(selectedData);
            if (okClicked) {
                showContractDetails(selectedData);
            }
        } else {
            // Ничего не выбрано.
            AlertWindow.showAlertWarning("Договор не выбран!", null);
        }
    }
}

/**
 * Утилита для определения остатка дней между сегодняшней
 * датой и датой завершения договора
 */
class CalendarUtil {
    public static String getResult(LocalDate firstDate, LocalDate secondDate) {
        Period period = Period.between(secondDate, firstDate);
        String s = period.toString();
        System.out.println(s);
        return s;
    }
}