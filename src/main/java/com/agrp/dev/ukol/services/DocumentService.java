package com.agrp.dev.ukol.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agrp.dev.ukol.entities.DocumentEntity;
import com.agrp.dev.ukol.entities.DocumentUserViewEntity;
import com.agrp.dev.ukol.repositories.DocumentRepository;
import com.agrp.dev.ukol.repositories.DocumentUserViewRepository;
import com.agrp.dev.ukol.repositories.UserRepository;
import com.agrp.dev.ukol.valueObjects.CommonResultVO;
import com.agrp.dev.ukol.valueObjects.DocumentVO;

/**
 * @author SebelaM
 *
 */
@Service
public class DocumentService {
	private final DocumentRepository documentRepository;
	private final UserRepository userRepository;
	private final DocumentUserViewRepository documentUserViewRepository;

    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository, DocumentUserViewRepository documentUserViewRepository) {
    	this.documentRepository = documentRepository;
    	this.userRepository = userRepository;
    	this.documentUserViewRepository = documentUserViewRepository;
    }
    
    public List<DocumentVO> getTrendingDocuments() {
    	List<DocumentVO> documents = new ArrayList<>();
    	
    	Pageable topTen = PageRequest.of(0, 10);
    	Date dateTo = new Date();
    	Calendar cal = Calendar.getInstance();
        cal.setTime(dateTo);
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date dateFrom = cal.getTime();
        
    	List<DocumentEntity> documentEntities = documentRepository.topViewedDocuments(dateFrom, dateTo, topTen);
    	for(DocumentEntity documentEntity : documentEntities) {
    		documents.add(toDocumentVO(documentEntity));
    	}

        return documents;
    }

    public DocumentVO getDocumentEntity(Long id) {
    	DocumentEntity document = documentRepository.findById(id)
        	.orElseThrow(() ->new RuntimeException("Entity not found"));
    	
        return toDocumentVO(document);
    }
    
    public CommonResultVO viewDocument(Long documentUuid, Long userUuid) {
    	CommonResultVO resultVO = new CommonResultVO(); 
    	
    	try {
    		
    		DocumentUserViewEntity docView = new DocumentUserViewEntity();
    		docView.setUser(userRepository.getById(userUuid));
    		docView.setDocument(documentRepository.getById(documentUuid));
    		docView.setViewDate(new Date());
    	
    		documentUserViewRepository.saveAndFlush(docView);
    		resultVO.setResult(true);
    	} catch (Exception e) {
    		resultVO.setResult(false);
    		resultVO.setMessage(e.getMessage());
    	}
    	return resultVO;
    }
    
    private DocumentVO toDocumentVO(DocumentEntity documentEntity) {
    	DocumentVO documentVO = new DocumentVO();
    	documentVO.setUuid(documentEntity.getUuid());
    	documentVO.setTitle(documentEntity.getTitle());
    	if(documentEntity.getViews() != null) {
    		documentVO.setViewCount(documentEntity.getViews().size());
    	}
    	
    	return documentVO;
    }
}
