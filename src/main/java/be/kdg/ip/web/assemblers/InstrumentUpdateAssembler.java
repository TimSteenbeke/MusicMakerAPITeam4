package be.kdg.ip.web.assemblers;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.web.resources.InstrumentUpdateResource;
import org.springframework.stereotype.Component;

@Component
public class InstrumentUpdateAssembler extends Assembler<Instrument, InstrumentUpdateResource> {
    public InstrumentUpdateAssembler() {
        super(Instrument.class, InstrumentUpdateResource.class);
    }

}
