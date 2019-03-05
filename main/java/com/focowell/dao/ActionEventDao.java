package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.ActionEvent;


@Repository
public interface ActionEventDao extends CrudRepository<ActionEvent, Long> {
	List<ActionEvent> findByActionName(String actionName);
	
//	@Query("SELECT a FROM ActionEvent  ")
//	Iterable<ActionEvent> findAllByJPQL();
}
