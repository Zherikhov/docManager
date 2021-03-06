package docManager.model;

import java.time.LocalDate;
import java.util.Objects;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import static java.util.stream.Collectors.toList;
import docManager.service.storage.Document;

public class MainData {
    private final StringProperty numberContract = new SimpleStringProperty();                                           //номер договора
    private final ObjectProperty<LocalDate> dateContract = new SimpleObjectProperty<LocalDate>();                       //дата заключения договора
    private final StringProperty counterparty = new SimpleStringProperty();                                             //Контрагент
    private final StringProperty subjectContract = new SimpleStringProperty();                                          //Предмет договора
    private final ObjectProperty<LocalDate> dateExecutionContract = new SimpleObjectProperty<LocalDate>();              //дата исполнения договора
    private final ObjectProperty<LocalDate> timeContract = new SimpleObjectProperty<LocalDate>();                       //срок действия договора
    private final IntegerProperty price = new SimpleIntegerProperty();                                                  //цена договора
    private final IntegerProperty priceOnly = new SimpleIntegerProperty();                                              //остаток цены по договору
    private final ListProperty<Attachment> nameLink = new SimpleListProperty<>(FXCollections.observableArrayList());    //таблица с файлами
    private final ListProperty<Attachment> costs = new SimpleListProperty<>(FXCollections.observableArrayList());       //таблица с расходами

    /**
     * Конструктор по умолчанию.
     */
    public MainData() {
        this(null, null, null);
    }

    /**
     * Конструктор модели UI из данных
     */
    public MainData(Document doc) {
        this.numberContract.set(doc.getNumberContract());
        this.dateExecutionContract.set(doc.getDateExecutionContract());
        this.timeContract.set(doc.getTimeContract());
        this.dateContract.set(doc.getDateContract());
        this.counterparty.set(doc.getCounterparty());
        this.subjectContract.set(doc.getSubjectContract());
        this.price.set(doc.getPrice().intValue());
        this.priceOnly.set(doc.getPriceOnly().intValue());

        // Преобразуем коллекцию вложений - из пути к файлу сразу достаем имя для показа
        // в интерфейсе
        List<Attachment> atList = doc.getAttachments().stream().map(attach -> {
            Path p = Paths.get(attach.getFilename(), attach.getDescription());
            return new Attachment(this, attach.getFilename(), p.getFileName().toString());
        }).collect(toList());
        nameLink.getValue().setAll(atList);

        List<Attachment> atList2 = doc.getTransactions().stream().map(attach -> {
            Path p = Paths.get(attach.getSum(), attach.getDescription());
            return new Attachment(this, attach.getSum(), p.getFileName().toString());
        }).collect(toList());
        costs.getValue().setAll(atList2);
    }

    /**
     * @return данные из модели
     */
    public Document toDocument() {
        Document document = new Document();
        document.setNumberContract(numberContract.get());
        document.setDateExecutionContract(dateExecutionContract.get());
        document.setTimeContract(timeContract.get());
        document.setDateContract(dateContract.get());
        document.setCounterparty(counterparty.get());
        document.setSubjectContract(subjectContract.get());
        document.setPrice(BigDecimal.valueOf(price.get()));
        document.setPriceOnly(BigDecimal.valueOf(priceOnly.get()));

        List<docManager.service.storage.Attachment> filesNameLink = nameLink.stream().map(attach -> {
            docManager.service.storage.Attachment a = new docManager.service.storage.Attachment();
            a.setFilename(attach.getLink());
            a.setDescription(attach.getFileName());
            return a;
        }).collect(toList());
        document.setAttachments(filesNameLink);

        List<docManager.service.storage.Transaction> filesNameLink2 = costs.stream().map(attach -> {
            docManager.service.storage.Transaction a = new docManager.service.storage.Transaction();
            a.setSum(attach.getLink());
            a.setDescription(attach.getFileName());
            return a;
        }).collect(toList());
        document.setTransactions(filesNameLink2);

        return document;
    }

    /**
     * Конструктор с некоторыми начальными данными.
     */
    public MainData(String numberContract, LocalDate dateExecutionContract, LocalDate timeContract) {
        this.numberContract.set(numberContract);
        this.dateExecutionContract.set(dateExecutionContract);
        this.timeContract.set(timeContract);
    }

    public ListProperty<Attachment> getCosts() {
        return costs;
    }

    public int getCostsInteger() {
        String tempString;
        int tempInteger;
        int theEnd = 0;
        for (Attachment element : costs) {
            tempString = element.toString();
            tempInteger = Integer.parseInt(tempString);
            theEnd += tempInteger;
            System.out.println(theEnd);
        }
        return theEnd;
    }

    public ListProperty<Attachment> setCosts(String nameLink, String description) {
        this.costs.add(new Attachment(this, nameLink, description));
        return getCosts();
    }

    public ListProperty<Attachment> getNameLink() {
        return nameLink;
    }

    public ListProperty<Attachment> setNameLink(String nameLink, String description) {
        this.nameLink.add(new Attachment(this, nameLink, description));
        return getNameLink();
    }

    public String getNumberContract() {
        return numberContract.get();
    }

    public StringProperty numberContractProperty() {
        return numberContract;
    }

    public void setNumberContract(String numberContract) {
        this.numberContract.set(numberContract);
    }

    public LocalDate getDateContract() {
        return dateContract.get();
    }

    public void setDateContract(LocalDate dateContract) {
        this.dateContract.set(dateContract);
    }

    public String getCounterparty() {
        return counterparty.get();
    }

    public void setCounterparty(String counterparty) {
        this.counterparty.set(counterparty);
    }

    public String getSubjectContract() {
        return subjectContract.get();
    }

    public void setSubjectContract(String subjectContract) {
        this.subjectContract.set(subjectContract);
    }

    public LocalDate getDateExecutionContract() {
        return dateExecutionContract.get();
    }

    public ObjectProperty<LocalDate> dateExecutionContractProperty() {
        return dateExecutionContract;
    }

    public void setDateExecutionContract(LocalDate dateExecutionContract) {
        this.dateExecutionContract.set(dateExecutionContract);
    }

    public LocalDate getTimeContract() {
        return timeContract.get();
    }

    public LocalDate getCurrentTime() {
        LocalDate date = LocalDate.now();
        return date;
    }

    public ObjectProperty<LocalDate> timeContractProperty() {
        return timeContract;
    }

    public void setTimeContract(LocalDate timeContract) {
        this.timeContract.set(timeContract);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    @Override
    public String toString() {
        return "MainData{" + "nameLink=" + nameLink + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MainData mainData = (MainData) o;
        return Objects.equals(timeContract, mainData.timeContract);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeContract);
    }
}