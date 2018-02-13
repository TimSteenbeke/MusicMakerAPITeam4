package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
}
