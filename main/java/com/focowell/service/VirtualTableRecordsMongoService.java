package com.focowell.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dto.VirtualTableRecordForGridDto;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.FormMaster;
import com.focowell.model.VirtualRowRecord;
import com.focowell.model.VirtualTableRecords;

import javassist.NotFoundException;

public interface VirtualTableRecordsMongoService {
	    List<VirtualRowRecord> findAll();
	    VirtualRowRecord findAllRowsByTableAndPk(long tableId, long pkValue);
	    VirtualRowRecord findRowByTableAndPk(long tableId, long pkValue);
		List<VirtualRowRecord> findAllRowsByTable(long tableId);
		Set<FormComponentRefValue> findAllFormComponentRefValueByTableAndFields(long tableId, String key,
				String value);
		VirtualTableRecordForGridDto findAllByTableForGrid(long tableId);
	
	    void deleteByPkAndTable(long tableId, long pkId);
	    VirtualRowRecord saveOneRowRecordAfterCheckPkValue(VirtualRowRecord virtualRowRecordsDto) throws Exception;
		VirtualRowRecord saveVirtualRecordsFromForm(FormMaster form) throws Exception;
		List<VirtualRowRecord> saveAllRowRecords(List<VirtualRowRecord> virtualRowRecords);
	    
	    
	    VirtualRowRecord update(VirtualRowRecord virtualRowRecordsDto) throws NotFoundException;
	    VirtualRowRecord updateVirtualRecordsFromForm(FormMaster form, long pkValue) throws NotFoundException;
				
}
