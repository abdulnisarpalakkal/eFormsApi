package com.focowell.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focowell.config.error.AlreadyExistsException;
import com.focowell.dao.FormMasterDao;
import com.focowell.model.FormDesign;
import com.focowell.model.FormMaster;
import com.focowell.service.FormMasterService;
import com.focowell.service.VirtualTableFieldsService;

@Service(value = "formMasterService")
public class FormMasterServiceImpl implements FormMasterService {

	@Autowired
	private FormMasterDao formMasterDao;
	
	@Autowired
    private VirtualTableFieldsService virtualTableFieldsService;
	
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
	public FormMaster updateDesign(Set<FormDesign> formDesigns)  {
		
		FormMaster form=formDesigns.stream().findFirst().get().getFormMaster();
		FormMaster updateFormMaster =formMasterDao.findById(form.getId()).get();

		updateFormMaster.getFormDesignList().clear();
		updateFormMaster.getFormDesignList().addAll(formDesigns);
//		updateFormMaster.setFormDesignList(formDesigns);
		formDesigns.forEach(design->{
			design.setFormMaster(updateFormMaster);
			if(design.getComponentRefValues()!=null)
				design.getComponentRefValues().forEach(refValue->refValue.setFormDesign(design));
		});
		
		
        formMasterDao.save(updateFormMaster);
        return updateFormMaster;
	}
	
	private boolean formMasterExist(String formName	) {
        List<FormMaster> forms = formMasterDao.findByFormName(formName);
        if (forms != null && forms.size()!=0 ) {
            return true;
        }
        return false;
    }
	
}
