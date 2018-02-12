package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance,Integer> {
}
