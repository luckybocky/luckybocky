package com.project.luckybocky.user.repository;

import com.project.luckybocky.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor//final이 붙은 변수에 생성자주입
public class UserSettingRepository {

    private final EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User findById(String id) {
        return em.find(User.class, id);
    }

    public Optional<User> findByKey(String userKey) {
        return em.createQuery("select u from User u where u.userKey=:userKey", User.class)
                .setParameter("userKey", userKey)
                .getResultList().stream().findAny();
    }


}
