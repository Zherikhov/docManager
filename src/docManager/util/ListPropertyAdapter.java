package docManager.util;

import javafx.beans.property.ListProperty;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ListPropertyAdapter extends XmlAdapter<String, ListProperty> {

    @Override
    public ListProperty unmarshal(String v) throws Exception {
        return ListProperty.add(v); //тут проблема
    }

    @Override
    public String marshal(ListProperty v) throws Exception {
        return v.toString();
    }
}