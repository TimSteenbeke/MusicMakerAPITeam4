package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.repositories.api.InstrumentRepository;
import be.kdg.ip.services.api.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service("InstrumentService")
@Transactional
public class InstrumentServiceImpl implements InstrumentService {
    @Autowired
    private InstrumentRepository instrumentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Instrument addInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    @Override
    public Instrument getInstrument(int instrumentId) {
        return instrumentRepository.findOne(instrumentId);
    }


    @Override
    public List<Instrument> getAllInstruments() {
        return instrumentRepository.findAll();
    }

    @Override
    public void removeInstrument(int instrumentId) {
        Instrument instrument = instrumentRepository.findOne(instrumentId);

        instrumentRepository.delete(instrument);
    }

    @Override
    public Instrument updateInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }
}
