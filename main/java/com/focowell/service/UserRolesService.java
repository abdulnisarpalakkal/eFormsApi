package com.focowell.service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.UserRoles;

import java.util.List;

public interface UserRolesService  {

    
    List<UserRoles> findAll();
    void delete(long id);
    UserRoles findOne(String userRolesname);

    UserRoles findById(Long id);
    UserRoles save(UserRoles userRoles) throws AlreadyExistsException;
    UserRoles update(UserRoles userRoles);
}
