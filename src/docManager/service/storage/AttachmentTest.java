package docManager.service.storage;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attachments")
public class AttachmentTest {

    private String filenameTest;

    private String filenameTest2;

    public AttachmentTest() {
    }

    @XmlAttribute(name = "filenameTest")
    public String getFilenameTest() {
        return filenameTest;
    }

    public void setFilenameTest(String filename) {
        this.filenameTest = filename;
    }

    @XmlAttribute(name = "filenameTest")
    public String getFilenameTest2() {
        return filenameTest2;
    }

    public void setFilenameTest2(String filename) {
        this.filenameTest2 = filename;
    }
}