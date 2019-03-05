package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.VirtualTableSequenceDao;
import com.focowell.model.VirtualTableSequence;
import com.focowell.service.VirtualTableSequenceService;

@Service(value = "virtualTableSequenceService")
public class VirtualTableSequenceServiceImpl implements VirtualTableSequenceService {

	@Autowired
	private VirtualTableSequenceDao virtualTableSequenceDao;
	
	@Override
	public List<VirtualTableSequence> findAll() {
		List<VirtualTableSequence> list = new ArrayList<>();
		virtualTableSequenceDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		virtualTableSequenceDao.deleteById(id);
		
	}

	@Override
	public VirtualTableSequence findOne(String virtualTableSequenceName) {
		Optional<VirtualTableSequence> opSeq=virtualTableSequenceDao.findBySequenceName(virtualTableSequenceName);
		if(opSeq.isPresent())
			return opSeq.get();
		return null;
		
	}

	@Override
	public VirtualTableSequence findById(Long id) {
		return virtualTableSequenceDao.findById(id).get();
	}

	@Override
	public VirtualTableSequence save(VirtualTableSequence virtualTableSequence) throws AlreadyExistsException {
		if(virtualTableSequenceExist(virtualTableSequence.getSequenceName()))
		{
			throw new AlreadyExistsException(
		              "There is an VirtualTableSequence with the same name "
		              +  virtualTableSequence.getSequenceName() );
		}
		return virtualTableSequenceDao.save(virtualTableSequence);
	}

	@Override
	public VirtualTableSequence update(VirtualTableSequence virtualTableSequence) {
		VirtualTableSequence updateVirtualTableSequence =virtualTableSequenceDao.findById(virtualTableSequence.getId()).get();
				
		updateVirtualTableSequence.setSequenceName(virtualTableSequence.getSequenceName());
		updateVirtualTableSequence.setLastValue(virtualTableSequence.getLastValue());
					
        return virtualTableSequenceDao.save(updateVirtualTableSequence);
	}
	@Override
	public long getNextSeq(VirtualTableSequence virtualTableSequence) {
		VirtualTableSequence updateVirtualTableSequence =virtualTableSequenceDao.findById(virtualTableSequence.getId()).get();
		updateVirtualTableSequence.setLastValue(updateVirtualTableSequence.getLastValue()+1);
		virtualTableSequenceDao.save(updateVirtualTableSequence);			
        return updateVirtualTableSequence.getLastValue();
	}
	
	@Override
	public long getNextSeqByName(String sequenceName) {
		VirtualTableSequence updateVirtualTableSequence =findOne(sequenceName);
		updateVirtualTableSequence.setLastValue(updateVirtualTableSequence.getLastValue()+1);
					
        return virtualTableSequenceDao.save(updateVirtualTableSequence).getLastValue();
	}
	
	private boolean virtualTableSequenceExist(String virtualTableSequenceName) {
        if(virtualTableSequenceDao.findBySequenceName(virtualTableSequenceName).isPresent())
        	return true;
        return false;
    }

}
