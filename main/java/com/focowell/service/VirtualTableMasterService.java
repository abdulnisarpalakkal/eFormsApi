package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.VirtualTableMaster;
import com.focowell.model.dto.VirtualTableFKConstraintRefDto;
import com.focowell.model.dto.VirtualTableFieldsConstraintDto;

public interface VirtualTableMasterService {
	    List<VirtualTableMaster> findAll();
	    List<VirtualTableMaster> findAllByProcessId(long processId);
	    void delete(long id);
	    VirtualTableMaster findOne(String virtualTableMasterName);

	    VirtualTableMaster findById(Long id);
	    VirtualTableMaster save(VirtualTableMaster virtualTableMaster) throws AlreadyExistsException;
	    VirtualTableMaster update(VirtualTableMaster virtualTableMaster) throws AlreadyExistsException;
		VirtualTableFKConstraintRefDto findVirtualTableConstraintRefData(long processId);
		VirtualTableMaster save(VirtualTableFieldsConstraintDto virtualTableFieldsConstraintDto)
				throws AlreadyExistsException;
		VirtualTableMaster update(VirtualTableFieldsConstraintDto virtualTableFieldsConstraintDto)
				throws AlreadyExistsException;
		List<VirtualTableMaster> findAllTablesReferringMe(long tableId);
}
