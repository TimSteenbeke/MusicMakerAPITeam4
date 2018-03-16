package be.kdg.ip.web;

import be.kdg.ip.domain.NewsItem;
import be.kdg.ip.services.api.NewsItemService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.resources.NewsItemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/newsitems")
public class NewsItemController {
    @Autowired
    NewsItemService newsItemService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST,value ="/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<NewsItemResource> addNewsItem(@Valid @RequestBody NewsItemResource newsItemResource) {
        NewsItem newsItem = new NewsItem();
        newsItem.setMessage(newsItemResource.getMessage());
        newsItem.setDate(new Date());
        newsItem.setEditor(userService.findUser(newsItemResource.getUserId()));

        String imageString = newsItemResource.getMessageImage();

        try {
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            newsItem.setMessageImage(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        newsItemService.addNewsItem(newsItem);

        return  new ResponseEntity<>(newsItemResource, HttpStatus.OK);
    }

    @GetMapping("/{newsitemId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<NewsItem> findNewsItemById(@PathVariable int newsitemId) {
        NewsItem newsItem = newsItemService.getNewsItem(newsitemId);
        return new ResponseEntity<NewsItem>(newsItem, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<NewsItem>> findAll(){
        List<NewsItem> newsItems = newsItemService.getNewsItems();
        return new ResponseEntity<>(newsItems,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{newsitemId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<NewsItemResource> deleteNewsItem(@PathVariable("newsitemId") int newsitemId) {
        newsItemService.removeNewsItem(newsitemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
