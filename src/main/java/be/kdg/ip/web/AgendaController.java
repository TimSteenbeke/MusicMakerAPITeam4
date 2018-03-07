package be.kdg.ip.web;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.resources.AgendaResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @Autowired
    private UserService userService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET,value ="/api/agenda")
    //ToDo: Authorization fix: Agenda
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<AgendaResource> getAgenda(Principal principal) {

        try {
            String username = principal.getName();
            User user =  userService.findUserByUsername(username);

            Agenda agenda= user.getAgenda();
            AgendaResource agendaResource = new AgendaResource();

            agendaResource.setAgendaOwner(agenda.getUser().getUsername());
            agendaResource.setAgendaId(agenda.getAgendaId());
            agendaResource.setLessons(agenda.getLessons());
            agendaResource.setPerformances(agenda.getPerformances());

            return  new ResponseEntity<>(agendaResource, HttpStatus.OK);

        } catch (UserServiceException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
