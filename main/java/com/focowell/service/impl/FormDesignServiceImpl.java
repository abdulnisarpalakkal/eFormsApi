package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.FormDesignDao;
import com.focowell.dao.FormRuleDao;
import com.focowell.model.FormComponentType;
import com.focowell.model.FormDesign;
import com.focowell.model.FormRule;
import com.focowell.model.dto.FormDesignDto;
import com.focowell.service.FormDesignService;
import com.focowell.service.FormRuleService;
import com.focowell.service.FormRuleTypeService;
import com.focowell.service.VirtualTableFieldsService;

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
		formDesigns.forEach(formDesign->{
			if(formDesign.getComponentRefValues()!=null) {
				formDesign.getComponentRefValues().forEach(refValue->refValue.setFormDesign(formDesign));
			}
		});
		formDesignDao.saveAll(formDesigns).iterator().forEachRemaining(list::add);
		return list;
		
		
	}

	@Override
	public FormDesign update(FormDesign formDesign) throws AlreadyExistsException {
		FormDesign updateFormDesign =formDesignDao.findById(formDesign.getId()).get();
				
		updateFormDesign.setComponentName(formDesign.getComponentName());
		updateFormDesign.setComponentLabel(formDesign.getComponentLabel());
		updateFormDesign.setComponentType(formDesign.getComponentType());
		updateFormDesign.setComponentValue(formDesign.getComponentValue());
		
		updateFormDesign.setAlignOrder(formDesign.getAlignOrder());
		updateFormDesign.setHide(formDesign.isHide());
		updateFormDesign.setFormMaster(formDesign.getFormMaster());
		updateFormDesign.setVirtualTableField(formDesign.getVirtualTableField());
		updateFormDesign.setComponentRefValues(formDesign.getComponentRefValues());
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
	
	
}
