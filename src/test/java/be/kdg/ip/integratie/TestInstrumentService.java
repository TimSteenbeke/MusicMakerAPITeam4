package be.kdg.ip.integratie;

import be.kdg.ip.IP2Application;
import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.services.api.InstrumentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IP2Application.class)
@WebAppConfiguration
public class TestInstrumentService {
    @Autowired
    private InstrumentService instrumentService;

    @Before
    public void setup(){
        InstrumentCategory soort = new InstrumentCategory("piano");
        Instrument instrument = new Instrument(soort,"naam","type","uitvoering");
        instrumentService.addInstrument(instrument);
    }

    @Test
    public void TestAddInstrument(){
        InstrumentCategory soort = new InstrumentCategory("gitaar");
        Instrument instrument = new Instrument(soort,"naam2","type2","uitvoering2");
        instrumentService.addInstrument(instrument);
        Instrument opgehaaldInstrument = instrumentService.getInstrument(instrument.getInstrumentId());
        assertEquals(instrument.getInstrumentName(),opgehaaldInstrument.getInstrumentName());
        assertEquals(instrument.getType(),opgehaaldInstrument.getType());
    }

}
