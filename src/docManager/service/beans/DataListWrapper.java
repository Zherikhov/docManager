package docManager.service.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Вспомогательный класс для обёртывания списка адресатов.
 * Используется для сохранения списка адресатов в XML.
 */
@XmlRootElement(name = "mainData")
public class DataListWrapper {

    private List<Document> mainData;

    @XmlElement(name = "mainData")
    public List<Document> getMainData() {
        return mainData;
    }

    public void setMainData(List<Document> mainData) {
        this.mainData = mainData;
    }
}

