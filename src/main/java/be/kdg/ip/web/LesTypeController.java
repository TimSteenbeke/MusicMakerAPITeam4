package be.kdg.ip.web;

import be.kdg.ip.domain.LesType;
import be.kdg.ip.services.api.LesTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LesTypeController {
    private LesTypeService lesTypeService;

    public LesTypeController(LesTypeService lesTypeService) {
        this.lesTypeService = lesTypeService;
    }

    @RequestMapping(method = RequestMethod.GET,value ="/lestypes")
    public List<LesType> getLesTypes() {
        return lesTypeService.getAllLesTypes();
    }
}
