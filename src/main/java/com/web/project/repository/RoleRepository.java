package com.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.project.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
