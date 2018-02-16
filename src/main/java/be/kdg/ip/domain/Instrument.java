package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name ="instrument")
public class Instrument {

    @Id
    @GeneratedValue
    @Column(name="InstrumentId",nullable = false)
    private int InstrumentId;

    @ManyToOne(cascade = {CascadeType.MERGE},fetch= FetchType.EAGER)
    @JoinColumn(name = "InstrumentSoortId")
    private InstrumentSoort soort;
    @Column
    private String naam;
    @Column
    private String type;
    @Column
    private String uitvoering;
    @Column
    private String afbeelding;

    public Instrument(){

    }

    public Instrument(InstrumentSoort soort, String naam, String type, String uitvoering, String afbeelding) {
        this.soort = soort;
        this.naam = naam;
        this.type = type;
        this.uitvoering = uitvoering;
        this.afbeelding = afbeelding;
    }

    public int getInstrumentId() {
        return InstrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        InstrumentId = instrumentId;
    }

    public InstrumentSoort getSoort() {
        return soort;
    }

    public void setSoort(InstrumentSoort soort) {
        this.soort = soort;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUitvoering() {
        return uitvoering;
    }

    public void setUitvoering(String uitvoering) {
        this.uitvoering = uitvoering;
    }

    public String getAfbeelding() {
        return afbeelding;
    }

    public void setAfbeelding(String afbeelding) {
        this.afbeelding = afbeelding;
    }
}
