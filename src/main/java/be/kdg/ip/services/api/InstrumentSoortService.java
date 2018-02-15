package be.kdg.ip.services.api;


import be.kdg.ip.domain.InstrumentSoort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstrumentSoortService {
    InstrumentSoort getInstrumentSoort(int instrumentSoortId);
    InstrumentSoort addInstrumentSoort(InstrumentSoort instrumentSoort);
    List<InstrumentSoort> getAllInstrumentSoorten();
    InstrumentSoort updateInstrumentSoort(InstrumentSoort instrumentSoort);
    void removeInstrumentSoort(int instrumentsoortId);
}
