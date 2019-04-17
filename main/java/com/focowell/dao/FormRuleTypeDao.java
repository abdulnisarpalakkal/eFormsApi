package com.focowell.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.FormRuleType;

@Repository
public interface FormRuleTypeDao extends CrudRepository<FormRuleType, Long> {
	
	Optional<FormRuleType> findByRuleTypeName(String ruleTypeName);
}
