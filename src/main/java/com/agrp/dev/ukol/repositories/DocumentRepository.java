package com.agrp.dev.ukol.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agrp.dev.ukol.entities.DocumentEntity;

/**
 * @author SebelaM
 *
 */
@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
	
	@Query(
		  value = "SELECT d.uuid, d.title "
		  		+ "FROM document_entity d "
		  		+ "LEFT JOIN document_user_view_entity e on d.uuid = e.document_uuid "
		  		+ "WHERE ( e.view_date > ?1 AND e.view_date < ?2 ) "
		  		+ "GROUP BY d.uuid "
		  		+ "ORDER BY COUNT(e.view_uuid) DESC",
		  		
		  nativeQuery = true
	)
	public List<DocumentEntity> topViewedDocuments(Date dateFrom, Date dateTo, Pageable pageable);
	
}
