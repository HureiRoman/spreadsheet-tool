package com.spreadsheettool.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.spreadsheettool.entity.Cell;
import com.spreadsheettool.entity.Spreadsheet;
import com.spreadsheettool.repository.SpreadsheetRepositoryCustom;

@Component
public class SpreadsheetRepositoryImpl implements SpreadsheetRepositoryCustom{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void updateCell(Spreadsheet dbSpreadsheet, Cell cell) {
		if(dbSpreadsheet != null){
			List<Cell> spreadsheetCells = (dbSpreadsheet.getCells() == null) ? new ArrayList<Cell>() : dbSpreadsheet.getCells();
			
			Optional<Cell> optionalCell = spreadsheetCells.stream().filter( listCell ->
			listCell.getColumnIndex().equals(cell.getColumnIndex()) && listCell.getRowIndex().equals(cell.getRowIndex()) ).findFirst();
		
			if(optionalCell.isPresent()){
				Cell matchedCell = optionalCell.get();

				matchedCell.update(cell);
			} else {
				spreadsheetCells.add(cell);
			}
			
			dbSpreadsheet.setCells(spreadsheetCells);
			
			mongoTemplate.save(dbSpreadsheet);
		}

	}

	@Override
	public List<String> findAllNames() {
		List<Spreadsheet> allSpreadsheets = mongoTemplate.findAll(Spreadsheet.class);
		
		return allSpreadsheets.stream().map(Spreadsheet::getName)
	              .collect(Collectors.toList());
	}

}
