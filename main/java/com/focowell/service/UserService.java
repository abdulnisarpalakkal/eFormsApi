package com.focowell.service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.User;
import com.focowell.model.dto.UserDto;

import java.util.List;

public interface UserService  {

    
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);
    User save(UserDto user) throws AlreadyExistsException;
    User update(User user);
	User updateUserRoles(User user);
	User updateUserPassword(User user);
}
