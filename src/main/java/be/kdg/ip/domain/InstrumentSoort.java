package be.kdg.ip.domain;

import javax.persistence.*;

@Entity
@Table(name = "instrumentsoort")
public class InstrumentSoort {

    @Id
    @GeneratedValue
    @Column(name = "InstrumentSoortId",nullable = false)
    private int InstrumentSoortId;

    @Column
    private String soortNaam;

    public InstrumentSoort(){

    }

    public InstrumentSoort(String soortNaam) {
        this.soortNaam = soortNaam;
    }

    public int getInstrumentSoortId() {
        return InstrumentSoortId;
    }

    public void setInstrumentSoortId(int instrumentSoortId) {
        InstrumentSoortId = instrumentSoortId;
    }

    public String getSoortNaam() {
        return soortNaam;
    }

    public void setSoortNaam(String soortNaam) {
        this.soortNaam = soortNaam;
    }
}
