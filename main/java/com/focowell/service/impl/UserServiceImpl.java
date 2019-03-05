package com.focowell.service.impl;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.UserDao;
import com.focowell.dao.UserRolesDao;
import com.focowell.model.User;
import com.focowell.model.UserRoleType;
import com.focowell.model.UserRoles;
import com.focowell.model.dto.UserDto;
import com.focowell.service.UserRolesService;
import com.focowell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRolesService userRolesService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userDao.findByUsername(userId);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getUserRoles()));
	}

	private List<SimpleGrantedAuthority> getAuthority(Set<UserRoles> roles) {
		List<SimpleGrantedAuthority> authorityList=new ArrayList<SimpleGrantedAuthority>();
		for(UserRoles role : roles)
		{
			authorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getType()));
		}
		return authorityList;
//		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userDao.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id).get();
	}

	@Override
    public User save(UserDto user) throws AlreadyExistsException {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		Set<UserRoles> roles=new HashSet<UserRoles>();
		UserRoles role=userRolesService.findOne(UserRoleType.USER.toString());
		roles.add(role);
		newUser.setUserRoles(roles);
		if(userExist(newUser.getUsername(),newUser.getEmail()))
		{
			throw new AlreadyExistsException(
		              "There is an account with the same user name "
		              +  newUser.getUsername()+ " or mail account "+newUser.getEmail() );
		}
			
        return userDao.save(newUser);
    }
	@Override
    public User update(User user)  {
		User updateUser =userDao.findById(user.getId()).get();
		
		
		updateUser.setFirstName(user.getFirstName());
		updateUser.setLastName(user.getLastName());
		updateUser.setUsername(user.getUsername());
		updateUser.setEmail(user.getEmail());
		
			
        return userDao.save(updateUser);
    }
	
	@Override
    public User updateUserRoles(User user)  {
		User updateUser =userDao.findById(user.getId()).get();
		updateUser.setUserRoles(user.getUserRoles());
					
        return userDao.save(updateUser);
    }
	
	@Override
    public User updateUserPassword(User user)  {
		User updateUser =userDao.findById(user.getId()).get();
		updateUser.setPassword(bcryptEncoder.encode(user.getPassword()));
					
        return userDao.save(updateUser);
    }
	private boolean userExist(String username,String email) {
        List<User> users = userDao.findByUsernameOrEmail(username, email);
        if (users != null && users.size()!=0 ) {
            return true;
        }
        return false;
    }
	private boolean emailExist(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
