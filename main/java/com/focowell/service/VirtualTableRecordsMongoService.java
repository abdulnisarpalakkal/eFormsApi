package com.focowell.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.FormMaster;
import com.focowell.model.VirtualTableRecords;
import com.focowell.model.dto.VirtualRowRecordsDto;
import com.focowell.model.dto.VirtualTableRecordForGridDto;

import javassist.NotFoundException;

public interface VirtualTableRecordsMongoService {
	    List<VirtualRowRecordsDto> findAll();
	    VirtualRowRecordsDto findAllRowsByTableAndPk(long tableId, long pkValue);
	    VirtualRowRecordsDto findRowByTableAndPk(long tableId, long pkValue);
		List<VirtualRowRecordsDto> findAllRowsByTable(long tableId);
		Set<FormComponentRefValue> findAllFormComponentRefValueByTableAndFields(long tableId, String key,
				String value);
		VirtualTableRecordForGridDto findAllByTableForGrid(long tableId);
	
	    void deleteByPkAndTable(long tableId, long pkId);
	    VirtualRowRecordsDto saveOneRowRecordAfterCheckPkValue(VirtualRowRecordsDto virtualRowRecordsDto) throws Exception;
		VirtualRowRecordsDto saveVirtualRecordsFromForm(FormMaster form) throws Exception;
		List<VirtualRowRecordsDto> saveAllRowRecords(List<VirtualRowRecordsDto> virtualRowRecords);
	    
	    
	    VirtualRowRecordsDto update(VirtualRowRecordsDto virtualRowRecordsDto) throws NotFoundException;
	    VirtualRowRecordsDto updateVirtualRecordsFromForm(FormMaster form, long pkValue) throws NotFoundException;
				
}
