package eFormsAPI;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTests {
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate =new TestRestTemplate();
	
	HttpHeaders headers=new HttpHeaders();
	
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
    public void testRetrieveCategory() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
          createURLWithPort("/category/categories"), HttpMethod.GET, entity, String.class);
        HttpStatus status = response.getStatusCode();
       
        assertTrue(status.equals(HttpStatus.OK));
    }
	 private String createURLWithPort(String uri) {
	        return "http://localhost:" + port + uri;
	    }
}
