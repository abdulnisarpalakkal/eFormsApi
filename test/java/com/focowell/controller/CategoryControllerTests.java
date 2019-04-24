package com.focowell.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class CategoryControllerTests {
	
	
	TestRestTemplate restTemplate =new TestRestTemplate();
	
	HttpHeaders headers=new HttpHeaders();
	
	 @Test
    public void find_allBook_OK() throws Exception {
//
//        List<Book> books = Arrays.asList(
//                new Book(1L, "Book A", "Ah Pig", new BigDecimal("1.99")),
//                new Book(2L, "Book B", "Ah Dog", new BigDecimal("2.99")));
//
//        when(mockRepository.findAll()).thenReturn(books);

//        String expected = om.writeValueAsString(books);

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/category/categories", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
//        JSONAssert.assertEquals(expected, response.getBody(), false);

//        verify(mockRepository, times(1)).findAll();
	    }
	
//	@Test
//	public void testCreateCategory() throws Exception{
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//		 ResponseEntity<String> response = restTemplate.exchange(
//		          createURLWithPort("/category/categories"), HttpMethod.POST, entity, String.class);
//		 String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
//	     assertTrue(actual.contains("/category/categories"));
//	}
//	
//	@Test
//    public void testRetrieveCategory() throws Exception {
//        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//        ResponseEntity<String> response = restTemplate.exchange(
//          createURLWithPort("/category/categories"), HttpMethod.GET, entity, String.class);
//        HttpStatus status = response.getStatusCode();
//       
//        assertTrue(status.equals(HttpStatus.OK));
//    }
//	 private String createURLWithPort(String uri) {
//	        return "http://localhost:" + port + uri;
//	    }
}
