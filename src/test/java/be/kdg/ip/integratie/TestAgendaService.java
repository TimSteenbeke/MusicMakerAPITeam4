package be.kdg.ip.integratie;

import be.kdg.ip.IP2Application;
import be.kdg.ip.domain.*;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.services.api.AgendaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IP2Application.class)
@WebAppConfiguration
public class TestAgendaService {


    @Autowired
    private AgendaService agendaService;


    @Before
    public void setup(){
        List<Role> roles = Arrays.asList(new Administrator());
         User jef = new User("jef","jef","jefferson","jef@hotmail.com","jefiscool",roles);

         // Add 2 lessons



    }

    @Test
    public void TestGetAllItemsFromAgenda() {



    }
}
