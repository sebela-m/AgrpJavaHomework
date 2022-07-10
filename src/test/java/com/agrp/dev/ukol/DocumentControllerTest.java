package com.agrp.dev.ukol;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.agrp.dev.ukol.entities.DocumentEntity;
import com.agrp.dev.ukol.entities.UserEntity;
import com.agrp.dev.ukol.repositories.DocumentRepository;
import com.agrp.dev.ukol.repositories.DocumentUserViewRepository;
import com.agrp.dev.ukol.repositories.UserRepository;
import com.agrp.dev.ukol.valueObjects.CommonResultVO;
import com.agrp.dev.ukol.valueObjects.DocumentVO;

import static org.rnorth.visibleassertions.VisibleAssertions.assertEquals;
import static org.rnorth.visibleassertions.VisibleAssertions.assertNotEquals;

import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author SebelaM
 *
 */
public class DocumentControllerTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;
        
    @LocalServerPort
    private int randomServerPort;
    
    @Before
    public void setup() {
    	for(int i = 1; i <= 10; i++) {    		
    		DocumentEntity documentEntity = new DocumentEntity();
    		documentEntity.setUuid(Long.valueOf(i));
    		documentEntity.setTitle("Test" + i);
    		documentRepository.save(documentEntity);
    	}
    	
    	for(int i = 11; i <= 20; i++) {   
    		UserEntity userEntity = new UserEntity();
        	userEntity.setUuid(Long.valueOf(i));
        	userEntity.setName("User" + i);
        	userRepository.save(userEntity);
    	}
    }

    @Test
    public void testGetDocument() {
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
		
        String getDocumentResource = "http://localhost:" 
        			+ randomServerPort 
        			+ "/documents/" 
        			+ 1;
        
        ResponseEntity<DocumentVO> result = restTemplate.exchange(getDocumentResource, 
        		HttpMethod.GET, new HttpEntity<Object>(headers), DocumentVO.class);
        assertEquals("Get document 1", 1L, result.getBody().getUuid());
        

        
        
        getDocumentResource = "http://localhost:" 
    			+ randomServerPort 
    			+ "/documents/" 
    			+ 100;
        result = restTemplate.exchange(getDocumentResource, 
        		HttpMethod.GET, new HttpEntity<Object>(headers), DocumentVO.class);
        assertNotEquals("Get document 100", 100L, result);
    }

    @Test
    public void testViewDocument() {
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        
        boolean result = sendViewDocument(11L, 1L);
    		
        assertEquals("Document 1 is viewed by user 11", true, result);
        


        
        String getDocumentResource = "http://localhost:" 
    			+ randomServerPort 
    			+ "/documents/" 
    			+ 1;
    
        ResponseEntity<DocumentVO> result2 = restTemplate.exchange(getDocumentResource, 
    			HttpMethod.GET, new HttpEntity<Object>(headers), DocumentVO.class);
    	assertEquals("Document 1 view count = 1", 1, result2.getBody().getViewCount());


    	
    	
    	getDocumentResource = "http://localhost:" 
    			+ randomServerPort 
				+ "/documents/" 
				+ 2;
    	result2 = restTemplate.exchange(getDocumentResource, 
    		HttpMethod.GET, new HttpEntity<Object>(headers), DocumentVO.class);
    	assertEquals("Document 2 view count = 0", 0, result2.getBody().getViewCount());
    }

    @Test
    public void testTrendingDocuments() {

    	sendViewDocument(11L, 3L);
    	sendViewDocument(12L, 4L);
    	sendViewDocument(13L, 4L);
    	sendViewDocument(14L, 4L);
    	sendViewDocument(15L, 4L);
    	sendViewDocument(16L, 4L);
        sendViewDocument(17L, 5L);
        sendViewDocument(18L, 5L);
        sendViewDocument(19L, 5L);
        sendViewDocument(19L, 6L);
        sendViewDocument(19L, 6L);
        sendViewDocument(19L, 7L);
        sendViewDocument(19L, 8L);
        sendViewDocument(19L, 9L);

    	

    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
		
        String getTrendingDocumentsResource = "http://localhost:" 
        			+ randomServerPort 
        			+ "/documents/trending"; 
        
        ParameterizedTypeReference<List<DocumentVO>> responseType = new ParameterizedTypeReference<List<DocumentVO>>() {
        };
        
        ResponseEntity<List<DocumentVO>> result = restTemplate.exchange(getTrendingDocumentsResource, 
        		HttpMethod.GET, new HttpEntity<Object>(headers), responseType);
        
        
        assertEquals("Trending documents - 1. top document has 5 views", 5, result.getBody().get(0).getViewCount());
        assertEquals("Trending documents - 2. top document has 3 views", 3, result.getBody().get(1).getViewCount());
        assertEquals("Trending documents - 3. top document has 2 views", 2, result.getBody().get(2).getViewCount());
        
    }
    
    private boolean sendViewDocument(long user_uuid, long document_uuid) {
    	MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        
    	String viewDocumentResource = "http://localhost:" 
    			+ randomServerPort 
    			+ "/documents/view";
    	
    	JSONObject viewJsonObject = new JSONObject();
    	try {
			viewJsonObject.put("user_uuid", user_uuid);
    		viewJsonObject.put("document_uuid", document_uuid);
		} catch (JSONException e) {
			
		}	
            
        HttpEntity<String> httpEntity = new HttpEntity<>(viewJsonObject.toString(), headers);
    		
        ResponseEntity<CommonResultVO> result = restTemplate.exchange(viewDocumentResource, 
          		HttpMethod.POST, httpEntity, CommonResultVO.class);
        
        return result.getBody().isResult();
    }

}
