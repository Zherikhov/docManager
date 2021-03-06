package docManager;

import docManager.controller.*;
import docManager.util.AlertWindow;
import docManager.service.storage.DocFileStorage;
import docManager.service.storage.Document;
import docManager.service.storage.JAXBDocStorage;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import docManager.model.MainData;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {

    private Stage menuBar;
    private BorderPane mainWindow;

    private DocFileStorage docStorage = new JAXBDocStorage();

    /**
     * Данные, в виде списка документов.
     */
    private ObservableList<MainData> contractData = FXCollections.observableArrayList();

    public ObservableList<MainData> getContractData() {
        return contractData;
    }

    public Main() {
    }

    /**
     * Возвращаем главную сцену.
     *
     * @return menuBar
     */
    public Stage getMenuBar() {
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.menuBar = primaryStage;
        menuBar.getIcons().add(new Image("file:stylelogo.png"));

        initMenuBar();
        initMainWindow();

        menuBar.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                AlertWindow.showAlertConfirmationExit();
            }
        });
    }

    /**
     * Инициализируем menuBar.
     */
    private void initMenuBar() {
        final int PREF_MIN_WIDTH = 1100;
        final int PREF_MIN_HEIGHT = 675;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/menuBarOverview.fxml"));
            mainWindow = loader.load();

            Scene scene = new Scene(mainWindow, PREF_MIN_WIDTH, PREF_MIN_HEIGHT);
            menuBar.setScene(scene);
            menuBar.showingProperty().addListener((observable, oldValue, showing) -> {
                if(showing) {
                    menuBar.setMinHeight(menuBar.getHeight());
                    menuBar.setMinWidth(menuBar.getWidth());
                }
            });

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
    private void initMainWindow() {
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
     * Открывает диалоговое окно для изменения деталей указанного договора. Если
     * пользователь кликнул OK, то изменения сохраняются в предоставленном объекте
     * адресата и возвращается значение true.
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
            dialogStage.setResizable(false);
            dialogStage.setTitle("Редактирование договора");
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
     * Открывает диалоговое окно для добавления документов в указанный договор. Если
     * пользователь кликнул OK, то изменения сохраняются в предоставленном объекте
     * документа и возвращается значение true.
     *
     * @param mainData - объект адресата, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showAddLink(MainData mainData) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/addLink.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setTitle("Прикрепить файл к документу");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(menuBar);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AddLinkController controller = loader.getController();
            controller.setEditStage(dialogStage);
            controller.setMainData(mainData);
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
     * Открывает диалоговое окно для добавление расходов в указанном договоре. Если
     * пользователь кликнул OK, то изменения сохраняются в предоставленном объекте
     * адресата и возвращается значение true.
     *
     * @param mainData - объект адресата, который надо изменить
     * @return true, если пользователь кликнул OK, в противном случае false.
     */
    public boolean showAddCost(MainData mainData) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/addCost.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setTitle("Указать расход");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(menuBar);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AddCostController controller = loader.getController();
            controller.setEditStage(dialogStage);
            controller.setMainData(mainData);
            controller.setMain(this);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void initAbout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/fxml/about.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setTitle("О программе");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(menuBar);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает preference файла документов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     *
     * @return filePath or null
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
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется в реестре,
     * специфичном для конкретной операционной системы.
     *
     * @param file - файл или null, чтобы удалить путь
     */
    public void setDataFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            menuBar.setTitle("DocManager - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            menuBar.setTitle("DocManager");
        }
    }

    /**
     * Загружает информацию документов из указанного файла. Текущая информация
     * документов будет заменена.
     */
    public void loadDataFromFile(File file) {
        try {
            List<MainData> mainDataList = docStorage.read(file).stream().map(MainData::new)
                    .collect(Collectors.toList());
            contractData.setAll(mainDataList);

            // Сохраняем путь к файлу в реестре.
            setDataFilePath(file);
        } catch (Exception e) {
            AlertWindow.showAlertError("Не удалось загрузить данные.",
                    "Пусть файла: " + file.getPath());
        }
    }

    /**
     * Сохраняет текущую информацию документов в указанном файле.
     */
    public void saveDataDataToFile(File file) {
        try {
            List<Document> docs = contractData.stream().map(MainData::toDocument).collect(Collectors.toList());
            docStorage.write(file, docs);

            // Сохраняем путь к файлу в реестре.
            setDataFilePath(file);
        } catch (Exception e) { // Ловим ЛЮБОЕ исключение
            AlertWindow.showAlertError("Не удалось сохранить данные.",
                    "Путь файла: " + file.getPath());
        }
    }
}
