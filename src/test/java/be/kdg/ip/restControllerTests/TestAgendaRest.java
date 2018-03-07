package be.kdg.ip.restControllerTests;

import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.User;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.AgendaController;
import be.kdg.ip.web.resources.AgendaResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(AgendaController.class)
public class TestAgendaRest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;


    @MockBean
    private AgendaController agendaController;

    @Before
    public void createTestUser() {
        //Create a user
        Role administrator = new Administrator();
        List<Role> rolesAdmin = new ArrayList<Role>();
        rolesAdmin.add(administrator);
        User tom = new User("tom","tomiscool","tom","tomson",rolesAdmin);
        userService.addUser(tom);

    }

    @Test
    public void getAgendaLessons() throws Exception, UserServiceException {
      /*  Arrival arrival = new Arrival();
        arrival.setCity("Yerevan");

        List<Arrival> allArrivals = singletonList(arrival);

        given(arrivalController.getAllArrivals()).willReturn(allArrivals);

        mvc.perform(get(VERSION + ARRIVAL + "all")
                .with(user("blaze").password("Q1w2e3r4"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", is(arrival.getCity())));
        */
        //Prep test data
        User tom = userService.findUserByUsername("tom");
        Lesson lesson = new Lesson();
        int agendaId = tom.getAgenda().getAgendaId();

        //Resource to be expected
        AgendaResource agendaResource = new AgendaResource();
        agendaResource.getLessons().add(lesson);


        //Actual test
        given(agendaController.getAgenda(agendaId)).willReturn(new ResponseEntity<AgendaResource>(agendaResource,HttpStatus.OK));










    }
}
