package com.example.demo.services.interfaces;


import java.util.List;

import com.example.demo.model.User;

public interface UserService {
    
    public void saveUser(User user);
    public User getUserById(Integer id);
    public User findByUsername(String username);
    public User authenticateUser(String username, String password);
    public List<User> getAllUsers();
    public void deleteUser(Integer id);
    public void updateUser(User user);
}
