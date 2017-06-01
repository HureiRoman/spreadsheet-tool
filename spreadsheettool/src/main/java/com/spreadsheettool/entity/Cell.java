package com.spreadsheettool.entity;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cell {

	private Double value;
	
	private String expression;
	
	@NotNull
	private Integer columnIndex;
	
	@NotNull
	private Integer rowIndex;

	public void update(Cell cell) {
		this.value = cell.value;
		this.expression = cell.expression;
		this.columnIndex = cell.columnIndex;
		this.rowIndex = cell.rowIndex;
	}
	
}
