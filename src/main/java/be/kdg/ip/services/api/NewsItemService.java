package be.kdg.ip.services.api;

import be.kdg.ip.domain.NewsItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsItemService {
    NewsItem addNewsItem(NewsItem newsItem);
    NewsItem getNewsItem(int newsitemid);
    List<NewsItem> getNewsItems();
    void removeNewsItem(int newsitemid);
    NewsItem updateNewsItem(NewsItem newsItem);
}
