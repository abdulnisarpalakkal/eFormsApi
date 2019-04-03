package com.focowell.service;

import java.util.List;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.VirtualTableFieldDataType;
import com.focowell.model.VirtualTableField;

public interface VirtualTableFieldsService {
	    List<VirtualTableField> findAll();
	    void delete(long id);
	    VirtualTableField findOne(String virtualTableFieldsName);

	    VirtualTableField findById(Long id);
	    VirtualTableField save(VirtualTableField virtualTableFields) throws AlreadyExistsException;
	    List<VirtualTableField> saveAll(Set<VirtualTableField> virtualTableFieldsList);
	    VirtualTableField update(VirtualTableField virtualTableFields);
	    List<VirtualTableFieldDataType> findAllVirtualTableFieldDataTypes();
		List<VirtualTableField> findAllByTableId(long tableId);
		List<String> findAllFieldNamesByTableId(long tableId);
		
		
}
