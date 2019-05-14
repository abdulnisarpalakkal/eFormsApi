package com.focowell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.focowell.config.JwtTokenUtil;
import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.AuthToken;
import com.focowell.model.Constants;
import com.focowell.model.LoginUser;
import com.focowell.model.StaticData;
import com.focowell.model.User;
import com.focowell.model.dto.UserDto;
import com.focowell.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

    	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();     // get request object
    	attr.setAttribute(Constants.TENANT_ID, loginUser.getTenantId(),RequestAttributes.SCOPE_REQUEST);
    	
    	UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());
    	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, loginUser.getPassword(), userDetails.getAuthorities());
    	
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(userDetails,loginUser.getTenantId());
        return ResponseEntity.ok(new AuthToken(token));
    }
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDto user) throws AlreadyExistsException{
    	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();     // get request object
    	attr.setAttribute(Constants.TENANT_ID, user.getTenantId(),RequestAttributes.SCOPE_REQUEST);
    	return userService.save(user);
    }

}
