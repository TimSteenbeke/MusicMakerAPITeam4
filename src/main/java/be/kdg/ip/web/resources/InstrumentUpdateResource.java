package be.kdg.ip.web.resources;

public class InstrumentUpdateResource {
    private String instrumentname;
    private String type;
    private String details;
    private String image;
    private Integer instrumentCategoryid;

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

    public Integer getInstrumentCategoryid() {
        return instrumentCategoryid;
    }

    public void setInstrumentCategoryid(Integer instrumentCategoryid) {
        this.instrumentCategoryid = instrumentCategoryid;
    }
}