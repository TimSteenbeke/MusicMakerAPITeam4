package be.kdg.ip.web.resources;

public class InstrumentUpdateResource {
    private int instrumentsoortid;
    private String naam;
    private String type;
    private String uitvoering;
    private String afbeelding;



    public int getInstrumentsoortid() {
        return instrumentsoortid;
    }

    public void setInstrumentsoortid(int instrumentsoortid) {
        this.instrumentsoortid = instrumentsoortid;
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
