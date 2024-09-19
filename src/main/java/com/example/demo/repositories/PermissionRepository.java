package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Permission;

/**
 * PermissionRepository
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query(
        value = "Select p.* from permission p, user_role ur "+
                "where p.role_id = ur.role_id and ur.user_id = ?1",
        nativeQuery = true)
    List<Permission> findByUser(Integer user);
    
}