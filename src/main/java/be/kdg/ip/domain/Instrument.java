package be.kdg.ip.domain;

import javax.persistence.*;

@Entity
@Table(name ="instrument")
public class Instrument {

    @Id
    @GeneratedValue
    @Column(name="instrumentId",nullable = false)
    private int instrumentId;

    @ManyToOne(cascade = {CascadeType.MERGE},fetch= FetchType.EAGER)
    @JoinColumn(name = "instrumentCategoryId")
    private InstrumentCategory instrumentCategory;
    @Column
    private String instrumentName;
    @Column
    private String type;
    @Column
    private String uitvoering;
    @Lob
    @Column
    private byte[] afbeelding;

    public Instrument(){

    }

    public Instrument(InstrumentCategory instrumentCategory, String instrumentName, String type, String uitvoering) {
        this.instrumentCategory = instrumentCategory;
        this.instrumentName = instrumentName;
        this.type = type;
        this.uitvoering = uitvoering;
    }


    public Instrument(InstrumentCategory instrumentCategory, String instrumentName, String type, String uitvoering, byte[] afbeelding) {
        this.instrumentCategory = instrumentCategory;
        this.instrumentName = instrumentName;
        this.type = type;
        this.uitvoering = uitvoering;
        this.afbeelding = afbeelding;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public InstrumentCategory getInstrumentCategory() {
        return instrumentCategory;
    }

    public void setInstrumentCategory(InstrumentCategory instrumentCategory) {
        this.instrumentCategory = instrumentCategory;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
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

    public byte[] getAfbeelding() {
        return afbeelding;
    }

    public void setAfbeelding(byte[] afbeelding) {
        this.afbeelding = afbeelding;
    }
}
