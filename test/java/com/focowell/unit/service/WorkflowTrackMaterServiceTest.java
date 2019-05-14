package com.focowell.unit.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.focowell.dao.WorkflowTrackMasterDao;
import com.focowell.model.WorkflowTrackMaster;
import com.focowell.service.FileService;
import com.focowell.service.FormDesignService;
import com.focowell.service.UserRolesService;
import com.focowell.service.UserService;
import com.focowell.service.VirtualTableFieldsService;
import com.focowell.service.VirtualTableRecordsService;
import com.focowell.service.VirtualTableSequenceService;
import com.focowell.service.WorkflowLinkService;
import com.focowell.service.WorkflowMasterService;
import com.focowell.service.WorkflowTrackDetService;
import com.focowell.service.WorkflowTrackMasterService;
import com.focowell.service.impl.WorkflowTrackMasterServiceImpl;

@RunWith(SpringRunner.class)
public class WorkflowTrackMaterServiceTest {
	@TestConfiguration
	static class WorkflowTrackMaterServiceTestContextConfiguration{
		@Bean
		WorkflowTrackMasterService workflowTrackMasterService() {
			return new WorkflowTrackMasterServiceImpl();
		}
		/*@Bean
		WorkflowTrackDetService workflowTrackDetService() {
			return new WorkflowTrackDetServiceImpl();
		}
		@Bean
		WorkflowLinkService WorkflowLinkService() {
			return new WorkflowLinkServiceImpl();
		}
		@Bean
		VirtualTableSequenceService virtualTableSequenceService() {
			return new VirtualTableSequenceServiceImpl();
		}
		
		@Bean
		VirtualTableRecordsService virtualTableRecordsService() {
			return new VirtualTableRecordsServiceImpl();
		}
		@Bean
		VirtualTableFieldsService virtualTableFieldsService() {
			return new VirtualTableFieldsServiceImpl();
		}
		@Bean
		UserService userService() {
			return new UserServiceImpl();
		}
		@Bean
		WorkflowMasterService workflowMasterService() {
			return new WorkflowMasterServiceImpl();
		}
		
		@Bean
		FormDesignService formDesignService() {
			return new FormDesignServiceImpl();
		}
		
		@Bean
		FileService fileService() {
			return new FileServiceImp();
		}
		
		@Bean
		UserRolesService userRolesService() {
			return new UserRolesServiceImpl();
		}*/
		
	}
	
	
	
	
	@Autowired
	WorkflowTrackMasterService workflowTrackMasterService;
	
	@MockBean
	WorkflowTrackDetService workflowTrackDetService;
	
	@MockBean
	WorkflowLinkService WorkflowLinkService;
	
	@MockBean
	VirtualTableSequenceService virtualTableSequenceService;
	
	@MockBean
	VirtualTableRecordsService virtualTableRecordsService;
	
	@MockBean
	VirtualTableFieldsService virtualTableFieldsService;
	
	@MockBean
	UserService userService;
	
	@MockBean
	WorkflowMasterService workflowMasterService;
	
	@MockBean
	FormDesignService formDesignService;
	
	@MockBean
	FileService fileService;
	
	@MockBean
	UserRolesService userRolesService;
	
	
	@MockBean
	WorkflowTrackMasterDao workflowTrackMasterDao;
	
	
	
	@Before
	public void setUp() {
		WorkflowTrackMaster workflowTrackMaster=new WorkflowTrackMaster();
		workflowTrackMaster.setId(2);
		workflowTrackMaster.setCompleted(true);
		workflowTrackMaster.setDataId(15);
		
		Mockito.when(workflowTrackMasterDao.findById(workflowTrackMaster.getId())).thenReturn(Optional.of(workflowTrackMaster));
	}
	
	@Test
	public void whenValidId_thenWorkflowTrackMasterShouldBeFound() {
		WorkflowTrackMaster testTrack=workflowTrackMasterService.findById(2L);
		assertEquals(testTrack.getId(),2);
	}

}
