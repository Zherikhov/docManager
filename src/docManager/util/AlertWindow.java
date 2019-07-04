package docManager.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

/**
 * Утилитный класс, позволяющий вызвать нужное информационное окно с
 * предупреждающим сообщением
 */
public class AlertWindow {

    public static void showAlertConfirmationRemove(String setHeaderText, TableView tableView, int selectedIndex) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " ", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Удаление");
        alert.setHeaderText(setHeaderText);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            tableView.getItems().remove(selectedIndex);
        }
    }

    public static void showAlertConfirmationExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " ", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Выход");
        alert.setHeaderText("Все несохраненные данные будут потеряны.");
        alert.setContentText("Вы действительно хотите выйти?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

     public static void showAlertWarning(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showAlertError(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
