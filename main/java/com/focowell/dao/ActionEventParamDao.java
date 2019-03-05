package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.ActionEventParam;




@Repository
public interface ActionEventParamDao extends CrudRepository<ActionEventParam, Long> {
	List<ActionEventParam> findByParamName(String paramName);
	
	@Query("SELECT p FROM ActionEventParam p  ")
	Iterable<ActionEventParam> findAllByJPQL();
}