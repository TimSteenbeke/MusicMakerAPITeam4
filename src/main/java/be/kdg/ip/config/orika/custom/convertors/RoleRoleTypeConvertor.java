package be.kdg.ip.config.orika.custom.convertors;

import be.kdg.ip.domain.Role;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

@Component
public class RoleRoleTypeConvertor /*extends CustomConverter<Role, Role.RoleType>*/ {

    /*@Override
    public Role.RoleType convert(Role role, Type<? extends Role.RoleType> type, MappingContext mappingContext) {
        return role.getRoleType();
    }*/

}
