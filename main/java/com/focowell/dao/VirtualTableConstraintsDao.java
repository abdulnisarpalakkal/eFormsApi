package com.focowell.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.VirtualTableConstraints;

@Repository
public interface VirtualTableConstraintsDao extends CrudRepository<VirtualTableConstraints, Long> {
	@Query("SELECT v FROM VirtualTableConstraints v left join fetch v.virtualTableField  where v.virtualTableField.virtualTableMaster.id=:id")
	Iterable<VirtualTableConstraints> findAllByTableIdJPQL(@Param("id") long id);
	
	Iterable<VirtualTableConstraints> findAllByVirtualTableField_VirtualTableMaster_Process_id( long processId);
	
	@Query("SELECT v FROM VirtualTableConstraints v left join fetch v.virtualTableField f  where f.virtualTableMaster.process.id=:processId")
	Iterable<VirtualTableConstraints> findAllByVirtualTableFieldConstraintsByProcess_id(@Param("processId") long processId);
	
//	@Modifying
//	@Query("delete FROM VirtualTableConstraints v left join fetch v.virtualTableField f where f.virtualTableMaster.id=:tableId ")
//	void deleteByTableId(@Param("tableId") long tableId);
	@Modifying
	@Query("delete FROM VirtualTableConstraints v  where v.virtualTableField.virtualTableMaster.id=:tableId ")
	void deleteByTableId(@Param("tableId") long tableId);
}
