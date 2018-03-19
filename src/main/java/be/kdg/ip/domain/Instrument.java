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
    private String details;
    @Lob
    @Column
    private byte[] image;

    public Instrument(){

    }

    public Instrument(InstrumentCategory instrumentCategory, String instrumentName, String type, String details) {
        this.instrumentCategory = instrumentCategory;
        this.instrumentName = instrumentName;
        this.type = type;
        this.details = details;
    }


    public Instrument(InstrumentCategory instrumentCategory, String instrumentName, String type, String details, byte[] image) {
        this.instrumentCategory = instrumentCategory;
        this.instrumentName = instrumentName;
        this.type = type;
        this.details = details;
        this.image = image;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
