package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, Integer> {
}
