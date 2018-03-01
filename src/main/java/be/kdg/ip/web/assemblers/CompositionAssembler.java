package be.kdg.ip.web.assemblers;

import be.kdg.ip.domain.Composition;
import be.kdg.ip.web.resources.CompositionResource;
import org.springframework.stereotype.Component;

@Component
public class CompositionAssembler extends Assembler<Composition, CompositionResource> {
    public CompositionAssembler(){
        super(Composition.class,CompositionResource.class);
    }
}
