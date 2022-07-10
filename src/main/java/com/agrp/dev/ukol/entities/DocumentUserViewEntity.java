/**
 * 
 */
package com.agrp.dev.ukol.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * @author SebelaM
 *
 */
@Data
@Entity
public class DocumentUserViewEntity {

	@Id
	@GeneratedValue
	private Long viewUuid;
	
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_uuid")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "document_uuid")
    private DocumentEntity document;
      
    @Column
    private Date viewDate;
    
}
