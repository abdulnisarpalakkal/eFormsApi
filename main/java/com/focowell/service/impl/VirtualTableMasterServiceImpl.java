package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.VirtualTableMasterDao;
import com.focowell.dto.VirtualTableFKConstraintRefDto;
import com.focowell.dto.VirtualTableFieldsConstraintDto;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableMaster;
import com.focowell.model.VirtualTableSequence;
import com.focowell.service.VirtualTableConstraintsService;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableMasterService;

@Service(value = "virtualTableMasterService")
public class VirtualTableMasterServiceImpl implements VirtualTableMasterService {

	@Autowired
	private VirtualTableMasterDao virtualTableMasterDao;
	
	@Autowired
    private VirtualTableConstraintsService virtualTableConstraintsService;
	
	@Autowired
    private VirtualTableFieldsService virtualTableFieldsService;
	
	@Override
	public List<VirtualTableMaster> findAll() {
		List<VirtualTableMaster> list = new ArrayList<>();
		virtualTableMasterDao.findAllByJPQL().iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<VirtualTableMaster> findAllByProcessId(long processId) {
		List<VirtualTableMaster> list = new ArrayList<>();
		virtualTableMasterDao.findAllByProcessJPQL(processId).iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public void delete(long id) {
		virtualTableMasterDao.deleteById(id);
		
	}

	@Override
	public VirtualTableMaster findOne(String virtualTableMasterName) {
		return virtualTableMasterDao.findByTableName(virtualTableMasterName).get(0);
	}

	@Override
	public VirtualTableMaster findById(Long id) {
		return virtualTableMasterDao.findByIdJPQL(id).get();
	}

	/*
	 * this for listing all tables in same process for setting foreign key
	 */
	@Override
	public VirtualTableFKConstraintRefDto findVirtualTableConstraintRefData(long processId) {
		VirtualTableFKConstraintRefDto virtualTableConstraintRefDto=new VirtualTableFKConstraintRefDto();
		List<VirtualTableMaster> refTables=new ArrayList<VirtualTableMaster>();
		List<VirtualTableConstraints> refPkConstraints=new  ArrayList<VirtualTableConstraints>();
		
		virtualTableMasterDao.findAllByProcess_Id(processId).iterator().forEachRemaining(refTables::add);
		virtualTableConstraintsService.findAllByVirtualTableFieldByProcess(processId).iterator().forEachRemaining(refPkConstraints::add);
		
		virtualTableConstraintRefDto.setRefPkConstraints(refPkConstraints);
		virtualTableConstraintRefDto.setRefTables(refTables);
		
		return virtualTableConstraintRefDto;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.focowell.service.VirtualTableMasterService#save(com.focowell.model.VirtualTableMaster)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VirtualTableMaster> findAllTablesReferringMe(long tableId){
		List<VirtualTableMaster> referTablelist = new ArrayList<>();
		virtualTableMasterDao.findAllTablesReferringMeJPQL(VirtualTableConstraintType.FOREIGN_KEY,tableId).iterator().forEachRemaining(referTablelist::add);
		referTablelist.forEach(referTable->referTable.setVirtualTableFieldsList(new HashSet<VirtualTableField>(virtualTableFieldsService.findAllByTableId(referTable.getId()))));
		return referTablelist;
		
	}
	@Override
	public VirtualTableMaster save(VirtualTableMaster virtualTableMaster) throws AlreadyExistsException {
		if(virtualTableMasterExist(virtualTableMaster.getTableName()))
		{
			throw new AlreadyExistsException(
		              "There is an VirtualTableMaster with the same name "
		              +  virtualTableMaster.getTableName() );
		}
		if(virtualTableMaster.getVirtualTableFieldsList()!=null)
		{
			virtualTableMaster.getVirtualTableFieldsList().forEach((u) ->{
				u.setVirtualTableMaster(virtualTableMaster);
				if(u.getFieldConstraintList()!=null && !u.getFieldConstraintList().isEmpty())
					u.getFieldConstraintList().forEach(c->c.setVirtualTableField(u));
			});
			
		}
		VirtualTableSequence sequence=new VirtualTableSequence();
		sequence.setSequenceName(virtualTableMaster.getTableName()+"_seq");
		sequence.setVirtualTableMaster(virtualTableMaster);
		virtualTableMaster.setVirtualTableSequence(sequence);
		
		VirtualTableMaster resVirtualTableMaster=virtualTableMasterDao.save(virtualTableMaster);
		resVirtualTableMaster.setVirtualTableFieldsList(null);	
		return resVirtualTableMaster;
		
		
	}

	@Override
	@Transactional
	public VirtualTableMaster update(VirtualTableMaster virtualTableMaster) throws AlreadyExistsException {
		VirtualTableMaster updateVirtualTableMaster =virtualTableMasterDao.findById(virtualTableMaster.getId()).get();
				
		updateVirtualTableMaster.setTableName(virtualTableMaster.getTableName());
		updateVirtualTableMaster.setTableDesc(virtualTableMaster.getTableDesc());
		updateVirtualTableMaster.setAutoIncr(virtualTableMaster.isAutoIncr());
		if(virtualTableMaster.getVirtualTableFieldsList()!=null && !virtualTableMaster.getVirtualTableFieldsList().isEmpty() )
			for (VirtualTableField virtualTableFields : virtualTableMaster.getVirtualTableFieldsList()) {
//				virtualTableFields.setFieldConstraintList(virtualTableFields.getFieldConstraintList()!=null && virtualTableFields.getFieldConstraintList().isEmpty()?
//						null:virtualTableFields.getFieldConstraintList());
				if(virtualTableFields.isDeleted())
				{
					virtualTableFieldsService.delete(virtualTableFields.getId());
					continue;
				}
				if(virtualTableFields.getId()!=0)
					virtualTableFieldsService.update(virtualTableFields);
				else
				{
					virtualTableFields.setVirtualTableMaster(updateVirtualTableMaster);
					virtualTableFieldsService.save(virtualTableFields);
				}
						
			}
		if(updateVirtualTableMaster.getVirtualTableSequence()==null)
		{
			VirtualTableSequence sequence=new VirtualTableSequence();
			sequence.setSequenceName(virtualTableMaster.getTableName()+"_seq");
			sequence.setVirtualTableMaster(updateVirtualTableMaster);
			updateVirtualTableMaster.setVirtualTableSequence(sequence);
		}
        return virtualTableMasterDao.save(updateVirtualTableMaster);
	}
	
	private boolean virtualTableMasterExist(String tableName	) {
        List<VirtualTableMaster> categories = virtualTableMasterDao.findByTableName(tableName);
        if (categories != null && categories.size()!=0 ) {
            return true;
        }
        return false;
    }
	
	
	@Override
	@Transactional
	public VirtualTableMaster save(VirtualTableFieldsConstraintDto virtualTableFieldsConstraintDto) throws AlreadyExistsException {
		VirtualTableMaster virtualTableMaster=virtualTableFieldsConstraintDto.getVirtualTable();
		if(virtualTableMasterExist(virtualTableMaster.getTableName()))
		{
			throw new AlreadyExistsException(
		              "There is an VirtualTableMaster with the same name "
		              +  virtualTableMaster.getTableName() );
		}
		virtualTableMaster.setVirtualTableFieldsList(virtualTableFieldsConstraintDto.getVirtualTableFields());
		if(virtualTableMaster.getVirtualTableFieldsList()!=null) //setting bidirectional relationship b/w table and table fields
		{
			virtualTableMaster.getVirtualTableFieldsList().forEach((u) ->{
				u.setVirtualTableMaster(virtualTableMaster);
				
			});
			
		}
		VirtualTableSequence sequence=new VirtualTableSequence(); //setting bidirectional relationship between sequence and table
		sequence.setSequenceName(virtualTableMaster.getTableName()+"_seq");
		sequence.setVirtualTableMaster(virtualTableMaster);
		virtualTableMaster.setVirtualTableSequence(sequence);
		
		
		
		
		VirtualTableMaster resVirtualTableMaster=virtualTableMasterDao.save(virtualTableMaster);
		
		
		//setting bidirectional relationship b/w pk constraint and field
        Set<VirtualTableConstraints> constraints=new HashSet<VirtualTableConstraints>();
        
		if(virtualTableFieldsConstraintDto.getPkConstraint()!=null && virtualTableFieldsConstraintDto.getPkConstraint().getVirtualTableField()!=null) {
			VirtualTableField field= resVirtualTableMaster.getVirtualTableFieldsList().stream()
					.filter(f->f.getFieldName().equals(virtualTableFieldsConstraintDto.getPkConstraint().getVirtualTableField().getFieldName())).findFirst().orElse(null);
			if(field.getFieldConstraintList()==null)
				field.setFieldConstraintList(new HashSet<VirtualTableConstraints>());
			
			field.getFieldConstraintList().add(virtualTableFieldsConstraintDto.getPkConstraint());
			virtualTableFieldsConstraintDto.getPkConstraint().setVirtualTableField(field);
			constraints.add(virtualTableFieldsConstraintDto.getPkConstraint());
			
		}
		//setting bidirectional relationship b/w fk constraint and field
		if(virtualTableFieldsConstraintDto.getFkConstraints()!=null ) {
			virtualTableFieldsConstraintDto.getFkConstraints().forEach(fk->{
				VirtualTableField field= resVirtualTableMaster.getVirtualTableFieldsList().stream()
						.filter(f->f.getFieldName().equals(fk.getVirtualTableField().getFieldName())).findFirst().orElse(null);
				if(field.getFieldConstraintList()==null)
					field.setFieldConstraintList(new HashSet<VirtualTableConstraints>());
				field.getFieldConstraintList().add(fk);
				fk.setVirtualTableField(field);
				constraints.add(fk);
			});
			
		}
		virtualTableConstraintsService.saveAll(constraints);
//		Set<VirtualTableConstraints> constraints= virtualTableFieldsConstraintDto.getVirtualTableConstraints()
//				.stream().filter(c->c.getVirtualTableField().getFieldName().equals(u.getFieldName())).collect(Collectors.toSet());
//		if(constraints!=null && !constraints.isEmpty())
//			u.setFieldConstraintList(constraints);
		
		resVirtualTableMaster.setVirtualTableFieldsList(null);	
		return resVirtualTableMaster;
		
		
	}
	@Override
	@Transactional
	public VirtualTableMaster update(VirtualTableFieldsConstraintDto virtualTableFieldsConstraintDto) throws AlreadyExistsException {
		VirtualTableMaster updateVirtualTableMaster =virtualTableMasterDao.findById(virtualTableFieldsConstraintDto.getVirtualTable().getId()).get();
				
		updateVirtualTableMaster.setTableName(virtualTableFieldsConstraintDto.getVirtualTable().getTableName());
		updateVirtualTableMaster.setTableDesc(virtualTableFieldsConstraintDto.getVirtualTable().getTableDesc());
		updateVirtualTableMaster.setAutoIncr(virtualTableFieldsConstraintDto.getVirtualTable().isAutoIncr());
		updateVirtualTableMaster.setProcess(virtualTableFieldsConstraintDto.getVirtualTable().getProcess());
		if(virtualTableFieldsConstraintDto.getVirtualTableFields()!=null && !virtualTableFieldsConstraintDto.getVirtualTableFields().isEmpty() )
			for (VirtualTableField virtualTableFields : virtualTableFieldsConstraintDto.getVirtualTableFields()) {
//				virtualTableFields.setFieldConstraintList(virtualTableFields.getFieldConstraintList()!=null && virtualTableFields.getFieldConstraintList().isEmpty()?
//						null:virtualTableFields.getFieldConstraintList());
				if(virtualTableFields.isDeleted())
				{
					virtualTableFieldsService.delete(virtualTableFields.getId()); //delete requested fields from db
					updateVirtualTableMaster.getVirtualTableFieldsList().removeIf(field->field.getId()==virtualTableFields.getId());
					continue;
				}
				if(virtualTableFields.getId()!=0)
					virtualTableFieldsService.update(virtualTableFields);//updating fields if they are already exists in db
				else
				{
					virtualTableFields.setVirtualTableMaster(updateVirtualTableMaster);
					virtualTableFieldsService.save(virtualTableFields);
				}
						
			}
		if(updateVirtualTableMaster.getVirtualTableSequence()==null) //creating sequence if it is not created already
		{
			VirtualTableSequence sequence=new VirtualTableSequence();
			sequence.setSequenceName(updateVirtualTableMaster.getTableName()+"_seq");
			sequence.setVirtualTableMaster(updateVirtualTableMaster);
			updateVirtualTableMaster.setVirtualTableSequence(sequence);
		}
		VirtualTableMaster resVirtualTableMaster=virtualTableMasterDao.save(updateVirtualTableMaster);
		
//		virtualTableConstraintsService.findAllByTableId(resVirtualTableMaster.getId()).forEach(constraint->{ //deleting all constaints TODO: need to optimize
//			virtualTableConstraintsService.delete(constraint.getId());
//		});
		
		
		//freshly saving all constraints just as like save method
		Set<VirtualTableConstraints> constraints=new HashSet<VirtualTableConstraints>();
		if(virtualTableFieldsConstraintDto.getPkConstraint()!=null && virtualTableFieldsConstraintDto.getPkConstraint().getVirtualTableField()!=null) {
			VirtualTableField field= resVirtualTableMaster.getVirtualTableFieldsList().stream()
					.filter(f->f.getFieldName().equals(virtualTableFieldsConstraintDto.getPkConstraint().getVirtualTableField().getFieldName())).findFirst().orElse(null);
//			if(field.getFieldConstraintList()==null)
//				field.setFieldConstraintList(new HashSet<VirtualTableConstraints>());
			
//			field.getFieldConstraintList().add(virtualTableFieldsConstraintDto.getPkConstraint());
			virtualTableFieldsConstraintDto.getPkConstraint().setVirtualTableField(field);
			constraints.add(virtualTableFieldsConstraintDto.getPkConstraint());
			
		}
		
		if(virtualTableFieldsConstraintDto.getFkConstraints()!=null ) {
			virtualTableFieldsConstraintDto.getFkConstraints().forEach(fk->{
				
				if(fk.isDeleted()){
					virtualTableConstraintsService.delete(fk.getId()); //delete requested fields from db
					
				}
				else {
					VirtualTableField field= resVirtualTableMaster.getVirtualTableFieldsList().stream()
							.filter(f->f.getFieldName().equals(fk.getVirtualTableField().getFieldName())).findFirst().orElse(null);
//					if(field.getFieldConstraintList()==null)
//						field.setFieldConstraintList(new HashSet<VirtualTableConstraints>());
//					field.getFieldConstraintList().add(fk);
					fk.setVirtualTableField(field);
					
				
					constraints.add(fk);
				}
				
			});
			
		}
		virtualTableConstraintsService.saveAll(constraints);
		
//		resVirtualTableMaster.setVirtualTableFieldsList(null); // if you set null, then it is giving error
        return resVirtualTableMaster;
	}

}
