package be.kdg.ip.domain;

import javax.persistence.*;

@Entity
@Table(name ="instrumentlevel")
public class InstrumentLevel {

    @Id
    @GeneratedValue
    @Column(name="instrumentLevelId",nullable = false)
    private int instrumentLevelId;

    @Column
    private int maxLevel;
    @Column
    private int level;

    @ManyToOne
    private Instrument instrument;

    @ManyToOne
    private User user;

    public InstrumentLevel() {
    }

    public InstrumentLevel(int maxLevel, int level, Instrument instrument, User user) {
        this.maxLevel = maxLevel;
        this.level = level;
        this.instrument = instrument;
        this.user = user;
    }

    public int getInstrumentLevelId() {
        return instrumentLevelId;
    }

    public void setInstrumentLevelId(int instrumentLevelId) {
        this.instrumentLevelId = instrumentLevelId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
