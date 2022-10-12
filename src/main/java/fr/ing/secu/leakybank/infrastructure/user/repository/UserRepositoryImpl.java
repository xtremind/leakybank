package fr.ing.secu.leakybank.infrastructure.user.repository;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;
import fr.ing.secu.leakybank.domain.user.imports.UserRepository;
import fr.ing.secu.leakybank.infrastructure.user.mapper.UserMapper;
import fr.ing.secu.leakybank.infrastructure.user.repository.db.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UsersDAO userDao;

    @Override
    public Optional<Customer> authenticate(User user) {
        return userDao.login(user.getLogin(), user.getPassword())
                .map(customerEntity -> {return UserMapper.INSTANCE.toDomain(customerEntity);});
    }
}
