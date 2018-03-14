package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.CourseType;
import be.kdg.ip.services.api.CourseTypeService;
import be.kdg.ip.web.CourseTypeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ContextConfiguration
public class TestCourseType {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CourseTypeController controller;

    @Autowired
    private OAuthHelper oAuthHelper;


    @MockBean
    private CourseTypeService courseTypeService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testGetCourseTypeById() throws Exception{
        int courseTypeId = 1;

        CourseType courseType = new CourseType();
        courseType.setPrice(10);
        courseType.setDescription("Gitaarles");

        given(this.courseTypeService.getCourseType(courseTypeId)).willReturn(courseType);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("jos","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/courseTypes/1").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", CoreMatchers.is(courseType.getPrice())))
                .andExpect(jsonPath("$.courseTypeDescription", CoreMatchers.is(courseType.getDescription())));
    }


    @Test
    public void testReturn404WhenNotFound() throws Exception {
        when(courseTypeService.getCourseType(99999)).thenReturn(null);

        this.mockMvc.perform(get("api/courseTypes/99999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
