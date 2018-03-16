package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsItemRepository extends JpaRepository<NewsItem,Integer> {
}
