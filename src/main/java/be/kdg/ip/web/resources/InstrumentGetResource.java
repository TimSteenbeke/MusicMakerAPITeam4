package be.kdg.ip.web.resources;

import be.kdg.ip.domain.InstrumentCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class InstrumentGetResource {
    private Integer instrumentid;
    private String instrumentname;
    private String type;
    private String details;
    private String image;
    @JsonIgnoreProperties({"instrumentList"})
    private InstrumentCategory instrumentCategory;

    public Integer getInstrumentid() {
        return instrumentid;
    }

    public void setInstrumentid(Integer instrumentid) {
        this.instrumentid = instrumentid;
    }

    public String getInstrumentname() {
        return instrumentname;
    }

    public void setInstrumentname(String instrumentname) {
        this.instrumentname = instrumentname;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public InstrumentCategory getInstrumentCategory() {
        return instrumentCategory;
    }

    public void setInstrumentCategory(InstrumentCategory instrumentCategory) {
        this.instrumentCategory = instrumentCategory;
    }
}
