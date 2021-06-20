package edu.practice.dao;

import edu.practice.domain.Document;
import edu.practice.domain.RegCard;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@Component
public class DocumentDao {
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Document> findDocuments() {
        return entityManager.createNamedQuery("Document.findAll")
                .getResultList();
    }

    public Document findDocumentById(int documentId) {
        Query query = entityManager.createNamedQuery("Document.findById");
        query.setParameter("documentId", documentId);
        List<Document> document = query.getResultList();
        return document.get(0);
    }

    public void loadDocument(String documentName, String author, String documentIntroNumber, String path) throws IOException {
        entityManager.getTransaction().begin();
        Document doc = new Document();
        doc.setCatalogId(1);
        doc.setDocumentName(documentName);
        doc.setAuthor(author);
        byte[] content = Files.readAllBytes(Paths.get(path));
        doc.setContent(content);
        entityManager.persist(doc);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        RegCard rc = new RegCard();
        rc.setDocumented(doc.getDocumentId());
        rc.setDocumentIntroNumber(documentIntroNumber);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        rc.setDateIntro(timestamp);
        entityManager.persist(rc);
        entityManager.getTransaction().commit();
    }

    public void downloadDocument(int documentId, String path) throws IOException {
        Document document = findDocumentById(documentId);
        byte[] Content = document.getContent();
        Files.write(Paths.get(path), Content);
    }

    public void deregisterDocument(int documentId, String documentExternNumber) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNamedQuery("RegCard.findRegCardByDocumentId");
        query.setParameter("documentId", documentId);
        List<RegCard> rc = query.getResultList();
        rc.get(0).setDocumentExternNumber(documentExternNumber);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        rc.get(0).setDateExtern(timestamp);
        entityManager.merge(rc.get(0));
        entityManager.getTransaction().commit();
    }
}
