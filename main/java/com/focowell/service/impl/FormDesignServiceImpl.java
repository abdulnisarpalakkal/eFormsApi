package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.FormDesignDao;
import com.focowell.model.FormComponentRefValue;
import com.focowell.model.FormComponentType;
import com.focowell.model.FormDesign;
import com.focowell.model.VirtualTableConstraintType;
import com.focowell.model.VirtualTableConstraints;
import com.focowell.model.VirtualTableField;
import com.focowell.model.VirtualTableFieldDataType;
import com.focowell.model.dto.FormDesignDto;
import com.focowell.service.FormDesignService;
import com.focowell.service.FormRuleService;
import com.focowell.service.FormRuleTypeService;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableRecordsMongoService;

@Service(value = "formDesignService")
public class FormDesignServiceImpl implements FormDesignService {

	@Autowired
	private FormDesignDao formDesignDao;
	
	@Autowired
    private VirtualTableFieldsService virtualTableFieldsService;
    
    @Autowired
    private FormRuleService formRuleService;
    
    @Autowired
    private FormRuleTypeService formRuleTypeService;
    
    @Autowired
	private VirtualTableRecordsMongoService virtualTableRecordsService;
	
	
	
	@Override
	public List<FormDesign> findAll() {
		List<FormDesign> list = new ArrayList<>();
		formDesignDao.findAllByJPQL().iterator().forEachRemaining(list::add);
		return list;
	}
	@Override
	public List<FormDesign> findAllFormComponentsByFormId(Long formId) {
		List<FormDesign> designList = new ArrayList<>();
		
		formDesignDao.findAllByFormIdJPQL(formId).iterator().forEachRemaining(designList::add);
		return designList;
	}
	
	@Override
	public FormDesignDto findAllByFormId(Long formId) {
		FormDesignDto formDesignDto=new FormDesignDto();
		
		formDesignDto.setFormDesigns(findAllFormComponentsByFormId(formId));
		formDesignDto.setFormRules(formRuleService.findByFormId(formId));
		formDesignDto.setFormRuleTypes(formRuleTypeService.findAll());
		return formDesignDto;
	}

	@Override
	public void delete(long id) {
		formDesignDao.deleteById(id);
		
	}

	@Override
	public FormDesign findOne(String componentName) {
		return formDesignDao.findByComponentName(componentName).get(0);
	}

	@Override
	public FormDesign findById(Long id) {
		return formDesignDao.findByIdJPQL(id).get();
	}

	@Override
	public FormDesign save(FormDesign formDesign) throws AlreadyExistsException {
		if(formDesignExist(formDesign.getComponentName()))
		{
			throw new AlreadyExistsException(
		              "There is an FormDesign with the same name "
		              +  formDesign.getComponentName() );
		}
		
		
		FormDesign resFormDesign=formDesignDao.save(formDesign);
		return resFormDesign;
		
		
	}
	@Override
	public List<FormDesign> saveAll(List<FormDesign> formDesigns)  {
		
		List<FormDesign> list = new ArrayList<>();
		
		 // keeping bidirectional relationship
		
		formDesigns.forEach(formDesign->{
			if(formDesign.getFormComponent()!=null) {
				formDesign.getFormComponent().setFormDesign(formDesign);
				formDesign.getFormComponent().getComponentRefValues().forEach(refValue->refValue.setFormComponent(formDesign.getFormComponent()));
			}
			if(formDesign.getFormGrid()!=null) {
				if(formDesign.getFormGrid().getFormDesignList()!=null)
					formDesign.getFormGrid().getFormDesignList().add(formDesign);
				else
					formDesign.getFormGrid().setFormDesignList(new HashSet<>(Arrays.asList(formDesign))) ;
					
			}
		});
		
		
		formDesignDao.saveAll(formDesigns).iterator().forEachRemaining(list::add);
		return list;
		
		
	}

	@Override
	public FormDesign update(FormDesign formDesign) throws AlreadyExistsException {
		FormDesign updateFormDesign =formDesignDao.findById(formDesign.getId()).get();
				
		updateFormDesign.setComponentName(formDesign.getComponentName());
		
		updateFormDesign.setComponentType(formDesign.getComponentType());
		
		
		updateFormDesign.setAlignOrder(formDesign.getAlignOrder());
		updateFormDesign.setHide(formDesign.isHide());
		updateFormDesign.setFormMaster(formDesign.getFormMaster());
		
		
		if(updateFormDesign.getFormComponent()!=null && formDesign.getFormComponent()!=null) {
			updateFormDesign.getFormComponent().setComponentLabel(formDesign.getFormComponent().getComponentLabel());
			updateFormDesign.getFormComponent().setComponentValue(formDesign.getFormComponent().getComponentValue());
			updateFormDesign.getFormComponent().setVirtualTableField(formDesign.getFormComponent().getVirtualTableField());
			updateFormDesign.getFormComponent().setComponentRefValues(formDesign.getFormComponent().getComponentRefValues());
		}
		else 
			updateFormDesign.setFormComponent(formDesign.getFormComponent());
		
		//TODO grid updation
			
		
        return formDesignDao.save(updateFormDesign);
	}
	
	private boolean formDesignExist(String componentName) {
        List<FormDesign> forms = formDesignDao.findByComponentName(componentName);
        if (forms != null && forms.size()!=0 ) {
            return true;
        }
        return false;
    }
	@Override
	public List<FormComponentType> findAllFormComponentTypes() {
		
		List<FormComponentType> list = Arrays.asList(FormComponentType.values());
		return list;
	}
	
	@Override
	public void populateRefValuesIfForeignKey(FormDesign formDesign) {
		
		if(formDesign.getComponentType()==FormComponentType.GRID 
				|| formDesign.getFormComponent().getVirtualTableField().getFieldConstraintList()==null || formDesign.getFormComponent().getVirtualTableField().getFieldConstraintList().isEmpty() )
			return;
		VirtualTableConstraints foreignConstraint=formDesign.getFormComponent().getVirtualTableField().getFieldConstraintList()
				.stream().filter(constraint->constraint.getConstraintType()==VirtualTableConstraintType.FOREIGN_KEY).findFirst().orElse(null);
		if(foreignConstraint!=null) {
			long tableId=foreignConstraint.getForeignConstraint().getVirtualTableField().getVirtualTableMaster().getId();
			FormComponentRefValue compRefValue=new FormComponentRefValue();
			compRefValue.setRefKey(foreignConstraint.getForeignConstraint().getVirtualTableField().getFieldName());
			if(formDesign.getFormComponent().getComponentRefValues()==null) 
				compRefValue.setRefValue(foreignConstraint.getForeignConstraint().getVirtualTableField().getFieldName());
			else 
				compRefValue.setRefValue(formDesign.getFormComponent().getComponentRefValues().stream().findFirst().get().getRefValue());
			
			formDesign.getFormComponent().setComponentRefValues(
					virtualTableRecordsService.findAllFormComponentRefValueByTableAndFields(
							tableId,
							compRefValue.getRefKey(), //reference key always will be the primary field of reference table
							compRefValue.getRefValue()
							));
			formDesign.setComponentType(FormComponentType.COMPO);
		}
	}
	@Override
	public List<FormDesign> getFormDesignFromTableFields(List<VirtualTableField> fields){
		List<FormDesign> formDesigns=null;
		
		if(fields!=null) {
			int index=0;
			formDesigns=new ArrayList<>();
			for(VirtualTableField field:fields){
				FormDesign design=new FormDesign(field.getFieldName(),field.getFieldName(),FormComponentType.TEXT,index++,field);
				if(field.getFieldDataType()==VirtualTableFieldDataType.DATE)
					design.setComponentType(FormComponentType.DATE);
				if(field.getFieldDataType()==VirtualTableFieldDataType.BLOB)
					design.setComponentType(FormComponentType.FILE);
				else
					populateRefValuesIfForeignKey(design);
				
				formDesigns.add(design);
			}
		}
		
		return formDesigns;
	}
	
}
