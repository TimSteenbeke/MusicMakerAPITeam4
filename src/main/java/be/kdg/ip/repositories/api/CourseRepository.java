package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
}
