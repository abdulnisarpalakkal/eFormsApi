package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.UserRolesDao;
import com.focowell.model.UserRoles;
import com.focowell.service.UserRolesService;

@Service(value = "userRolesService")
public class UserRolesServiceImpl implements UserRolesService {

	@Autowired
	private UserRolesDao userRolesDao;
	
	@Override
	public List<UserRoles> findAll() {
		List<UserRoles> list = new ArrayList<>();
		userRolesDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userRolesDao.deleteById(id);
		
	}

	@Override
	public UserRoles findOne(String roleType) {
		return userRolesDao.findByType(roleType).get(0);
	}

	@Override
	public UserRoles findById(Long id) {
		return userRolesDao.findById(id).get();
	}

	@Override
	public UserRoles save(UserRoles userRoles) throws AlreadyExistsException {
		if(userRolesExist(userRoles.getType()))
		{
			throw new AlreadyExistsException(
		              "There is an UserRoles with the same name "
		              +  userRoles.getType() );
		}
		return userRolesDao.save(userRoles);
	}

	@Override
	public UserRoles update(UserRoles userRoles) {
		UserRoles updateUserRoles =userRolesDao.findById(userRoles.getId()).get();
				
		updateUserRoles.setType(userRoles.getType());
		updateUserRoles.setRoleDesc(userRoles.getRoleDesc());
					
        return userRolesDao.save(updateUserRoles);
	}
	
	private boolean userRolesExist(String userRolesName) {
        List<UserRoles> categories = userRolesDao.findByType(userRolesName);
        if (categories != null && categories.size()!=0 ) {
            return true;
        }
        return false;
    }

}
