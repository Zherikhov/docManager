package docManager.model;

import docManager.controller.MainController;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Attachment {

    private MainData contract;

    private String link;

    private String fileName;

    private MainController mainController;

    public Attachment() {

    }

    public Attachment(MainData contract, String qwerty) {
        super();
        this.contract = contract;
        this.link = qwerty;
        this.fileName = qwerty;
    }

    public MainData getContract() {
        return contract;
    }

    public MainController getMainController (MainController mainController) {
        return mainController;
    }

    public void setContract(MainData contract) {
        this.contract = contract;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return link;
    }

    public void openFile(){
        System.out.println("openFile");

//        MainData auxPerson = getTableView().getItems().get(getIndex());
//        System.out.println(auxPerson.toString());

        String fileName = contract.getNameLink().get(0).toString();
        System.out.println(fileName);



        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            desktop.open(new File(fileName));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}