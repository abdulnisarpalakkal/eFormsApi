package com.focowell.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Ignore
@ActiveProfiles("test")
public class CategoryControllerIntegratedTest {
	@LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    
    @WithMockUser("admin")
    @Test
    public void testRetrieveStudent() throws Exception {
        
        headers.setContentType(MediaType.APPLICATION_JSON);  
        headers.setBearerAuth("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn0seyJhdXRob3JpdHkiOiJST0xFX2NvbnN1bHRhbnQifSx7ImF1dGhvcml0eSI6IlJPTEVfbGVhZCBpbnZlc3RpZ2F0aW9uIn0seyJhdXRob3JpdHkiOiJST0xFX3Jldmlld2VyIn0seyJhdXRob3JpdHkiOiJST0xFX3NhZmV0eSJ9LHsiYXV0aG9yaXR5IjoiUk9MRV90b3AgcmV2aWV3ZXJzIn1dLCJpc3MiOiJodHRwOi8vZm9jb3dlbGwuY29tIiwiaWF0IjoxNTU2MjAwMjI3LCJleHAiOjE1NTYyMTgyMjd9.Vp3wmBXEyIkPSJJcxWZKofzTvHU4H7ow4kCkYX-w1tk");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
          createURLWithPort("/category/categories"), HttpMethod.GET, entity, String.class);
        String expected = "{\"id\":1,\"name\":\"Rajesh Bhojwani\",\"description\":\"Class 10\"}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
