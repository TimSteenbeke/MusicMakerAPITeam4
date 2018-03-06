package be.kdg.ip.services.api;


import be.kdg.ip.domain.InstrumentCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstrumentSoortService {
    InstrumentCategory getInstrumentSoort(int instrumentSoortId);
    InstrumentCategory addInstrumentSoort(InstrumentCategory instrumentCategory);
    List<InstrumentCategory> getAllInstrumentSoorten();
    InstrumentCategory updateInstrumentSoort(InstrumentCategory instrumentCategory);
    void removeInstrumentSoort(int instrumentsoortId);
}
