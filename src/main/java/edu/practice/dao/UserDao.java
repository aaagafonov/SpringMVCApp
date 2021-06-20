package edu.practice.dao;

import edu.practice.domain.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class UserDao {
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAll")
                .getResultList();
    }

    public User findById(int userId) {
        Query query = entityManager.createNamedQuery("User.findById");
        query.setParameter("userId", userId);
        List<User> user = query.getResultList();
        return user.get(0);
    }

    public User findByUsername(String username) {
        Query query = entityManager.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        List<User> user = query.getResultList();
        return user.get(0);
    }

    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }
}
