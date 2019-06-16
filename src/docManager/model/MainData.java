package docManager.model;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import docManager.service.storage.Document;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class MainData {
	private final StringProperty numberContract = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> dateContract = new SimpleObjectProperty<LocalDate>();
	private final StringProperty counterparty = new SimpleStringProperty();
	private final StringProperty subjectContract = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> dateExecutionContract = new SimpleObjectProperty<LocalDate>();
	private final ObjectProperty<LocalDate> timeContract = new SimpleObjectProperty<LocalDate>();
	private final IntegerProperty price = new SimpleIntegerProperty();
	private final IntegerProperty priceOnly = new SimpleIntegerProperty();
	private final ListProperty<Attachment> nameLink = new SimpleListProperty<>(FXCollections.observableArrayList());;
	private final ListProperty<Attachment> costs = new SimpleListProperty<>(FXCollections.observableArrayList());
//    private final ListProperty<Attachment> costsDescription;

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

		this.counterparty.set(doc.getCounterparty());
		this.subjectContract.set(doc.getSubjectContract());
		this.price.set(doc.getPrice().intValue()); // TODO: деньги не int
		this.priceOnly.set(doc.getPriceOnly().intValue()); // TODO: деньги не int

		// Преобразуем коллекцию вложений - из пути к файлу сразу достаем имя для показа
		// в интерфейсе
		List<Attachment> atList = doc.getAttachments().stream().map(attach -> {
			Path p = Paths.get(attach.getFilename());
			return new Attachment(this, attach.getFilename(), p.getFileName().toString());
		}).collect(toList());
		nameLink.getValue().setAll(atList);

		// TODO: costs
	}

	/**
	 * @return данные из модели
	 */
	public Document toDocument() {
		Document document = new Document();
		document.setNumberContract(numberContract.get());
		document.setDateExecutionContract(dateExecutionContract.get());
		document.setTimeContract(timeContract.get());
		document.setCounterparty(counterparty.get());
		document.setSubjectContract(subjectContract.get());
		document.setPrice(BigDecimal.valueOf(price.get()));
		document.setPriceOnly(BigDecimal.valueOf(priceOnly.get()));

		List<docManager.service.storage.Attachment> files = nameLink.stream().map(attach -> {
			docManager.service.storage.Attachment a = new docManager.service.storage.Attachment();
			a.setFilename(attach.getLink());
			return a;
		}).collect(toList());
		document.setAttachments(files);

		// TODO: costs

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

	public ListProperty<Attachment> setCosts(String nameLink, String qwerty) {
		this.costs.add(new Attachment(this, nameLink, qwerty));
		return getCosts();
	}

	public ListProperty<Attachment> getNameLink() {
		return nameLink;
	}

	public ListProperty<Attachment> setNameLink(String nameLink, String qwerty) {
		this.nameLink.add(new Attachment(this, nameLink, qwerty));
		return getNameLink();
	}

	public int getPriceOnly() {
		return priceOnly.get();
	}

	public IntegerProperty priceOnlyProperty() {
		return priceOnly;
	}

	public void setPriceOnly(int priceOnly) {
		this.priceOnly.set(priceOnly);
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

	public ObjectProperty<LocalDate> dateContractProperty() {
		return dateContract;
	}

	public void setDateContract(LocalDate dateContract) {
		this.dateContract.set(dateContract);
	}

	public String getCounterparty() {
		return counterparty.get();
	}

	public StringProperty counterpartyProperty() {
		return counterparty;
	}

	public void setCounterparty(String counterparty) {
		this.counterparty.set(counterparty);
	}

	public String getSubjectContract() {
		return subjectContract.get();
	}

	public StringProperty subjectContractProperty() {
		return subjectContract;
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

	public IntegerProperty priceProperty() {
		return price;
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