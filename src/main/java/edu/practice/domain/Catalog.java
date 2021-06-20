package edu.practice.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "catalog")
public class Catalog implements Comparable<Catalog> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalog_id")
    private int catalogId;

    @Column(name = "catalog_name")
    private String catalogName;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "catalog")
    private List<Document> documents;

    @Override
    public int compareTo(Catalog catalog) {
        if (this.catalogId == catalog.catalogId)
            return 0;
        else
            return this.catalogId > catalog.catalogId ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return catalogId == catalog.catalogId && catalogName.equals(catalog.catalogName) && documents.equals(catalog.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catalogId, catalogName, documents);
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }
}
