package be.kdg.ip.services.api;

import be.kdg.ip.domain.Instrument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstrumentService {
    Instrument addInstrument(Instrument instrument);

    Instrument getInstrument(int instrumentId);

    List<Instrument> getAllInstruments();

    void removeInstrument(int instrumentId);
    Instrument updateInstrument(Instrument instrument);
}
