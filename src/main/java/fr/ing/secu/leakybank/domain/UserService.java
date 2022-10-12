package fr.ing.secu.leakybank.domain;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;
import fr.ing.secu.leakybank.domain.user.imports.UserRepository;

import java.util.Optional;

@Service
@DDD.Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<Customer> authenticate(User user) {
        return userRepository.authenticate(user);
    }

}
