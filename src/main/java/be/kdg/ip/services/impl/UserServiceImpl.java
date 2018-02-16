package be.kdg.ip.services.impl;

import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.UserRepository;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * Created by wouter on 30.01.17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public User addUser(User user) {

        user.setEncryptedPassword(passwordEncoder.encode(user.getEncryptedPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) throws UserServiceException {
        User user = userRepository.findUserByUsername(username);
        if (user == null)
            throw new UserServiceException("User not found");
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserServiceException {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new UserServiceException("User not found");
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(s);
    }
}
