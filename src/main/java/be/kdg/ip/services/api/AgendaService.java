package be.kdg.ip.services.api;

import be.kdg.ip.domain.Agenda;

import java.util.List;

public interface AgendaService {
    Agenda getAgenda(String username);
    void saveAgenda(Agenda agenda);
    Agenda getAgendaById(int agendaId);


}
