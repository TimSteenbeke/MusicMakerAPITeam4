package be.kdg.ip.web.assemblers;

import be.kdg.hardwareshop.domain.user.User;
import be.kdg.hardwareshop.web.resources.users.UserResource;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends Assembler<User, UserResource> {

    public UserAssembler() {
        super(User.class, UserResource.class);
    }
}
