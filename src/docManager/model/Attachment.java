package docManager.model;

public class Attachment {

	private MainData contract;

	private String link;

	public Attachment() {

	}

	public Attachment(MainData contract, String link) {
		super();
		this.contract = contract;
		this.link = link;
	}

	public MainData getContract() {
		return contract;
	}

	public void setContract(MainData contract) {
		this.contract = contract;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "Attachment [link=" + link + "]";
	}

}
