package com.example.demo.services.interfaces;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.example.demo.model.User;

public interface UserService {
    
    @PreAuthorize("hasRole('WRITE-USER')")
    public void saveUser(User user);

    @PreAuthorize("hasAnyRole('READ-USER', 'USER-INFO')")
    public User getUserById(Integer id);

    @PreAuthorize("hasRole('READ-USER')")
    public User findByUsername(String username);

    public User authenticateUser(String username, String password);

    @PreAuthorize("hasRole('READ-USER')")
    public List<User> getAllUsers();

    @PreAuthorize("hasRole('DELETE-USER')")
    public void deleteUser(Integer id);

    @PreAuthorize("hasAnyRole('UPDATE-USER', 'WRITE-USER')")
    public void updateUser(User user);
}
