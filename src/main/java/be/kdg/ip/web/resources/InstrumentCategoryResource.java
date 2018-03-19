package be.kdg.ip.web.resources;

import java.util.List;

public class InstrumentCategoryResource {
    private String categoryname;

    private List<Integer> instrumentIds;

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public List<Integer> getInstrumentIds() {
        return instrumentIds;
    }

    public void setInstrumentIds(List<Integer> instrumentIds) {
        this.instrumentIds = instrumentIds;
    }
}