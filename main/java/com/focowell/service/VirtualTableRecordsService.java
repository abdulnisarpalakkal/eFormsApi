package com.focowell.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.dto.VirtualTableRecordForGridDto;

public interface VirtualTableRecordsService {
	    List<VirtualTableRecords> findAll();
	    void delete(long id);
	    VirtualTableRecords 
	    findOne(String virtualTableRecordsName);

	    VirtualTableRecords findById(Long id);
	    VirtualTableRecords save(VirtualTableRecords virtualTableRecords) throws AlreadyExistsException;
	    VirtualTableRecords update(VirtualTableRecords virtualTableRecords);
		List<VirtualTableRecords> saveAll(Set<VirtualTableRecords> virtualTableRecords);
		Set<VirtualTableRecords> findAllByPk(long pkId);
		List<Map> findAllByTable(long tableId);
		VirtualTableRecordForGridDto findAllByTableForGrid(long tableId);
}
