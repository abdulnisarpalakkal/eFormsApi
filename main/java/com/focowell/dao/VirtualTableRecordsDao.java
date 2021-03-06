package com.focowell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.VirtualTableRecords;


@Repository
public interface VirtualTableRecordsDao extends CrudRepository<VirtualTableRecords, Long> {
	List<VirtualTableRecords> findByPkValue(long pkVlaue);
	
//	@Query("SELECT a FROM VirtualTableRecords  ")
//	Iterable<VirtualTableRecords> findAllByJPQL();
}
