package be.kdg.ip.services.impl;

import be.kdg.ip.domain.LesType;
import be.kdg.ip.repositories.api.LesTypeRepository;
import be.kdg.ip.services.api.LesTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("LesTypeService")
@Transactional
public class LesTypeImpl implements LesTypeService{

    @Autowired
    private LesTypeRepository repository;

    @Override
    public LesType addLesType(LesType lesType) {
        return repository.save(lesType);
    }

    @Override
    public LesType getLesType(int lesTypeId) {
        return repository.findOne(lesTypeId);
    }

    @Override
    public LesType updateLestype(LesType lesType) {
        return repository.save(lesType);
    }

    @Override
    public void removeLesType(int lesTypeId) {
        repository.delete(lesTypeId);
    }

    @Override
    public List<LesType> getAllLesTypes() {
        return repository.findAll();
    }
}
