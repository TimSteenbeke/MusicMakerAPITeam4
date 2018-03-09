package be.kdg.ip.services.api;


import be.kdg.ip.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface RoleService {

//    Role addRole(String roleName );

    Role addRole(Role role);

    Role getRole(int roleId);

    Role getRoleByName(String roleName);

    List<Role> getRoles();
}