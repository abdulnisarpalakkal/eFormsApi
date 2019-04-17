package com.focowell.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.FormRule;


@Repository
public interface FormRuleDao extends CrudRepository<FormRule, Long> {

	Optional<FormRule> findByFormRuleName(String formRuleName);
	
	Iterable<FormRule> findByFormDesigns_FormMaster_Id(long formId);
	Iterable<FormRule> findByFormMaster_Id(long formId);
	
}
