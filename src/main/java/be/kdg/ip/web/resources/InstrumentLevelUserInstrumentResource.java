package be.kdg.ip.web.resources;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class InstrumentLevelUserInstrumentResource {
    private int maxLevel;
    private int level;

    private Instrument instrument;
    @JsonIgnoreProperties({"username","lastname","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","instrumentLevels"})
    private User user;

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
