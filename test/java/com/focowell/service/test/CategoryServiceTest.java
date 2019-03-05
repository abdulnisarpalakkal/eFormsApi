package com.focowell.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.focowell.service.impl.CategoryServiceImpl;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

	@Autowired
	CategoryServiceImpl categoryServiceImp;
	
	@Test
	public void testfindAll() {
		assertTrue(categoryServiceImp.findAll().size()>0);
	}
}
