package ru.kata.spring.boot_security.demo;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class init {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostConstruct
    private void postConstruct() {
        Role admin = new Role();
        admin.setId(1L);
        admin.setRole("ROLE_ADMIN");
        Role user = new Role();
        user.setId(2L);
        user.setRole("ROLE_USER");
        roleRepository.saveAll(List.of(admin, user));

        User adminUser = new User();
        adminUser.setName("Oleg");
        adminUser.setLastName("Petrov");
        adminUser.setAge((byte) 24);
        adminUser.setEmail("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.addRole(admin);

        User normalUser = new User();
        normalUser.setName("Muslim");
        normalUser.setLastName("Tuibaev");
        normalUser.setAge((byte) 18);
        normalUser.setEmail("user");
        normalUser.setPassword(passwordEncoder.encode("user"));
        normalUser.addRole(user);

        userRepository.save(adminUser);
        userRepository.save(normalUser);
    }

}


