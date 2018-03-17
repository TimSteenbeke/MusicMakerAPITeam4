package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTypeRepository extends JpaRepository<CourseType,Integer> {
}
