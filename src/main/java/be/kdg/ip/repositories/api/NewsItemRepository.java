package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsItemRepository extends JpaRepository<NewsItem,Integer> {
    List<NewsItem> findAllByOrderByDateDesc();
}
