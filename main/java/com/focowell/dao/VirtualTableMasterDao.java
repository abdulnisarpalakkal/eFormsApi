package com.focowell.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableMaster;


@Repository
public interface VirtualTableMasterDao extends CrudRepository<VirtualTableMaster, Long> {
	List<VirtualTableMaster> findByTableName(String tableName);
	
	@Query("SELECT v FROM VirtualTableMaster v left join fetch v.process ")
	Iterable<VirtualTableMaster> findAllByJPQL();
	
	@Query("SELECT v FROM VirtualTableMaster v left join fetch v.process p  where v.id=:id ")
	Optional<VirtualTableMaster> findByIdJPQL(@Param("id") long id);
	
	@Query("SELECT v FROM VirtualTableMaster v left join fetch v.process p where p.id=:processId ")
	Iterable<VirtualTableMaster> findAllByProcessJPQL(@Param("processId") long processId);
	
	Iterable<VirtualTableMaster> findAllByProcess_Id(long processId);
	
	@Query("SELECT v FROM VirtualTableMaster v left join fetch v.process p  left join fetch v.virtualTableFieldsList f "
			+ " left join fetch f.fieldConstraintList c left join fetch c.foreignConstraint fConstraint "
			+ "left join fetch fConstraint.virtualTableField fField left join fetch fField.virtualTableMaster fTable where c.constraintType=:constraintType "
			+ " and fTable.id=:tableId ")
	Iterable<VirtualTableMaster> findAllTablesReferringMeJPQL(@Param("constraintType") VirtualTableConstraintType constraintType, @Param("tableId") long tableId);
}
