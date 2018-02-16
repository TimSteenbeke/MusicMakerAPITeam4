package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findOne(int groupId);
}
