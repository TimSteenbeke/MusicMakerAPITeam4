package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.repositories.api.InstrumentRepository;
import be.kdg.ip.services.api.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("InstrumentService")
@Transactional
public class InstrumentServiceImpl implements InstrumentService {
    @Autowired
    private InstrumentRepository repository;

    @Override
    public Instrument addInstrument(Instrument instrument) {
        return repository.save(instrument);
    }

    @Override
    public Instrument getInstrument(int instrumentId) {
        Instrument instrument = repository.findOne(instrumentId);
        return instrument;
    }

    @Override
    public List<Instrument> getAllInstruments() {
        return repository.findAll();
    }
}
