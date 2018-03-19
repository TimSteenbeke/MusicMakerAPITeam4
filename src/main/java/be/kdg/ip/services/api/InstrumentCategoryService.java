package be.kdg.ip.services.api;


import be.kdg.ip.domain.InstrumentCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstrumentCategoryService {
    InstrumentCategory getInstrumentCategory(int instrumentCategoryId);
    InstrumentCategory addInstrumentCategory(InstrumentCategory instrumentCategory);
    List<InstrumentCategory> getAllInstrumentCategories();
    InstrumentCategory updateInstrumentCategory(InstrumentCategory instrumentCategory);
    void removeInstrumentCategory(int instrumentCategoryId);
}
