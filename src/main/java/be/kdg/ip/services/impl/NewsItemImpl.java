package be.kdg.ip.services.impl;

import be.kdg.ip.domain.NewsItem;
import be.kdg.ip.repositories.api.NewsItemRepository;
import be.kdg.ip.services.api.NewsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NewsItemService")
public class NewsItemImpl implements NewsItemService {
    @Autowired
    NewsItemRepository newsItemRepository;

    @Override
    public NewsItem addNewsItem(NewsItem newsItem) {
        return newsItemRepository.save(newsItem);
    }

    @Override
    public NewsItem getNewsItem(int newsitemid) {
        return newsItemRepository.findOne(newsitemid);
    }

    @Override
    public List<NewsItem> getNewsItems() {
        return newsItemRepository.findAllByOrderByDateDesc();
    }

    @Override
    public void removeNewsItem(int newsitemid) {
        newsItemRepository.delete(newsitemid);
    }

    @Override
    public NewsItem updateNewsItem(NewsItem newsItem) {
        return newsItemRepository.save(newsItem);
    }
}
