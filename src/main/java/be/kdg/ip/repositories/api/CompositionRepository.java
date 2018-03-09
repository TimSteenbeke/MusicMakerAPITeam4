package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Composition;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition,Integer> {
    List<Composition> findAllByTitel(String title);
    List<Composition> findAllByGenre(String genre);
    List<Composition> findAllBySubject(String subject);
    List<Composition> findAllByInstrumentType(String type);
    List<Composition> findAllByFileFormatContains(String fileFormat);
}
