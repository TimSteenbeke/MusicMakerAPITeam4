package be.kdg.ip.services.api;

import be.kdg.ip.domain.Address;
import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    User addUser(String username, String password, String firstName, String lastName, List<Role> roles, byte[] userimage, Address address);

    User addUser(User user);

    List<User> getUsers();

    List<User> getUserWithRole(Role role);

    User findUser(int userId);

    User findUserByUsername(String username) throws UserServiceException;


    User updateUser(User user);

    void deleteUser(int userId);

    boolean checkLogin(String username, String password);


}