package be.kdg.ip.services.api;

import be.kdg.ip.domain.InstrumentLevel;

import java.util.List;

public interface InstrumentLevelService {

    InstrumentLevel addInstrumentLevel(InstrumentLevel instrumentLevel);
    InstrumentLevel getIntrumentLevel(int instrumentLevelId);
    List<InstrumentLevel> getAllInstrumentLevels();
    InstrumentLevel updateInstrumentLevel(InstrumentLevel instrumentLevel);
    void removeInstrumentLevel(int instrumentLevelId);
}
