package be.kdg.ip.domain;

import javax.persistence.*;

@Entity
@Table(name ="instrumentlevel")
public class InstrumentLevel {

    @Id
    @GeneratedValue
    @Column(name="InstrumentLevelId",nullable = false)
    private int InstrumentLevelId;

    @Column
    private int maxLevel;
    @Column
    private int level;

    @ManyToOne
    private Instrument instrument;

    public InstrumentLevel() {
    }

    public int getInstrumentLevelId() {
        return InstrumentLevelId;
    }

    public void setInstrumentLevelId(int instrumentLevelId) {
        InstrumentLevelId = instrumentLevelId;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
