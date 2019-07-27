package com.focowell.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.FormMaster;


@Repository
public interface FormMasterDao extends CrudRepository<FormMaster, Long> {
	List<FormMaster> findByFormName(String formName);
	
	@Query("SELECT v FROM FormMaster v left join fetch v.virtualTableMaster  ")
	Iterable<FormMaster> findAllByJPQL();
	
	@Query("SELECT v FROM FormMaster v left join fetch v.virtualTableMaster t where t.process.id=:processId ")
	Iterable<FormMaster> findAllByProcessJPQL(@Param("processId") long processId);
	
	@Query("SELECT v FROM FormMaster v left join fetch v.virtualTableMaster where v.id=:id ")
	Optional<FormMaster> findByIdJPQL(@Param("id") long id);
	
}
