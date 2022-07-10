package com.agrp.dev.ukol.valueObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author SebelaM
 *
 */
@Data
public class DocumentVO {

	@JsonProperty("uuid")
    private long uuid;
    
	@JsonProperty("title")
    private String title;
    
	@JsonProperty("viewCount")
    private int viewCount;
    
}
