package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserServiceImpl() {
    }

    @Override
    @Transactional
    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getUser(Long id) {
        return userRepo.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    @Transactional
    public void editUser(User user) {

        if (user.getPassword().equals(userRepo.findUserByEmail(user.getEmail()).getPassword())) {
            userRepo.save(user);
        } else if (user.getPassword().equals("") & user.getRoles() != null) {
            user.setPassword(userRepo.findUserByEmail(user.getEmail()).getPassword());
            userRepo.save(user);
        } else if (user.getRoles() == null) {
            user.setRoles(findUserByEmail(user.getEmail()).getRoles());
            saveUser(user);
        } else if (user.getPassword().equals("") && user.getRoles() == null) {
            user.setPassword(userRepo.findUserByEmail(user.getEmail()).getPassword());
            user.setRoles(findUserByEmail(user.getEmail()).getRoles());
            userRepo.save(user);
        } else {
            saveUser(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepo.findUserByEmail(userName);
    }
}