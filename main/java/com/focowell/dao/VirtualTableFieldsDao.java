package com.focowell.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.focowell.model.VirtualTableField;


@Repository
public interface VirtualTableFieldsDao extends CrudRepository<VirtualTableField, Long> {
	@Query("SELECT v FROM VirtualTableField v left join fetch v.virtualTableMaster where v.fieldName=:fieldName and v.virtualTableMaster.tableName=:tableName")
	List<VirtualTableField> findByFieldNameAndTable(@Param("fieldName") String fieldName, @Param("tableName") String tableName);
	
	List<VirtualTableField> findByFieldName(String fieldName);
	
	@Query("SELECT v FROM VirtualTableField v left join fetch v.virtualTableMaster ")
	Iterable<VirtualTableField> findAllByJPQL();
	
	@Query("SELECT v FROM VirtualTableField v left join fetch v.virtualTableMaster  where v.virtualTableMaster.id=:id order by v.fieldName")
	Iterable<VirtualTableField> findAllByTableIdJPQL(@Param("id") long id);
	
	@Query("SELECT v FROM VirtualTableField v left join fetch v.virtualTableMaster  where v.virtualTableMaster.id=:id")
	Iterable<VirtualTableField> findAllFieldNamesByTableIdJPQL(@Param("id") long id);
	
}
