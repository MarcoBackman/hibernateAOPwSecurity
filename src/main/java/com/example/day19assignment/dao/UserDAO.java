package com.example.day19assignment.dao;

import com.example.day19assignment.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    public List<User> findAll();
    public User findUser(Integer userId);
    public Optional<User> findUserByUserName(String username);
    public Integer createUser(User user);
    public User deleteUser(Integer userId);
    public void setUserStatus(Integer userId, boolean activate);
}
