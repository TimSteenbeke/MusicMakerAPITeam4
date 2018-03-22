package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Role;
import be.kdg.ip.repositories.api.RoleRepository;
import be.kdg.ip.services.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


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
        return roleRepository.findOne(roleId);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findAll().stream().filter(x -> x.getRoleName().equals(roleName)).findFirst().get();

    }


    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}
