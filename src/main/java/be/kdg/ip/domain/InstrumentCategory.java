package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "instrumentType")
public class InstrumentCategory {

    @Id
    @GeneratedValue
    @Column(name = "instrumentCategoryId",nullable = false)
    private int instrumentCategoryId;

    @Column
    private String typeName;
    @JsonIgnore
    @OneToMany( mappedBy="type",cascade={CascadeType.MERGE})
    private List<Instrument> instrumentList;

    public InstrumentCategory(){

    }

    public InstrumentCategory(String typeName) {
        this.typeName = typeName;
    }

    public int getInstrumentCategoryId() {
        return instrumentCategoryId;
    }

    public void setInstrumentCategoryId(int instrumentCategoryId) {
        this.instrumentCategoryId = instrumentCategoryId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<Instrument> instrumentList) {
        this.instrumentList = instrumentList;
    }
}
