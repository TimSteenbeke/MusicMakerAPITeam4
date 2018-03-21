package be.kdg.ip.web.resources;

import java.util.List;

public class GroupResource {
    private String name;
    private int supervisorid;
    private List<Integer> userids;
    private String groupimage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSupervisorid() {
        return supervisorid;
    }

    public void setSupervisorid(int supervisorid) {
        this.supervisorid = supervisorid;
    }

    public List<Integer> getUserids() {
        return userids;
    }

    public void setUserids(List<Integer> userids) {
        this.userids = userids;
    }


    public String getGroupimage() {
        return groupimage;
    }

    public void setGroupimage(String groupimage) {
        this.groupimage = groupimage;
    }
}
