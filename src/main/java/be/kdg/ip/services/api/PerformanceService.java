package be.kdg.ip.services.api;

import be.kdg.ip.domain.Performance;
import be.kdg.ip.domain.User;

public interface PerformanceService {
    void addPerformance(Performance performance);
    //TODO: cruds
    Performance getPerformance(int PerformanceId);

    void setUserPresent(int performanceId, User user);
    void setUserAbsent(int performanceId, User user);

}
