package be.kdg.ip.services.impl;

import be.kdg.ip.domain.InstrumentSoort;
import be.kdg.ip.repositories.api.InstrumentSoortRepository;
import be.kdg.ip.services.api.InstrumentSoortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("InstrumentSoortService")
@Transactional
public class InstumentSoortServiceImpl implements InstrumentSoortService {
    @Autowired
    private InstrumentSoortRepository instrumentSoortRepository;


    @Override
    public InstrumentSoort getInstrumentSoort(int instrumentSoortId) {
        return instrumentSoortRepository.findOne(instrumentSoortId);
    }

    @Override
    public InstrumentSoort addInstrumentSoort(InstrumentSoort instrumentSoort) {
        return instrumentSoortRepository.save(instrumentSoort);
    }

    @Override
    public List<InstrumentSoort> getAllInstrumentSoorten() {
        return instrumentSoortRepository.findAll();
    }

    @Override
    public InstrumentSoort updateInstrumentSoort(InstrumentSoort instrumentSoort) {
        return instrumentSoortRepository.save(instrumentSoort);
    }

    @Override
    public void removeInstrumentSoort(int instrumentsoortId) {
        InstrumentSoort instrumentSoort = instrumentSoortRepository.findOne(instrumentsoortId);
        instrumentSoortRepository.delete(instrumentSoort);
    }
}
