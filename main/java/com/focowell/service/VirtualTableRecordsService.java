package com.focowell.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.FormMaster;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.dto.VirtualTableRecordForGridDto;

public interface VirtualTableRecordsService {
	    List<VirtualTableRecords> findAll();
	    void delete(long id);
	    void deleteByPkAndTable(long tableId, long pkId);
	    VirtualTableRecords 
	    findOne(String virtualTableRecordsName);

	    VirtualTableRecords findById(Long id);
	    VirtualTableRecords save(VirtualTableRecords virtualTableRecords) throws AlreadyExistsException;
	    VirtualTableRecords update(VirtualTableRecords virtualTableRecords);
		List<VirtualTableRecords> saveAll(Set<VirtualTableRecords> virtualTableRecords);
		List<Map> findAllByTable(long tableId);
		VirtualTableRecordForGridDto findAllByTableForGrid(long tableId);
		Set<FormComponentRefValue> findAllFormComponentRefValueByTableAndFields(long tableId, String key,
				String value);
		Set<VirtualTableRecords> findAllByTableAndPk(long tableId, long pkValue);
		List<Map> findAllRecordsByTable(long tableId);
		List<VirtualTableRecords> saveOneRowRecordAfterCheckPkValue(List<VirtualTableRecords> records) throws Exception;
		long saveVirtualRecordsFromForm(FormMaster form) throws Exception;
		long updateVirtualRecordsFromForm(FormMaster form, long pkValue);
		
}
