package docManager.util;

import docManager.model.MainData;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ListPropertyAdapter extends XmlAdapter<String, ListProperty> {
    MainData mainData = new MainData();

    @Override
    public SimpleListProperty unmarshal(String v) throws Exception {

        return (SimpleListProperty) SimpleListProperty.listExpression(mainData.getCosts()); //тут проблема
    }

    @Override
    public String marshal(ListProperty v) throws Exception {
        return v.toString();
    }
}