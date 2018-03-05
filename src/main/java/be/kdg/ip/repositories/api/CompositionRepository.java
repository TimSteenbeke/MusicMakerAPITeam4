package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompositionRepository extends JpaRepository<Composition,Integer> {
}
