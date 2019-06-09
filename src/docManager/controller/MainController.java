package docManager.controller;

import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import docManager.Main;
import docManager.model.Attachment;
import docManager.model.MainData;
import docManager.util.DateUtil;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.time.LocalDate;

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
    private TableView<Attachment> linkTable1;
    @FXML
    private TableColumn<Attachment, String> contractColumn1;
    @FXML
    private TableColumn<Attachment, String> linkColumn1;

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
    private void initialize() {

        // Инициализация таблицы.
        numberContractColumn.setCellValueFactory(cellData -> cellData.getValue().numberContractProperty());
        dateExecutionContractColumn.setCellValueFactory(cellData -> cellData.getValue().dateExecutionContractProperty());
        timeContractColumn.setCellValueFactory(cellData -> cellData.getValue().timeContractProperty());

        contractColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContract().getNumberContract()));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));
        linkTable.setRowFactory(tv -> {
            TableRow<Attachment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    linkTable.getSelectionModel().getSelectedItem().openFile();
                }
            });
            return row;
        });

        contractColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContract().getNumberContract()));
        linkColumn1.setCellValueFactory(new PropertyValueFactory<>("link"));

        // Очистка дополнительной информации об адресате.
        showContractDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        contractTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showContractDetails(newValue));


        //красим ячейки
//        timeContractColumn.setCellFactory(column -> {
//            return new TableCell<MainData, LocalDate>() {
//                @Override
//                protected void updateItem(LocalDate item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    if (item == null || empty) { //If the cell is empty
//                        setText(null);
//                        setStyle("");
//                    } else { //If the cell is not empty
//
//                        setText(item); //Put the String data in the cell
//
//                        //We get here all the info of the Person of this row
//                        MainData auxPerson = getTableView().getItems().get(getIndex());
//
//                        // Style all persons wich name is "Edgard"
//                        if (auxPerson.getTimeContract().equals("Edgard")) {
//                            setTextFill(Color.RED); //The text in red
//                            setStyle("-fx-background-color: yellow"); //The background of the cell in yellow
//                        } else {
//                            //Here I see if the row of this cell is selected or not
//                            if(getTableView().getSelectionModel().getSelectedItems().contains(auxPerson))
//                                setTextFill(Color.WHITE);
//                            else
//                                setTextFill(Color.BLACK);
//                        }
//                    }
//                }
//            };
//        });
    }

    public TableView<Attachment> getLinkTable() {
        return linkTable;
    }

    public TableColumn<Attachment, String> getLinkColumn() {
        return linkColumn;
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


        contractTable.getSelectionModel().selectedItemProperty().addListener( (ov,o,n) -> {
            if(n!= null){
                linkTable.setItems(n.getNameLink());
                linkTable1.setItems(n.getCosts());
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
    private void handleNewData() {
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
    private void handleEditData() {
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showDataEditDialog(selectedData);
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

//    @FXML
//    private void handleCalculator() {   //разобраться как работает
//        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
//        ArrayUtil arrayUtil = new ArrayUtil();
//        if (selectedData != null) {
//            boolean okClicked = main.showCalculatorDialog(selectedData, arrayUtil); //?
//            if (okClicked) {
//                showContractDetails(selectedData);
//            }
//        } else {
//            // Ничего не выбрано.
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.initOwner(main.getMenuBar());
//            alert.setTitle("Ошибка");
//            alert.setHeaderText("Договор не выбран.");
//            alert.setContentText("Пожалуйста, выберите необходимый документ для правки.");
//
//            alert.showAndWait();
//        }
//    }

    @FXML
    private void addLink() {   //разобраться как работает
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showAddLink(selectedData); //?
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

    @FXML
    private void addCost() {   //разобраться как работает
        MainData selectedData = contractTable.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            boolean okClicked = main.showAddCost(selectedData); //?
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