package com.focowell.service;

import java.util.List;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.model.VirtualTableSequence;;

public interface VirtualTableSequenceService {
	    List<VirtualTableSequence> findAll();
	    void delete(long id);
	    VirtualTableSequence findOne(String sequenceName);

	    VirtualTableSequence findById(Long id);
	    VirtualTableSequence save(VirtualTableSequence sequence) throws AlreadyExistsException;
	    VirtualTableSequence update(VirtualTableSequence sequence);
		long getNextSeq(VirtualTableSequence virtualTableSequence);
		long getNextSeqByName(String sequenceName);
}
