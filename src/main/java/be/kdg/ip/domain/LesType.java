package be.kdg.ip.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lesType")
public class LesType {
    @Id
    @Column
    private int lesTypeId;
    @Column
    private String Beschrijving;
    @Column
    private int prijs;

    public int getLesTypeId() {
        return lesTypeId;
    }

    public void setLesTypeId(int lesTypeId) {
        this.lesTypeId = lesTypeId;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        Beschrijving = beschrijving;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }
}
