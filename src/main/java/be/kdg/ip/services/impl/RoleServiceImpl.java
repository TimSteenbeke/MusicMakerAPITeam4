package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.domain.roles.Student;
import be.kdg.ip.domain.roles.Teacher;
import be.kdg.ip.repositories.api.RoleRepository;
import be.kdg.ip.services.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wouter on 30.01.17.
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }


/*    @Override
    public Role addRole(String roleName) {
        if(roleName.toUpperCase().contains("ADMIN")){
            return roleRepository.save(new Administrator());
        } else if( roleName.toUpperCase().contains("STUDENT")){
            return roleRepository.save(new Student());
        } else if(roleName.toUpperCase().contains("TEACHER")){
            return roleRepository.save(new Teacher());
        } else {
           return null;
        }
    }*/

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

}
