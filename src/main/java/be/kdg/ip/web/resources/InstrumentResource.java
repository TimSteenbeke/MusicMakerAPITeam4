package be.kdg.ip.web.resources;

import be.kdg.ip.domain.InstrumentSoort;

import javax.persistence.*;
import java.io.Serializable;

public class InstrumentResource implements Serializable {

    private int InstrumentId;
    private int InstrumentSoortId;
    private String naam;
    private String type;
    private String uitvoering;
    private String afbeelding;

    public int getInstrumentId() {
        return InstrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        InstrumentId = instrumentId;
    }

    public int getInstrumentSoortId() {
        return InstrumentSoortId;
    }

    public void setInstrumentSoortId(int instrumentSoortId) {
        InstrumentSoortId = instrumentSoortId;
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
