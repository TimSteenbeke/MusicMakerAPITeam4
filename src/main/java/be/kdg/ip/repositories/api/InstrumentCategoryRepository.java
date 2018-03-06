package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.InstrumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentCategoryRepository extends JpaRepository<InstrumentCategory, Integer> {
}
