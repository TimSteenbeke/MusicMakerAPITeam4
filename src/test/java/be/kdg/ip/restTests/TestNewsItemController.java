package be.kdg.ip.restTests;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.NewsItem;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.NewsItemService;
import be.kdg.ip.web.NewsItemController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestNewsItemController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private NewsItemController controller;

    @Autowired
    private OAuthHelper oAuthHelper;

    @MockBean
    private NewsItemService newsItemService;

    @MockBean
    private GroupService groupService;

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
    public void getNewsItemById() throws Exception{


        NewsItem newsItem = new NewsItem();
        newsItem.setNewsItemId(150);
        newsItem.setTitle("a fancy title");

        given(this.newsItemService.getNewsItem(150)).willReturn(newsItem);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/newsitems/150").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", CoreMatchers.is(newsItem.getTitle())))
                .andExpect(jsonPath("$.newsItemId", CoreMatchers.is(newsItem.getNewsItemId())));
    }


    @Test
    public void getAllNewsItems() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser","ADMIN");

        NewsItem newsItem = new NewsItem();
        newsItem.setTitle("the title");
        newsItem.setMessage("random message");

        List<NewsItem> newsItemList = singletonList(newsItem);

        given(newsItemService.getNewsItems()).willReturn(newsItemList);

        mockMvc.perform(get("http://localhost:8080/api/newsitems").with(bearerToken)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(newsItem.getTitle())))
                .andExpect(jsonPath("$[0].message", is(newsItem.getMessage())));

    }

    @Test
    public void deleteNewsItem() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser","ADMIN");

        NewsItem newsItem = new NewsItem();
        newsItem.setNewsItemId(230);
        newsItem.setTitle("the title");
        newsItem.setMessage("random message");

        given(newsItemService.getNewsItem(230)).willReturn(newsItem);

        this.mockMvc.perform(delete("http://localhost:8080/api/newsitems/230").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void addNewsItem() throws Exception {
        //TODO: fix test
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("jef","ADMIN");

        NewsItem newsItem = new NewsItem();
        newsItem.setNewsItemId(230);
        newsItem.setTitle("the title");
        newsItem.setMessage("random message");

        Group group = new Group();
        group.setGroupId(1);


        given(newsItemService.addNewsItem(Matchers.isA(NewsItem.class))).willReturn(newsItem);
        given(groupService.getGroup(1)).willReturn(group);

        this.mockMvc.perform(post("http://localhost:8080/api/newsitems/").with(bearerToken)
                .content(asJsonString(newsItem))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", CoreMatchers.is(newsItem.getTitle())))
                .andExpect(jsonPath("$.message", CoreMatchers.is(newsItem.getMessage())));
    }

    @Test
    public void updateNewsItem() throws Exception {
        //TODO: fix test
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser","ADMIN");
        NewsItem newsItem = new NewsItem();
        newsItem.setNewsItemId(230);
        newsItem.setTitle("the title");
        newsItem.setMessage("random message");

        Group group = new Group();
        group.setGroupId(1);


        given(newsItemService.addNewsItem(Matchers.isA(NewsItem.class))).willReturn(newsItem);
        given(groupService.getGroup(1)).willReturn(group);

        this.mockMvc.perform(put("http://localhost:8080/api/newsitems/newsitem/230").with(bearerToken)
                .content(asJsonString(newsItem))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }




    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        when(newsItemService.getNewsItem(123)).thenReturn(null);

        this.mockMvc.perform(get("http://localhost:8080/api/courseTypes/123").with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
