package edu.practice.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reg_card")
@NamedQueries({
        @NamedQuery(name = "RegCard.findRegCardByDocumentId",
                query = "SELECT rc FROM RegCard rc " +
                        "WHERE rc.documented = :documentId")
})
public class RegCard implements Comparable<RegCard> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_card_id")
    private int regCardId;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="documented", insertable = false, updatable = false)
    private Document document;

    @Column(name = "documented")
    private int documented;

    @Column(name = "document_intro_number")
    private String documentIntroNumber;

    @Column(name = "document_extern_number")
    private String documentExternNumber;

    @Column(name = "date_intro")
    private java.sql.Timestamp dateIntro;

    @Column(name = "date_extern")
    private java.sql.Timestamp dateExtern;

    public int compareTo(RegCard rc) {
        return dateIntro.compareTo(rc.dateIntro);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegCard regCard = (RegCard) o;
        return documented == regCard.documented && documentIntroNumber.equals(regCard.documentIntroNumber) && Objects.equals(documentExternNumber, regCard.documentExternNumber) && dateIntro.equals(regCard.dateIntro) && Objects.equals(dateExtern, regCard.dateExtern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documented, documentIntroNumber, documentExternNumber, dateIntro, dateExtern);
    }

    public int getRegCardId() {
        return regCardId;
    }

    public void setRegCardId(int regCardId) {
        this.regCardId = regCardId;
    }

    public int getDocumented() {
        return documented;
    }

    public void setDocumented(int documented) {
        this.documented = documented;
    }

    public String getDocumentIntroNumber() {
        return documentIntroNumber;
    }

    public void setDocumentIntroNumber(String documentIntroNumber) {
        this.documentIntroNumber = documentIntroNumber;
    }

    public String getDocumentExternNumber() {
        return documentExternNumber;
    }

    public void setDocumentExternNumber(String documentExternNumber) {
        this.documentExternNumber = documentExternNumber;
    }

    public Timestamp getDateIntro() {
        return dateIntro;
    }

    public void setDateIntro(Timestamp dateIntro) {
        this.dateIntro = dateIntro;
    }

    public Timestamp getDateExtern() {
        return dateExtern;
    }

    public void setDateExtern(Timestamp dateExtern) {
        this.dateExtern = dateExtern;
    }

    @Override
    public String toString() {
        return "RegCard{" +
                "regCardId=" + regCardId +
                ", documented=" + documented +
                ", documentIntroNumber='" + documentIntroNumber + '\'' +
                ", documentExternNumber='" + documentExternNumber + '\'' +
                ", dateIntro=" + dateIntro +
                ", dateExtern=" + dateExtern +
                '}';
    }
}
