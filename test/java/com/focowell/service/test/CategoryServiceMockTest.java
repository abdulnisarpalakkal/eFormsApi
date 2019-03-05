package com.focowell.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.focowell.dao.CategoryDao;
import com.focowell.model.Category;
import com.focowell.service.impl.CategoryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceMockTest {
	@Mock
	CategoryDao CategoryDao;
	
	@InjectMocks 
	CategoryServiceImpl categoryServiceImp;
	
//	 @Before
//	 public void init() {
//        MockitoAnnotations.initMocks(this);
//     }
//	
	@Test
	public void testfindAll(){
		List<Category> list=new ArrayList<Category>();
		
		when(CategoryDao.findAll()).thenReturn(list);
		assertEquals(list.size(),categoryServiceImp.findAll().size());
	}
	
}
