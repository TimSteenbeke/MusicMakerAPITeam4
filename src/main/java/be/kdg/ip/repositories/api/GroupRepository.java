package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findOne(int groupId);
}
