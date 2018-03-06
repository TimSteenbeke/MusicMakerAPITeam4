package be.kdg.ip.services.impl;

import be.kdg.ip.domain.InstrumentCategory;
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
    public InstrumentCategory getInstrumentSoort(int instrumentSoortId) {
        return instrumentSoortRepository.findOne(instrumentSoortId);
    }

    @Override
    public InstrumentCategory addInstrumentSoort(InstrumentCategory instrumentCategory) {
        return instrumentSoortRepository.save(instrumentCategory);
    }

    @Override
    public List<InstrumentCategory> getAllInstrumentSoorten() {
        return instrumentSoortRepository.findAll();
    }

    @Override
    public InstrumentCategory updateInstrumentSoort(InstrumentCategory instrumentCategory) {
        return instrumentSoortRepository.save(instrumentCategory);
    }

    @Override
    public void removeInstrumentSoort(int instrumentsoortId) {
        InstrumentCategory instrumentCategory = instrumentSoortRepository.findOne(instrumentsoortId);
        instrumentSoortRepository.delete(instrumentCategory);
    }
}
