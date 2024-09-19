package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.interfaces.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Role> getUserRoles(User user) {
        return user.getRoles().stream().map(m -> m.getRole()).toList();
    }

    @Override
    public List<Permission> getRolePermissions(Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRolePermissions'");
    }

    @Override
    public void saveRole(Role role, int ... permissionIds) {
       roleRepository.save(role);
       for (int i : permissionIds) {
                Permission permission = permissionRepository.findById(i).get();
                permission.setRole(role);  
                permissionRepository.save(permission);
            }
    }

    @Override
    public List<Permission> getUserPermissions(User user) {
        return permissionRepository.findByUser(user.getId());
    }
}
