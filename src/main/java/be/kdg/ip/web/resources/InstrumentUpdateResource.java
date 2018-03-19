package be.kdg.ip.web.resources;

public class InstrumentUpdateResource {
    private int instrumentcategoryid;
    private String name;
    private String type;
    private String details;
    private String image;



    public int getInstrumentcategoryid() {
        return instrumentcategoryid;
    }

    public void setInstrumentcategoryid(int instrumentcategoryid) {
        this.instrumentcategoryid = instrumentcategoryid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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