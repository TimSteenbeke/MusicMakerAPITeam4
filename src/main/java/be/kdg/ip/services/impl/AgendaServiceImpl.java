package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.AgendaRepository;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AgendaService")
public class AgendaServiceImpl implements AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UserService userService;


    @Override
    public Agenda getAgenda(String username) {

        try {
            User user = userService.findUserByUsername(username);
            return user.getAgenda();

        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveAgenda(Agenda agenda) {
        agendaRepository.save(agenda);
    }

    @Override
    public Agenda getAgendaById(int agendaId) {
        return agendaRepository.findOne(agendaId);
    }
}
