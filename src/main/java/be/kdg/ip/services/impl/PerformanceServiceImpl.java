package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Performance;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.PerformanceRepository;
import be.kdg.ip.services.api.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PerformanceService")
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    PerformanceRepository performanceRepository;

    @Override
    public void addPerformance(Performance performance) {
        performanceRepository.save(performance);
    }

    @Override
    public Performance getPerformance(int performanceId) {
        return performanceRepository.findOne(performanceId);
    }

    @Override
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    @Override
    public void deletePerformance(int performanceId) {
        performanceRepository.delete(performanceId);
    }

    @Override
    public Performance updatePerformance(Performance performance) {
        return performanceRepository.save(performance);
    }

    @Override
    public void setUserPresent(int performanceId, User user) {
        Performance performance = performanceRepository.findOne(performanceId);

        if (!performance.getPresentMembers().contains(user)) {
            performance.getPresentMembers().add(user);

            if (performance.getAbsentMembers().contains(user)) {
                performance.getAbsentMembers().remove(user);
            }
        }

        performanceRepository.save(performance);
    }

    @Override
    public void setUserAbsent(int performanceId, User user) {
        Performance performance = performanceRepository.findOne(performanceId);

        if (!performance.getAbsentMembers().contains(user)) {
            performance.getAbsentMembers().add(user);

            if (performance.getPresentMembers().contains(user)) {
                performance.getPresentMembers().remove(user);
            }
        }
        performanceRepository.save(performance);
    }
}
