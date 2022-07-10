package com.agrp.dev.ukol.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrp.dev.ukol.services.DocumentService;
import com.agrp.dev.ukol.valueObjects.CommonResultVO;
import com.agrp.dev.ukol.valueObjects.DocumentVO;
import com.agrp.dev.ukol.valueObjects.DocumentViewVO;

/**
 * @author Milovan
 *
 */
@RestController
@RequestMapping("/documents/")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService demoService) {
        this.documentService = demoService;
    }

    @GetMapping(value = "/trending", 
    		consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<DocumentVO> getTrending() {
        return documentService.getTrendingDocuments();
    }

    @PostMapping(value ="/view", 
    		consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public CommonResultVO postViewDocument(@RequestBody DocumentViewVO documentView) {
    	return documentService.viewDocument(documentView.getDocument_uuid(), documentView.getUser_uuid());
    }

    @GetMapping(value ="/{id}", 
    		consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public DocumentVO getDocument(@PathVariable("id") long id) {
        return documentService.getDocumentEntity(id);
    }
    
}
