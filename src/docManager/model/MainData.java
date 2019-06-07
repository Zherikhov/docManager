package docManager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.*;
import docManager.util.LocalDateAdapter;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.binding.ListExpression;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class MainData {
	private final StringProperty numberContract;
	private final ObjectProperty<LocalDate> dateContract;
	private final StringProperty counterparty;
	private final StringProperty subjectContract;
	private final ObjectProperty<LocalDate> dateExecutionContract;
	private final ObjectProperty<LocalDate> timeContract;
	private final IntegerProperty price;
	private final IntegerProperty priceOnly;
	private final ListProperty<Attachment> nameLink;

	private int[] sumСosts = new int[12];

	/**
	 * Конструктор по умолчанию.
	 */
	public MainData() {
		this(null, null, null);
	}

	/**
	 * Конструктор с некоторыми начальными данными.
	 */
	public MainData(String numberContract, LocalDate dateExecutionContract, LocalDate timeContract) {
		this.numberContract = new SimpleStringProperty(numberContract);
		this.dateExecutionContract = new SimpleObjectProperty(dateExecutionContract);
		this.timeContract = new SimpleObjectProperty(timeContract);

		// Какие-то фиктивные начальные данные для удобства тестирования.
		this.dateContract = new SimpleObjectProperty();
		this.counterparty = new SimpleStringProperty();
		this.subjectContract = new SimpleStringProperty();
		this.price = new SimpleIntegerProperty();
		this.priceOnly = new SimpleIntegerProperty();
		this.nameLink = new SimpleListProperty<>(FXCollections.observableArrayList());
		this.nameLink.add(new Attachment(this, "qwerty"));
		this.nameLink.add(new Attachment(this, "asdfgh"));
	}

	public ListProperty<Attachment> getNameLink() {
		return nameLink;
	}

	public ListProperty<Attachment> setNameLink(String nameLink) {
		this.nameLink.add(new Attachment(this, nameLink));
		return getNameLink();
	}

	public Integer getSumСostsInt() {
		Integer temp = 0;
		for (int i = 0; i < sumСosts.length; i++) {
			temp = temp + sumСosts[i];
		}
		return temp;
	}

	public int[] getCosts() {
		return sumСosts;
	}

	public void setSumСosts(int costs) {
		for (int i = 0; i < sumСosts.length; i++) {
			if (sumСosts[i] == 0) {
				sumСosts[i] = costs;
				return;
			}
		}
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

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
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

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDateExecutionContract() {
		return dateExecutionContract.get();
	}

	public ObjectProperty<LocalDate> dateExecutionContractProperty() {
		return dateExecutionContract;
	}

	public void setDateExecutionContract(LocalDate dateExecutionContract) {
		this.dateExecutionContract.set(dateExecutionContract);
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getTimeContract() {
		return timeContract.get();
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
}
