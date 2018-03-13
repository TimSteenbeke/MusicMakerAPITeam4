package be.kdg.ip.web.resources;

public class InstrumentLevelResource {
    private int userid;
    private int maxlevel;
    private int level;
    private int instrumentid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMaxlevel() {
        return maxlevel;
    }

    public void setMaxlevel(int maxlevel) {
        this.maxlevel = maxlevel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getInstrumentid() {
        return instrumentid;
    }

    public void setInstrumentid(int instrumentid) {
        this.instrumentid = instrumentid;
    }
}
