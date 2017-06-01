package com.spreadsheettool.entity;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spreadsheettool.util.ObjectIdJsonSerializer;

@Document
@Data
@NoArgsConstructor
public class Spreadsheet {
	
	@Id
	@JsonSerialize(using = ObjectIdJsonSerializer.class)
	private ObjectId id;
	
	@NotBlank
	private String name;
	
	private List<Cell> cells;
	
	public Spreadsheet(String name, List<Cell> cells) {
		super();
		this.name = name;
		this.cells = cells;
	}
	
}
