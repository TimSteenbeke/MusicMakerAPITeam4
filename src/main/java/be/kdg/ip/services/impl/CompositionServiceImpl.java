package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Composition;
import be.kdg.ip.repositories.api.CompositionRepository;
import be.kdg.ip.services.api.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("CompositionService")
@Transactional
public class CompositionServiceImpl implements CompositionService {
    @Autowired
    private CompositionRepository compositionRepository;

    @Override
    public Composition addComposition(Composition composition) {
        return compositionRepository.save(composition);
    }

    @Override
    public List<Composition> getAllCompositions() {
        return compositionRepository.findAll();
    }

    @Override
    public Composition getComposition(int compositionId) {
        return compositionRepository.findOne(compositionId);
    }

    @Override
    public void removeComposition(int compositionId) {
        compositionRepository.delete(compositionId);
    }

    @Override
    public Composition updateComposition(Composition composition) {
        return compositionRepository.save(composition);
    }

    @Override
    public List<Composition> getCompositionsByTitle(String title) {
        return compositionRepository.findAllByTitel(title);
    }

    @Override
    public List<Composition> getCompositionsByGenre(String genre) {
        return compositionRepository.findAllByGenre(genre);
    }

    @Override
    public List<Composition> getCompositionsBySubject(String subject) {
        return compositionRepository.findAllBySubject(subject);
    }

    @Override
    public List<Composition> getCompositionsByType(String instrumentType) {
        return compositionRepository.findAllByInstrumentType(instrumentType);
    }

    @Override
    public List<Composition> getCompositionsByFormat(String format) {
        return compositionRepository.findAllByFileFormat(format);
    }
}
