package com.focowell.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.focowell.dto.FormMasterDto;
import com.focowell.model.FormMaster;

@Component
public class FormMasterConvertor {

	@Autowired
	ModelMapper modelMapper;
	
	public FormMasterDto convertFormMasterToFormMasterDto(FormMaster formMaster) {
		
		FormMasterDto formMasterDto=modelMapper.map(formMaster,FormMasterDto.class);
		return formMasterDto;
	}
	public FormMaster convertFormMasterDtoToFormMaster(FormMasterDto formMasterDto) {
		
		FormMaster formMaster=modelMapper.map(formMasterDto,FormMaster.class);
		return formMaster;
	}
}
