package be.kdg.ip.web;

import be.kdg.ip.domain.LesType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(method = RequestMethod.GET,value ="/api/greeting")
    public String hallo() {
      return "Hallo Mankey";
    }


    @RequestMapping(method = RequestMethod.GET,value ="/api/lestype")
    public LesType lesType() {
        LesType lesType = new LesType();
        lesType.setPrijs(20);
        lesType.setBeschrijving("een beschrijving voor Lorenz");
        lesType.setLesTypeId(1);
        return lesType;
    }

}
