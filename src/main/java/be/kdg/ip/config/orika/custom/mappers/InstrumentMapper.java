package be.kdg.ip.config.orika.custom.mappers;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.web.resources.InstrumentResource;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InstrumentMapper extends CustomMapper<Instrument,InstrumentResource> {
    @Override
    public void mapAtoB(Instrument source, InstrumentResource destination, MappingContext context) {
       // mapperFacade.map(source.getSoort(), destination.getInstrumentSoortResource());
    }

    @Override
    public void mapBtoA(InstrumentResource source, Instrument destination, MappingContext context) {
       // mapperFacade.map(source.getInstrumentSoortResource(), destination.getSoort());
    }
}
