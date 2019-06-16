package docManager.service.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import docManager.util.LocalDateAdapter;

public class Document {

	private String numberContract;
	private LocalDate dateContract;
	private String counterparty;
	private String subjectContract;
	private LocalDate dateExecutionContract;
	private LocalDate timeContract;
	private BigDecimal price;
	private BigDecimal priceOnly;

	private List<Attachment> attachments = new ArrayList<>();
	private List<Transaction> transactions = new ArrayList<>();

	public Document() {
	}

	public String getNumberContract() {
		return numberContract;
	}

	public void setNumberContract(String numberContract) {
		this.numberContract = numberContract;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDateContract() {
		return dateContract;
	}

	public void setDateContract(LocalDate dateContract) {
		this.dateContract = dateContract;
	}

	public String getCounterparty() {
		return counterparty;
	}

	public void setCounterparty(String counterparty) {
		this.counterparty = counterparty;
	}

	public String getSubjectContract() {
		return subjectContract;
	}

	public void setSubjectContract(String subjectContract) {
		this.subjectContract = subjectContract;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDateExecutionContract() {
		return dateExecutionContract;
	}

	public void setDateExecutionContract(LocalDate dateExecutionContract) {
		this.dateExecutionContract = dateExecutionContract;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getTimeContract() {
		return timeContract;
	}

	public void setTimeContract(LocalDate timeContract) {
		this.timeContract = timeContract;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPriceOnly() {
		return priceOnly;
	}

	public void setPriceOnly(BigDecimal priceOnly) {
		this.priceOnly = priceOnly;
	}

	@XmlElementWrapper(name = "attachments")
	@XmlElement(name = "attachment")
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	@XmlElementWrapper(name = "transactions")
	@XmlElement(name = "transaction")
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Document [numberContract=" + numberContract + ", dateContract=" + dateContract + ", counterparty="
				+ counterparty + "]";
	}

}
