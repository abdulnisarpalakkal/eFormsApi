package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.FormMasterDao;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;
import com.focowell.model.FormRule;
import com.focowell.model.dto.FormDesignDto;
import com.focowell.service.FormMasterService;
import com.focowell.service.FormRuleService;
import com.focowell.service.VirtualTableFieldsService;

@Service(value = "formMasterService")
public class FormMasterServiceImpl implements FormMasterService {

	@Autowired
	private FormMasterDao formMasterDao;
	
	@Autowired
    private VirtualTableFieldsService virtualTableFieldsService;
	
	@Autowired
	private FormRuleService formRuleService;
	
	@Override
	public List<FormMaster> findAll() {
		List<FormMaster> list = new ArrayList<>();
		formMasterDao.findAllByJPQL().iterator().forEachRemaining(list::add);
		list.forEach(form->form.getAccessGroups().size());
		return list;
	}

	@Override
	public List<FormMaster> findAllByProcess(long processId) {
		List<FormMaster> list = new ArrayList<>();
		formMasterDao.findAllByProcessJPQL(processId).iterator().forEachRemaining(list::add);
		return list;
	}
	
	@Override
	public void delete(long id) {
		formMasterDao.deleteById(id);
		
	}

	@Override
	public FormMaster findOne(String formMasterName) {
		return formMasterDao.findByFormName(formMasterName).get(0);
	}

	@Override
	public FormMaster findById(Long id) {
		return formMasterDao.findByIdJPQL(id).get();
	}

	@Override
	public FormMaster save(FormMaster formMaster) throws AlreadyExistsException {
		if(formMasterExist(formMaster.getFormName()))
		{
			throw new AlreadyExistsException(
		              "There is an FormMaster with the same name "
		              +  formMaster.getFormName() );
		}
		
//		formMaster.getAccessGroups().forEach(role->{
//			Set<FormMaster> forms=new HashSet<FormMaster>() ;
//			role.setForms(forms);
//		});
		FormMaster resFormMaster=formMasterDao.save(formMaster);
		return resFormMaster;
		
		
	}

	@Override
	public FormMaster update(FormMaster formMaster) throws AlreadyExistsException {
		FormMaster updateFormMaster =formMasterDao.findById(formMaster.getId()).get();
				
		updateFormMaster.setFormName(formMaster.getFormName());
		updateFormMaster.setFormDesc(formMaster.getFormDesc());
		updateFormMaster.setOpen(formMaster.isOpen());
		updateFormMaster.setAccessUsers(formMaster.getAccessUsers());
		updateFormMaster.setAccessGroups(formMaster.getAccessGroups());
		
		updateFormMaster.setVirtualTableMaster(formMaster.getVirtualTableMaster());
		
        return formMasterDao.save(updateFormMaster);
	}
	
	@Override
	@Transactional
	public boolean updateDesign(FormDesignDto formDesignDto)  {
		List<FormDesign> formDesigns=formDesignDto.getFormDesigns();
		FormMaster form=formDesigns.stream().findFirst().get().getFormMaster();
		
	 
		
		FormMaster updateFormMaster =formMasterDao.findById(form.getId()).get();

		updateFormMaster.getFormDesignList().clear();
		updateFormMaster.getFormDesignList().addAll(formDesigns);
	
		// settting bidirectional relationship b/w formDesign and formRule, formDesign and componentRefValues, formRule and formRuleParameterValue

		formDesigns.forEach(design->{
			design.setFormMaster(updateFormMaster);
			if(design.getComponentRefValues()!=null)
				design.getComponentRefValues().forEach(refValue->refValue.setFormDesign(design));
			
			
			
			if(design.getFormRules()!=null) { 
				Set<FormRule> fromFormRules=design.getFormRules().stream()
						.map(designRule->formDesignDto.getFormRules().stream()
								.filter(rule->rule.getFormRuleName().equals(designRule.getFormRuleName())).findFirst().get())
						.collect(Collectors.toSet());
				design.setFormRules(fromFormRules);
			}
			
		});
		if(formDesignDto.getFormRules()!=null) {
			formDesignDto.getFormRules().forEach(formRule->{
				formRule.setFormMaster(updateFormMaster);
				formRule.setFormDesigns(formDesigns.stream().filter(design->
				design.getFormRules()!=null && design.getFormRules().stream().filter(rule->rule.getFormRuleName().equals(formRule.getFormRuleName())).findAny().isPresent())
						.collect(Collectors.toSet()));
				
				if(formRule.getFormRuleParameterValues()!=null) {
					formRule.getFormRuleParameterValues().forEach(parameterValue->{
						parameterValue.setFormRule(formRule);
					});
				}
			});
		}
		
		
		updateFormMaster.getFormRules().clear();
		updateFormMaster.getFormRules().addAll(formDesignDto.getFormRules());
		

        formMasterDao.save(updateFormMaster);
        return true;
	}
	
	private boolean formMasterExist(String formName	) {
        List<FormMaster> forms = formMasterDao.findByFormName(formName);
        if (forms != null && forms.size()!=0 ) {
            return true;
        }
        return false;
    }
	
}
