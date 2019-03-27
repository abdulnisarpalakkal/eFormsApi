package com.focowell.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.VirtualTableMaster;


@Repository
public interface VirtualTableMasterDao extends CrudRepository<VirtualTableMaster, Long> {
	List<VirtualTableMaster> findByTableName(String tableName);
	
	@Query("SELECT v FROM VirtualTableMaster v left join fetch v.process ")
	Iterable<VirtualTableMaster> findAllByJPQL();
	
	@Query("SELECT v FROM VirtualTableMaster v left join fetch v.process p  where id=:id ")
	Optional<VirtualTableMaster> findByIdJPQL(@Param("id") long id);
	
	@Query("SELECT v FROM VirtualTableMaster v left join fetch v.process p where p.id=:processId ")
	Iterable<VirtualTableMaster> findAllByProcessJPQL(@Param("processId") long processId);
	
	Iterable<VirtualTableMaster> findAllByProcess_Id(long processId);
	
}
