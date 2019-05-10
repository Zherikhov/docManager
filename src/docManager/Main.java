package docManager;

import docManager.controller.CalculatorController;
//import docManager.model.DataAdditionally;
import docManager.util.ArrayUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import docManager.controller.ContractEditController;
import docManager.controller.MainController;
import docManager.controller.MenuBarController;
import docManager.model.DataListWrapper;
import docManager.model.MainData;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Main extends Application {

    private Stage menuBar;
    private BorderPane mainWindow;

    /**
     * Данные, в виде наблюдаемого списка документов.
     */
    private ObservableList<MainData> contractData = FXCollections.observableArrayList();

    /**
     * Возвращает данные в виде наблюдаемого списка документов.
     * @return
     */
    public ObservableList<MainData> getContractData() {
        return contractData;
    }

    /**
     * Конструктор
     */
    public Main() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.menuBar = primaryStage;
        this.menuBar.setTitle("");

        initMenuBar();
        initMainWindow();
    }

    /**
     * Инициализируем menuBar.
     */
    public void initMenuBar() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/menuBarOverview.fxml"));
            mainWindow = loader.load();

            Scene scene = new Scene(mainWindow);
            menuBar.setScene(scene);

            // Даём контроллеру доступ к главному прилодению.
            MenuBarController controller = loader.getController();
            controller.setMain(this);

            menuBar.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загружает в корневой макет mainWindow.
     */
    public void initMainWindow() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/mainOverview.fxml"));
            AnchorPane personOverview = loader.load();

            mainWindow.setCenter(personOverview);

            // Даём контроллеру доступ к главному приложению.
            MainController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пытается загрузить последний открытый файл с адресатами.
        File file = getDataFilePath();
        if (file != null) {
            loadDataFromFile(file);
        }
    }

    /**
     * Возвращаем главную сцену.
     * @return
     */
    public Stage getMenuBar() {
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Открывает диалоговое окно для изменения деталей указанного адресата.
     * Если пользователь кликнул OK, то изменения сохраняются в предоставленном
     * объекте адресата и возвращается значение true.
     *
     * @param mainData - объект адресата, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showDataEditDialog(MainData mainData) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/сontractEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Правка договора");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(menuBar);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            ContractEditController controller = loader.getController();
            controller.setEditStage(dialogStage);
            controller.setMainData(mainData);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     */

    public boolean showCalculatorDialog(MainData mainData, ArrayUtil arrayUtil) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/calculator.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Калькулятор");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(menuBar);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            CalculatorController controller = loader.getController();
            controller.setEditStage(dialogStage);
            controller.setMainData(mainData, arrayUtil);
            controller.setMain(this);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     *
     * @return
     */
    public File getDataFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
     * в реестре, специфичном для конкретной операционной системы.
     *
     * @param file - файл или null, чтобы удалить путь
     */
    public void setDataFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            menuBar.setTitle("SpringTime - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            menuBar.setTitle("SpringTime");
        }
    }

    /**
     * Загружает информацию об адресатах из указанного файла.
     * Текущая информация об адресатах будет заменена.
     *
     * @param file
     */
    public void loadDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(DataListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Чтение XML из файла и демаршализация.
            DataListWrapper wrapper = (DataListWrapper) um.unmarshal(file);

            contractData.clear();
            contractData.addAll(wrapper.getMainData());

            // Сохраняем путь к файлу в реестре.
            setDataFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Не удалось загрузить данные.");
            alert.setContentText("Не удалось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Сохраняет текущую информацию об адресатах в указанном файле.
     *
     * @param file
     */
    public void saveDataDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(DataListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об адресатах.
            DataListWrapper wrapper = new DataListWrapper();
            wrapper.setMainData(contractData);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setDataFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Не удалось сохранить данные");
            alert.setContentText("Не удалось сохранить данные в:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}
