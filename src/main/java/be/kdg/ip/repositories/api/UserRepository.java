package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //User findUserByUsername(String username);

    User findUserById(int userId);

    User findByUsername(String username);
}
