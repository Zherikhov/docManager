package docManager.model;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Attachment {

    private MainData contract;

    private String link;

    public Attachment() {

    }

    public Attachment(MainData contract, String link) {
        super();
        this.contract = contract;
        this.link = link;
    }

    public MainData getContract() {
        return contract;
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

    @Override
    public String toString() {
        return link;
    }

    public void openFile(){
        System.out.println("openFile");
        String fileName = contract.getNameLink().toString();
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