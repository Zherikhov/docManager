package docManager.service;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import docManager.service.beans.Attachment;
import docManager.service.beans.DataListWrapper;
import docManager.service.beans.Document;
import docManager.service.beans.Transaction;

/**
 * Хранилище документов, эта реализация использует JAXB.
 *
 */
public class JAXBDocStorage implements DocFileStorage {

	public List<Document> read(File file) throws Exception {
		JAXBContext context = JAXBContext.newInstance(DataListWrapper.class);
		Unmarshaller um = context.createUnmarshaller();

		// Чтение XML из файла и демаршализация.
		DataListWrapper wrapper = (DataListWrapper) um.unmarshal(file);

		return wrapper.getMainData();
	}

	public void write(File file, List<Document> docs) throws Exception {
		JAXBContext context = JAXBContext.newInstance(DataListWrapper.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// Обёртываем наши данные об адресатах.
		DataListWrapper wrapper = new DataListWrapper();
		wrapper.setMainData(docs);

		// Маршаллируем и сохраняем XML в файл.
		m.marshal(wrapper, file);
	}

	@Override
	public void write(OutputStream os, List<Document> docs) throws Exception {
		JAXBContext context = JAXBContext.newInstance(DataListWrapper.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// Обёртываем наши данные об адресатах.
		DataListWrapper wrapper = new DataListWrapper();
		wrapper.setMainData(docs);

		// Маршаллируем и сохраняем XML в файл.
		m.marshal(wrapper, os);
	}

	public static void main(String[] args) throws Exception {
		JAXBDocStorage docStorage = new JAXBDocStorage();

		List<Document> docs = docStorage.read(new File("/home/ruslan/Projects/docManager/src/Док.xml"));

		Attachment a1 = new Attachment();
		a1.setFilename("d:/docs/my/sample1.doc");
		Attachment a2 = new Attachment();
		a2.setFilename("d:/docs/my/sample2.doc");
		docs.get(0).getAttachments().add(a1);
		docs.get(0).getAttachments().add(a2);

		Transaction t1 = new Transaction();
		t1.setDate(LocalDate.now());
		t1.setSum(BigDecimal.valueOf(32000));
		docs.get(0).getTransactions().add(t1);

		for (Document document : docs) {
			System.out.println(document);
		}

		docStorage.write(System.out, docs);

	}

}
