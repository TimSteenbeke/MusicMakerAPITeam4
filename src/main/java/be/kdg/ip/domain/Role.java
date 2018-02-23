package be.kdg.ip.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="RoleType", discriminatorType = DiscriminatorType.STRING)
public abstract class Role {

    @Id
    @GeneratedValue
    @Column
    private int roleId;

    @Column(name = "RoleName", nullable = true, length = 255)
    private String roleName;

    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}
