package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Performance;
import be.kdg.ip.repositories.api.PerformanceRepository;
import be.kdg.ip.services.api.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PerformanceService")
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    PerformanceRepository performanceRepository;

    @Override
    public void addPerformance(Performance performance) {
        performanceRepository.save(performance);
    }
}
