package com.focowell.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.FormRuleTypeParameter;


@Repository
public interface FormRuleTypeParameterDao extends CrudRepository<FormRuleTypeParameter, Long> {
	Optional<FormRuleTypeParameter> findByParameterName(String formRuleTypeParameterName);
	
}
