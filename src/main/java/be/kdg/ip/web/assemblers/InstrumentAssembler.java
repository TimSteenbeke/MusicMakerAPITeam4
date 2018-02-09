package be.kdg.ip.web.assemblers;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.web.resources.InstrumentResource;
import org.springframework.stereotype.Component;

@Component
public class InstrumentAssembler extends Assembler<Instrument, InstrumentResource> {

    public InstrumentAssembler() {
        super(Instrument.class, InstrumentResource.class);
    }
}
