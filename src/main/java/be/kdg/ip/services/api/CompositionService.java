package be.kdg.ip.services.api;

import be.kdg.ip.domain.Composition;

import java.util.List;

public interface CompositionService {
    Composition addComposition(Composition composition);
    List<Composition> getAllCompositions();
    Composition getComposition(int compositionId);
    void removeComposition(int compositionId);
    Composition updateComposition(Composition instrument);
    List<Composition> getCompositionsByTitle(String title);
    List<Composition> getCompositionsByGenre(String genre);
    List<Composition> getCompositionsBySubject(String subject);
    List<Composition> getCompositionsByType(String instrumentType);
    List<Composition> getCompositionsByFormat(String format);
}
