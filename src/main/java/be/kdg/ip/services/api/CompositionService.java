package be.kdg.ip.services.api;

import be.kdg.ip.domain.Composition;

import java.util.List;

public interface CompositionService {
    Composition addComposition(Composition composition);
    List<Composition> getAllCompositions();
    Composition getComposition(int compositionId);
    void removeComposition(int compositionId);
    Composition updateComposition(Composition instrument);
}
