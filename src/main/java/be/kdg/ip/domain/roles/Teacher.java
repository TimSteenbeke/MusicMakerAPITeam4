package be.kdg.ip.domain.roles;


import be.kdg.ip.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Teacher extends Role {

    public Teacher() {
        super("Teacher");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("TEACHER"));
        return authorities;
    }
}
