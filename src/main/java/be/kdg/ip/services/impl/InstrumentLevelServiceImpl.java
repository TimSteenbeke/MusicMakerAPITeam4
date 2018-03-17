package be.kdg.ip.services.impl;

import be.kdg.ip.domain.InstrumentLevel;
import be.kdg.ip.repositories.api.InstrumentLevelRepository;
import be.kdg.ip.services.api.InstrumentLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("InstrumentLevelService")
@Transactional
public class InstrumentLevelServiceImpl implements InstrumentLevelService {

    @Autowired
    private InstrumentLevelRepository instrumentLevelRepository;

    @Override
    public InstrumentLevel addInstrumentLevel(InstrumentLevel instrumentLevel) {
        return instrumentLevelRepository.save(instrumentLevel);
    }

    @Override
    public InstrumentLevel getIntrumentLevel(int instrumentLevelId) {
        return instrumentLevelRepository.findOne(instrumentLevelId);
    }

    @Override
    public List<InstrumentLevel> getAllInstrumentLevels() {
        return instrumentLevelRepository.findAll();
    }

    @Override
    public InstrumentLevel updateInstrumentLevel(InstrumentLevel instrumentLevel) {
        return instrumentLevelRepository.save(instrumentLevel);
    }

    @Override
    public void removeInstrumentLevel(int instrumentLevelId) {
        InstrumentLevel instrumentLevel = instrumentLevelRepository.findOne(instrumentLevelId);
        instrumentLevelRepository.delete(instrumentLevel);
    }
}
