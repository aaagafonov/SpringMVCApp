package edu.practice.service;

import edu.practice.dao.DocumentDao;
import edu.practice.domain.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

@Service
public class DocumentService {
    private final EntityManagerFactory factory;

    @Autowired
    public DocumentService(EntityManagerFactory entityManagerFactory) {
        this.factory = entityManagerFactory;
    }

    @Autowired
    private DocumentDao documentDao;

    public List<Document> findDocuments() {
        EntityManager entityManager = factory.createEntityManager();
        documentDao.setEntityManager(entityManager);
        return documentDao.findDocuments();
    }

    public Document findDocumentById(int documentId) {
        EntityManager entityManager = factory.createEntityManager();
        documentDao.setEntityManager(entityManager);
        return documentDao.findDocumentById(documentId);
    }

    public void loadDocument(String documentName, String author, String documentIntroNumber, String path) throws IOException {
        EntityManager entityManager = factory.createEntityManager();
        documentDao.setEntityManager(entityManager);
        documentDao.loadDocument(documentName, author, documentIntroNumber, path);
    }

    public void downloadDocument(int documentId, String path) throws IOException {
        EntityManager entityManager = factory.createEntityManager();
        documentDao.setEntityManager(entityManager);
        documentDao.downloadDocument(documentId, path);
    }

    public void deregisterDocument(int documentId, String documentExternNumber) {
        EntityManager entityManager = factory.createEntityManager();
        documentDao.setEntityManager(entityManager);
        documentDao.deregisterDocument(documentId, documentExternNumber);
    }
}
