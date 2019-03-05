package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.VirtualTableFieldsDao;
import com.focowell.model.VirtualTableFieldDataType;
import com.focowell.model.VirtualTableField;
import com.focowell.service.VirtualTableFieldsService;

@Service(value = "virtualTableFieldsService")
public class VirtualTableFieldsServiceImpl implements VirtualTableFieldsService {

	@Autowired
	private VirtualTableFieldsDao virtualTableFieldsDao;
	
	@Override
	public List<VirtualTableField> findAll() {
		List<VirtualTableField> list = new ArrayList<>();
		virtualTableFieldsDao.findAllByJPQL().iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public List<VirtualTableField> findAllByTableId(long tableId) {
		List<VirtualTableField> list = new ArrayList<>();
		virtualTableFieldsDao.findAllByTableIdJPQL(tableId).iterator().forEachRemaining(list::add);
		return list;
	}
	

	@Override
	public void delete(long id) {
		virtualTableFieldsDao.deleteById(id);
		
	}

	@Override
	public VirtualTableField findOne(String virtualTableFieldName) {
		return virtualTableFieldsDao.findByFieldName(virtualTableFieldName).get(0);
	}

	@Override
	public VirtualTableField findById(Long id) {
		return virtualTableFieldsDao.findById(id).get();
	}

	@Override
	public VirtualTableField save(VirtualTableField virtualTableFields) throws AlreadyExistsException {
		if(virtualTableFieldsExist(virtualTableFields.getFieldName(),virtualTableFields.getVirtualTableMaster().getTableName()))
		{
			throw new AlreadyExistsException(
		              "There is an field exists with the same name "
		              +  virtualTableFields.getFieldName() );
		}
		if(virtualTableFields.getFieldConstraintList()!=null && !virtualTableFields.getFieldConstraintList().isEmpty())
			virtualTableFields.getFieldConstraintList().forEach(u -> u.setVirtualTableField(virtualTableFields));
		return virtualTableFieldsDao.save(virtualTableFields);
		
				
	}
	@Override
	public List<VirtualTableField> saveAll(Set<VirtualTableField> virtualTableFieldsList)  {
		List<VirtualTableField> list = new ArrayList<>();
		virtualTableFieldsDao.saveAll(virtualTableFieldsList).iterator().forEachRemaining(list::add);
		
		return list;
				
	}

	@Override
	public VirtualTableField update(VirtualTableField virtualTableFields) {
		VirtualTableField updateVirtualTableFields =virtualTableFieldsDao.findById(virtualTableFields.getId()).get();
				
		updateVirtualTableFields.setFieldName(virtualTableFields.getFieldName());
		updateVirtualTableFields.setFieldDataType(virtualTableFields.getFieldDataType());
		updateVirtualTableFields.setFieldDesc(virtualTableFields.getFieldDesc());
		
		
		if(virtualTableFields.getFieldConstraintList()!=null && !virtualTableFields.getFieldConstraintList().isEmpty()) {
			updateVirtualTableFields.setFieldConstraintList(virtualTableFields.getFieldConstraintList());
			updateVirtualTableFields.getFieldConstraintList().forEach(u -> u.setVirtualTableField(updateVirtualTableFields));
		}
        return virtualTableFieldsDao.save(updateVirtualTableFields);
	}
	
	private boolean virtualTableFieldsExist(String fieldName,String tableName	) {
        List<VirtualTableField> virtualTableFields = virtualTableFieldsDao.findByFieldNameAndTable(fieldName,tableName);
        if (virtualTableFields != null && virtualTableFields.size()!=0 ) {
            return true;
        }
        return false;
    }
	@Override
	public List<VirtualTableFieldDataType> findAllVirtualTableFieldDataTypes() {
		
		List<VirtualTableFieldDataType> list = Arrays.asList(VirtualTableFieldDataType.values());
		return list;
	}
	

}
