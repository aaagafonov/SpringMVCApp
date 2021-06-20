package edu.practice.service;

import edu.practice.dao.UserDao;
import edu.practice.domain.Role;
import edu.practice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final EntityManagerFactory factory;

    @Autowired
    public UserService(EntityManagerFactory entityManagerFactory) {
        this.factory = entityManagerFactory;
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EntityManager entityManager = factory.createEntityManager();
        userDao.setEntityManager(entityManager);
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        userDao.setEntityManager(entityManager);
        Optional<User> userFromDb = Optional.ofNullable(userDao.findById(userId));
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        EntityManager entityManager = factory.createEntityManager();
        userDao.setEntityManager(entityManager);
        return userDao.findAll();
    }

    public boolean saveUser(User user) {
        EntityManager entityManager = factory.createEntityManager();
        userDao.setEntityManager(entityManager);
        User userFromDB = userDao.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return true;
    }
}