package be.kdg.ip.services.impl;

import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.repositories.api.InstrumentCategoryRepository;
import be.kdg.ip.services.api.InstrumentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("InstrumentSoortService")
@Transactional
public class InstumentCategoryServiceImpl implements InstrumentCategoryService {
    @Autowired
    private InstrumentCategoryRepository instrumentCategoryRepository;


    @Override
    public InstrumentCategory getInstrumentCategory(int instrumentCategoryId) {
        return instrumentCategoryRepository.findOne(instrumentCategoryId);
    }

    @Override
    public InstrumentCategory addInstrumentCategory(InstrumentCategory instrumentCategory) {
        return instrumentCategoryRepository.save(instrumentCategory);
    }

    @Override
    public List<InstrumentCategory> getAllInstrumentCategories() {
        return instrumentCategoryRepository.findAll();
    }

    @Override
    public InstrumentCategory updateInstrumentCategory(InstrumentCategory instrumentCategory) {
        return instrumentCategoryRepository.save(instrumentCategory);
    }

    @Override
    public void removeInstrumentCategory(int instrumentCategoryId) {
        InstrumentCategory instrumentCategory = instrumentCategoryRepository.findOne(instrumentCategoryId);
        instrumentCategoryRepository.delete(instrumentCategory);
    }
}
