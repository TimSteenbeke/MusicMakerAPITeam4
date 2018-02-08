package be.kdg.ip.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by wouter on 21.12.16.
 */
@Entity
@Table(name="role")
public abstract class Role {

   @Id
   @GeneratedValue
    private Integer roleId;

    @ManyToOne(targetEntity = User.class)
   private User user;

    public abstract Collection<? extends GrantedAuthority> getAuthorities();
}
