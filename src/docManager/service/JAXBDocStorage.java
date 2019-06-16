package docManager.service;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import docManager.service.beans.DataListWrapper;
import docManager.service.beans.Document;

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

	public static void main(String[] args) throws Exception {
		JAXBDocStorage docStorage = new JAXBDocStorage();

		List<Document> docs = docStorage.read(new File("/home/ruslan/Projects/docManager/src/Док.xml"));

		for (Document document : docs) {
			System.out.println(document);
		}

	}
}
