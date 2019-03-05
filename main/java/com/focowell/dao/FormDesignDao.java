package com.focowell.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.FormDesign;


@Repository
public interface FormDesignDao extends CrudRepository<FormDesign, Long> {
	List<FormDesign> findByComponentName(String componentName);
	
	@Query("SELECT v FROM FormDesign v left join fetch v.formMaster left join fetch v.virtualTableField ")
	Iterable<FormDesign> findAllByJPQL();
	
	@Query("SELECT v FROM FormDesign v left join fetch v.formMaster left join fetch v.virtualTableField where id=:id ")
	Optional<FormDesign> findByIdJPQL(@Param("id") long id);
	
	@Query("SELECT v FROM FormDesign v left join fetch v.formMaster f left join fetch v.virtualTableField where f.id=:formId ")
	Iterable<FormDesign> findAllByFormIdJPQL(@Param("formId") long formId);
	
}
