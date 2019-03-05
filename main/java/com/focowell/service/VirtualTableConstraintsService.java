package com.focowell.service;

import java.util.List;
import java.util.Set;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.VirtualTableConstraints;

public interface VirtualTableConstraintsService {
	    List<VirtualTableConstraints> findAll(long tableId);
	    void delete(long id);
	    
	    VirtualTableConstraints findById(Long id);
	    VirtualTableConstraints save(VirtualTableConstraints constraint) ;
		List<VirtualTableConstraints> findAllByVirtualTableFieldByProcess(long processId);
		List<VirtualTableConstraints> saveAll(Set<VirtualTableConstraints> virtualTableConstraints);
		List<VirtualTableConstraints> findAllByTableId(long tableId);
	    
		
}
