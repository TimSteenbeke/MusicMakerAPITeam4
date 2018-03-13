package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Composition;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition,Integer> {
    @Query(value = "SELECT r FROM Composition r where upper(r.subject) like concat('%',UPPER(:filter),'%') or" +
            " upper(r.titel) like concat('%',UPPER(:filter),'%') or" +
            " upper(r.genre) like concat('%',UPPER(:filter),'%') or" +
            " upper(r.artist) like concat('%',UPPER(:filter),'%') or" +
            " upper(r.fileFormat) like concat('%',UPPER(:filter),'%') or" +
            " upper(r.instrumentType) like concat('%',UPPER(:filter),'%')")
    List<Composition> findAllByFilter(@Param("filter") String filter);

}
