package docManager.service.storage;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import docManager.util.LocalDateAdapter;

@XmlRootElement(name = "transaction")
public class Transaction {

    private LocalDate date;

    private String sum;

    private String description;

    public Transaction() {
    }

    @XmlAttribute(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String descriptionж) {
        this.description = descriptionж;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlAttribute(name = "sum")
    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

}