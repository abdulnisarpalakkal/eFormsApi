package com.focowell.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.FormRuleParameterValue;


@Repository
public interface FormRuleParameterValueDao extends CrudRepository<FormRuleParameterValue, Long> {

	Optional<FormRuleParameterValue> findByFormRuleTypeParameter_ParameterName(String parameterName);
	
}
