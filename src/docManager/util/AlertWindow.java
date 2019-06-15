package docManager.util;

import docManager.model.Attachment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

public class AlertWindow {

    /**
     * Утилита для удаление строки из таблицы
     * @param setHeaderText
     * @param tableView
     */
    public static void showAlertConfirmation(String setHeaderText, TableView<Attachment> tableView){
        Attachment selectedItem = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " ", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Удаление");
        alert.setHeaderText(setHeaderText);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            tableView.getItems().remove(selectedItem);
        }
    }

    /**
     * Показать окно "Внимание"
     */
    public static void showAlertWarning(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Договор не выбран.");
        alert.setContentText("Пожалуйста, выберите необходимый документ.");
        alert.showAndWait();
    }
}
