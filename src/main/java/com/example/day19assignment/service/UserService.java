package com.example.day19assignment.service;

import com.example.day19assignment.dao.implementation.UserDAOImpl;
import com.example.day19assignment.domain.Permission;
import com.example.day19assignment.domain.User;
import com.example.day19assignment.domain.UserDetail;
import com.example.day19assignment.domain.UserPermission;
import com.example.day19assignment.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserDAOImpl hibernateUserDAO;

    @Autowired
    public UserService(UserDAOImpl hibernateUserDAO) {
        this.hibernateUserDAO = hibernateUserDAO;
    }

    @Transactional
    public List<User> findAll() {
        return hibernateUserDAO.findAll();
    }

    @Transactional
    public User findUser(Integer userId) {
        return hibernateUserDAO.findUser(userId);
    }

    @Transactional
    public Integer creatUser(User user) {
        return hibernateUserDAO.createUser(user);
    }

    @Transactional
    public void creatUserDetail(UserDetail userDetail) {
        hibernateUserDAO.createUserDetail(userDetail);
    }

    @Transactional
    public void removeUser(Integer userId) {
        hibernateUserDAO.deleteUser(userId);
    }

    @Transactional
    public void changeUserStatus(Integer userId, boolean userStatus) {
        hibernateUserDAO.setUserStatus(userId, userStatus);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = hibernateUserDAO.findUserByUserName(username);

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // database user

        return AuthUserDetail.builder()
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        for (UserPermission userPermission :  user.getUserPermissions()){
            Permission permission = userPermission.getPermissionId();
            userAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        return userAuthorities;
    }

    @Transactional
    public User findUserByName(String username) {
        return hibernateUserDAO.findUserByName(username).orElse(null);
    }

    @Transactional
    public User findUserByEmail(String username) {
        return hibernateUserDAO.findUserByName(username).orElse(null);
    }
}
