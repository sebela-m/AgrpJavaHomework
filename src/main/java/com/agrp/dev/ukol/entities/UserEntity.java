package com.agrp.dev.ukol.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * @author SebelaM
 *
 */
@Data
@Entity
public class UserEntity {
	
    @Id
    @GeneratedValue
    private Long uuid;

    @Column
    private String name;
    
    @OneToMany(mappedBy = "user")
    private List<DocumentUserViewEntity> views = new ArrayList<DocumentUserViewEntity>();
}
