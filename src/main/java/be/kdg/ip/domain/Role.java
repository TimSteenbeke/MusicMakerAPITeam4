package be.kdg.ip.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tRole")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="RoleType", discriminatorType = DiscriminatorType.STRING)
public abstract class Role {

    @Id
    @GeneratedValue
    @Column
    private int roleId;

    @Column
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }


    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
