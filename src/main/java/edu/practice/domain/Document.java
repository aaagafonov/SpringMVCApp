package edu.practice.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "document")
@NamedQueries({
        @NamedQuery(name = "Document.findAll",
        query = "SELECT d FROM Document d " +
                "LEFT JOIN FETCH d.regCard rc"),

        @NamedQuery(name = "Document.findById",
                query = "SELECT d FROM Document d " +
                        "LEFT JOIN FETCH d.regCard rc " +
                        "WHERE d.documentId = :documentId")
})
public class Document implements Comparable<Document>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private int documentId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "document")
    private RegCard regCard;

    @Column(name = "catalog_id")
    private int catalogId;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id", insertable = false, updatable = false)
    private Catalog catalog;

    @Column(name = "document_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, message = "Name should be more than 4 characters")
    private String documentName;

    @Column(name = "author")
    @NotEmpty(message = "Should not be empty")
    private String author;

    @Lob
    @Column(name = "content")
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;

    @Transient
    private String tempPath;

    public int compareTo(Document document) {
        return documentName.compareTo(document.documentName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return documentName.equals(document.documentName) && author.equals(document.author) && Arrays.equals(content, document.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(documentName, author);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public RegCard getRegCard() {
        return regCard;
    }

    public void setRegCard(RegCard regCard) {
        this.regCard = regCard;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", documentName='" + documentName + '\'' +
                ", author='" + author + '\'' +
                ", regCard=" + regCard +
                '}';
    }
}
