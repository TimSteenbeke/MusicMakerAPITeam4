package be.kdg.ip.web.dto;

import java.util.ArrayList;
import java.util.List;

public class RolesDTO {
    private List<RoleDTO> roles;

    public RolesDTO() {
        this.roles = new ArrayList<RoleDTO>();
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
