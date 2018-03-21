package be.kdg.ip.web.resources;

import be.kdg.ip.domain.Instrument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

public class InstrumentCatergoryGetResource {
    private int categoryid;
    private String categoryname;

    @JsonIgnoreProperties({"instrumentCategory"})
    private List<Instrument> instruments = new ArrayList<>();

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }
}
