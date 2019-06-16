package docManager.service.storage;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import docManager.util.LocalDateAdapter;

public class Transaction {

	private LocalDate date;

	private BigDecimal sum;

	public Transaction() {
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

}
