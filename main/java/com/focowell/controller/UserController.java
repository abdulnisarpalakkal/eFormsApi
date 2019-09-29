package com.focowell.controller;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.UserDto;
import com.focowell.model.User;
import com.focowell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteOne(@PathVariable(value = "id") Long id){
        userService.delete(id);
        
        
    }
    @RequestMapping(value="/users", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) throws AlreadyExistsException{
    	return userService.update(user);
    }
    @RequestMapping(value="/users/userRoles", method = RequestMethod.PUT)
    public User updateUserRoles(@RequestBody User user) {
    	return userService.updateUserRoles(user);
    }
    
    @RequestMapping(value="/users/password", method = RequestMethod.PUT)
    public User updateUserPassword(@RequestBody User user) {
    	return userService.updateUserPassword(user);
    }
    
}
