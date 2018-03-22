package be.kdg.ip.services.api;

import be.kdg.ip.domain.Performance;
import be.kdg.ip.domain.User;

import java.util.List;

public interface PerformanceService {
    void addPerformance(Performance performance);
    Performance getPerformance(int performanceId);
    List<Performance> getAllPerformances();
    void deletePerformance(int performanceId);
    Performance updatePerformance(Performance performance);
    void setUserPresent(int performanceId, User user);
    void setUserAbsent(int performanceId, User user);

}
