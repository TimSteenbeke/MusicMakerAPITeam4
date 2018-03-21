package be.kdg.ip.config.orika.custom.convertors;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConvertor /*extends CustomConverter<List<Role>, List<Role.RoleType>>*/ {

    /*@Override
    public List<Role.RoleType> convert(List<Role> roles, Type<? extends List<Role.RoleType>> type, MappingContext mappingContext) {
        return roles.stream().map(role -> role.getRoleType()).collect(Collectors.toList());
    }*/
}
