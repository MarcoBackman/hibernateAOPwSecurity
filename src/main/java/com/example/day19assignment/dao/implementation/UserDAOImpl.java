package com.example.day19assignment.dao.implementation;

import com.example.day19assignment.dao.AbstractHibernateDAO;
import com.example.day19assignment.dao.UserDAO;
import com.example.day19assignment.domain.User;
import com.example.day19assignment.domain.UserDetail;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl extends AbstractHibernateDAO<User> implements UserDAO {
    Session session;
    CriteriaBuilder cb;
    CriteriaQuery<User> userCR;
    Root<User> userRoot;

    @Autowired
    public UserDAOImpl() {
        setClazz(User.class);
    }

    private void initializeUserSession() {
        session = getCurrentSession();
        cb = session.getCriteriaBuilder();
        userCR = cb.createQuery(User.class);
        userRoot = userCR.from(User.class);
    }


    @Override
    public List<User> findAll() {
        initializeUserSession();
        userCR.select(userRoot);
        Query<User> query = session.createQuery(userCR);
        return query.getResultList();
    }

    @Override
    public User findUser(Integer userId) {
        initializeUserSession();
        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("id"), userId));
        Query<User> query = session.createQuery(userCR);
        Optional<User> result = query.getResultList().stream().findAny();
        return result.orElse(null);
    }

    public Optional<User> findUserByUserName(String userName) {
        initializeUserSession();
        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("username"), userName));
        Query<User> query = session.createQuery(userCR);
        return query.getResultList().stream().findAny();
    }

    @Override
    public Integer createUser(User user) {
        session = getCurrentSession();
        return (Integer) session.save(user);
    }

    public void createUserDetail(UserDetail userDetail) {
        session = getCurrentSession();
        session.save(userDetail);
    }


    @Override
    public User deleteUser(Integer userId) {
        initializeUserSession();
        //Delete user detail data first
        CriteriaQuery<UserDetail> userDetailCR = cb.createQuery(UserDetail.class);
        Root<UserDetail> userDetailRoot = userDetailCR.from(UserDetail.class);
        userDetailCR.select(userDetailRoot);
        userDetailCR.where(cb.equal(userDetailRoot.get("id"), userId));
        Query<UserDetail> query1 = session.createQuery(userDetailCR);
        UserDetail userDetail = query1.getResultList().stream().findFirst().orElse(null);
        if (userDetail != null) {
            session.delete(userDetail);
        }

        //Delete user data first
        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("id"), userId));
        Query<User> query2 = session.createQuery(userCR);
        User user = query2.getResultList().stream().findFirst().orElse(null);
        if (user != null) {
            session.delete(user);
        }

        return user;
    }

    @Override
    public void setUserStatus(Integer userId, boolean activate) {
        initializeUserSession();
        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("id"), userId));
        Query<User> query = session.createQuery(userCR);
        User user = query.getResultList().stream().findFirst().orElse(null);
        if (user != null) {
            System.out.println("Updating user: " + user.getId() + " to: " + activate);
            session = getCurrentSession();
            user.setStatus(activate);
            session.update(user);
        }
    }

    public Optional<User> findUserByName(String username) {
        initializeUserSession();
        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("username"), username));
        Query<User> query = session.createQuery(userCR);
        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findUserByEmail(String email) {
        initializeUserSession();
        userCR.select(userRoot);
        userCR.where(cb.equal(userRoot.get("email"), email));
        Query<User> query = session.createQuery(userCR);
        return query.getResultList().stream().findFirst();
    }
}


