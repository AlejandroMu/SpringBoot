package com.example.demo.services.interfaces;

import java.util.List;

import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.User;

public interface RoleService {
    
    public List<Role> getUserRoles(User user);
    public List<Permission> getRolePermissions(Role role);
    public void saveRole(Role role, int ... permissionIds);
    public List<Permission> getUserPermissions(User user);
}
