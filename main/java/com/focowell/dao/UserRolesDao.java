package com.focowell.dao;

import com.focowell.model.UserRoles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesDao extends CrudRepository<UserRoles, Long> {
   List< UserRoles> findByType(String roleType);
   
}
