package be.kdg.ip.web.resources;

public class InstrumentSoortResource {
    private int InstrumentSoortId;
    private String soortNaam;

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
