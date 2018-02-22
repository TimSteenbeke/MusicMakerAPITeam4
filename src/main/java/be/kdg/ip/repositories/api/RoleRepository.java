package be.kdg.ip.repositories.api;



import be.kdg.ip.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleById(int roleId);

    Role findRoleByRoleName(String roleName);
}
