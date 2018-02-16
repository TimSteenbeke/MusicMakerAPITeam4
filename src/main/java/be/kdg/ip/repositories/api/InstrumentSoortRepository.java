package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.InstrumentSoort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentSoortRepository extends JpaRepository<InstrumentSoort, Integer> {
}
