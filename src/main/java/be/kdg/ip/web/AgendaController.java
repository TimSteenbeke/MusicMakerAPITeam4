package be.kdg.ip.web;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.web.resources.AgendaResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
public class AgendaController {
    private AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET,value ="/api/agenda/{agendaId}")
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<AgendaResource> getAgenda(@PathVariable("agendaId") int agendaId) {

       Agenda agenda=agendaService.getAgendaById(agendaId);
        AgendaResource agendaResource = new AgendaResource();

        agendaResource.setAgendaEigenaar(agenda.getUser().getUsername());
        agendaResource.setAgendaId(agenda.getAgendaId());
        agendaResource.setLessons(agenda.getLessons());
        agendaResource.setPerformances(agenda.getPerformances());

        return  new ResponseEntity<>(agendaResource, HttpStatus.OK);
    }



}
