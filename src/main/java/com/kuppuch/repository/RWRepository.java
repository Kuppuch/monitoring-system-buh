package com.kuppuch.repository;

import com.kuppuch.model.RoleWork;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RWRepository extends CrudRepository<RoleWork, Long> {
    RoleWork findByRoleId(int roleID);
}
