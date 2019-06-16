package docManager.service.storage;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

public interface DocFileStorage {

	List<Document> read(File file) throws Exception;

	void write(File file, List<Document> docs) throws Exception;

	void write(OutputStream os, List<Document> docs) throws Exception;
}
