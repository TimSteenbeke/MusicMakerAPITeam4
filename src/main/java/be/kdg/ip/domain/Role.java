package be.kdg.ip.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by wouter on 21.12.16.
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="RoleType", discriminatorType = DiscriminatorType.STRING)
public abstract class Role {

    @Id
    @GeneratedValue
    @Column
    private String roleId;

    //@ManyToOne(targetEntity = User.class)
    //@JoinColumn(name = "id")
    @ManyToMany()
    private List<User> users;

    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}
