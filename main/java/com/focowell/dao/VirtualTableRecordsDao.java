package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.VirtualTableRecords;


@Repository
public interface VirtualTableRecordsDao extends CrudRepository<VirtualTableRecords, Long> {
	List<VirtualTableRecords> findByPkValue(long pkVlaue);
	
	@Query("SELECT record FROM VirtualTableRecords record inner join fetch record.virtualTableFields field inner join fetch field.virtualTableMaster table "
			+ "  where table.id=:tableId order by record.pkValue ")
	List<VirtualTableRecords> findAllByTableJPQL(@Param("tableId") long tableId);
	
	@Query("SELECT record FROM VirtualTableRecords record inner join fetch record.virtualTableFields field inner join fetch field.virtualTableMaster table "
			+ "  where table.id=:tableId and record.pkValue=:pkVlaue")
	List<VirtualTableRecords> findAllByTableAndPkValueJPQL(@Param("tableId") long tableId,@Param("pkVlaue") long pkVlaue);
	
	
//	@Query("SELECT a FROM VirtualTableRecords  ")
//	Iterable<VirtualTableRecords> findAllByJPQL();
}
