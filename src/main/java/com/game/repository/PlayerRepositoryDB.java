package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepositoryDB() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/rpg");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "my-secret-pw");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(com.game.entity.Player.class)
                .buildSessionFactory();
    }


    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery<Player> nativeQuery = session.createNativeQuery("select * from rpg.player", Player.class);
            nativeQuery.setFirstResult(pageNumber);
            nativeQuery.setMaxResults(pageSize);
            List<Player> resultList = nativeQuery.getResultList();
            transaction.commit();
            return resultList;
        }
    }


    @Override
    public int getAllCount() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            transaction.commit();
           return 1;
        }
    }

    @Override
    public Player save(Player player) {
        return null;
    }

    @Override
    public Player update(Player player) {
        return null;
    }

    @Override
    public Optional<Player> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Player player) {

    }

    @PreDestroy
    public void beforeStop() {

    }
}