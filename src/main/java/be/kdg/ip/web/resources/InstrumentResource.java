package be.kdg.ip.web.resources;

import java.io.Serializable;

public class InstrumentResource implements Serializable {

    //private int InstrumentId;
    //private InstrumentSoortResource instrumentSoortResource;
    private int instrumentid;
    private String instrumentname;
    private String type;
    private String details;
    private String image;



    public int getInstrumentid() {
        return instrumentid;
    }

    public void setInstrumentid(int instrumentid) {
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


}