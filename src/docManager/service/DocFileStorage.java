package docManager.service;

import java.io.File;
import java.util.List;

import docManager.service.beans.Document;

public interface DocFileStorage {

	List<Document> read(File file) throws Exception;

	void write(File file, List<Document> docs) throws Exception;
}
