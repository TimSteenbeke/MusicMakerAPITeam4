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


    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(int roleId) {
        Role role = roleRepository.findOne(roleId);
        return role;
    }

}
