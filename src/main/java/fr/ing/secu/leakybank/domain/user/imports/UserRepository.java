package fr.ing.secu.leakybank.domain.user.imports;

import java.util.Optional;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;

@DDD.Repository
public interface UserRepository {
    public Optional<Customer> authenticate(User user);
}
