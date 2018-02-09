package be.kdg.ip.services.api;

import be.kdg.ip.domain.LesType;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface LesTypeService {
    LesType addLesType(LesType lesType);
    LesType getLesType(int lesTypeId);
    LesType updateLestype(LesType lesType);
    void removeLesType(int lesTypeId);
    List<LesType> getAllLesTypes();
}
