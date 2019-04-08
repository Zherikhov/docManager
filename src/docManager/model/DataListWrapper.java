package docManager.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Вспомогательный класс для обёртывания списка адресатов.
 * Используется для сохранения списка адресатов в XML.
 *
 * @author Marco Jakob
 */
@XmlRootElement(name = "mainData")
public class DataListWrapper {

    private List<MainData> mainData;

    @XmlElement(name = "mainData")
    public List<MainData> getMainData() {
        return mainData;
    }

    public void setMainData(List<MainData> mainData) {
        this.mainData = mainData;
    }
}

