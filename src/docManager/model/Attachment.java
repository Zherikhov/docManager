package docManager.model;

import docManager.controller.MainController;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Attachment {

    private MainData contract;

    private String link;

    private int sum;

    private String fileName;

    private MainController mainController;

    public Attachment() {

    }

    public Attachment(MainData contract, String link, String fileName) {
        super();
        this.contract = contract;
        this.link = link;
        this.fileName = fileName;
    }

    public Attachment(MainData contract, int sum, String fileName) {
        super();
        this.contract = contract;
        this.sum = sum;
        this.fileName = fileName;
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

    public void openFile(String fileLink){
         Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            desktop.open(new File(fileLink));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}