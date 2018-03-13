package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Address;
import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.UserRepository;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by wouter on 30.01.17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public User addUser(String username, String password, String firstName, String lastName, List<Role> roles, byte[] userimage, Address address) {
        return userRepository.save(new User(username,password,firstName,lastName,roles,userimage,address));
    }


    @Override
    public User addUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUserWithRole(Role role) {
        return userRepository.findAll().stream().filter(x -> x.getRoles().contains(role)).collect(Collectors.toList());
    }


    @Override
    public User findUserByUsername(String username) throws UserServiceException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UserServiceException("User not found");
        return user;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findUserById(userId);

        userRepository.delete(user);
    }

    @Override
    public boolean checkLogin(String username, String password) {
        return false;
    }

    @Override
    public User findUser(int userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
