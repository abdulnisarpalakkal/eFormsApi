package com.focowell.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.focowell.dao.UserDao;
import com.focowell.model.WorkflowTrackMaster;
import com.focowell.service.UserRolesService;
import com.focowell.service.WorkflowTrackMasterService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=WorkflowTrackMasterController.class)
@ActiveProfiles("test")
@WithMockUser
public class WorkflowTrackMaterControllerTest {

	@TestConfiguration
	static class WorkflowTrackMaterServiceTestContextConfiguration{
//		@Bean
//		UserService userService() {
//			return new UserServiceImpl();
//		}
	}
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private WorkflowTrackMasterService workflowTrackMasterService;
	@MockBean
	private UserDao userDao;
	
	@MockBean
	private UserRolesService userRolesService;
	
	@Test
	public void givenId_whenFindById_thenReturnJsonArray() throws Exception {
		WorkflowTrackMaster workflowTrackMaster=new WorkflowTrackMaster();
		workflowTrackMaster.setId(2);
		workflowTrackMaster.setCompleted(true);
		workflowTrackMaster.setDataId(15);
		
		List<WorkflowTrackMaster> allEmployees = Arrays.asList(workflowTrackMaster);
		Mockito.when(workflowTrackMasterService.findAll()).thenReturn(allEmployees);
		mvc.perform(get("/workflowTrackMaster/workflowTrackMasters")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
			   
				
	}
}
