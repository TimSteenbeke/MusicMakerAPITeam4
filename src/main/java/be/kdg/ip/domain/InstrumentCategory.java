package be.kdg.ip.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instrumentCategory")
public class InstrumentCategory {

    @Id
    @GeneratedValue
    @Column(name = "instrumentCategoryId", nullable = false)
    private int instrumentCategoryId;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "instrumentCategory", cascade = {CascadeType.MERGE})
    private List<Instrument> instrumentList;

    public InstrumentCategory() {
        this.instrumentList = new ArrayList<>();
    }

    public InstrumentCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getInstrumentCategoryId() {
        return instrumentCategoryId;
    }

    public void setInstrumentCategoryId(int instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Instrument> instrumentList) {
        this.instrumentList = instrumentList;
    }
}
