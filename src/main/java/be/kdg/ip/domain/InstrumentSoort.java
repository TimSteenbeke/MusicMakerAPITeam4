package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "instrumentsoort")
public class InstrumentSoort {

    @Id
    @GeneratedValue
    @Column(name = "InstrumentSoortId",nullable = false)
    private int InstrumentSoortId;

    @Column
    private String soortNaam;
    @JsonIgnore
    @OneToMany( mappedBy="soort",cascade={CascadeType.MERGE})
    private List<Instrument> instrumentList;

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

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Instrument> instrumentList) {
        this.instrumentList = instrumentList;
    }
}
