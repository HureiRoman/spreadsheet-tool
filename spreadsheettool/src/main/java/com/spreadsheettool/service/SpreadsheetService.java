package com.spreadsheettool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spreadsheettool.entity.Cell;
import com.spreadsheettool.entity.Spreadsheet;
import com.spreadsheettool.repository.SpreadsheetRepository;

@Service
public class SpreadsheetService {
	
	@Autowired
	private SpreadsheetRepository spreadsheetRepository;

	public Spreadsheet findByName(String name) {
		return spreadsheetRepository.findByName(name);
	}

	public Spreadsheet findById(String spreadsheetId) {
		return spreadsheetRepository.findById(spreadsheetId);
	}

	public void updateCell(String spreadSheetName, Cell cell) {
		Spreadsheet dbSpreadsheet = spreadsheetRepository.findByName(spreadSheetName);
	
		spreadsheetRepository.updateCell(dbSpreadsheet, cell);
	}

	public Spreadsheet add(Spreadsheet spreadsheet) {
		Spreadsheet dbSpreadsheet  = spreadsheetRepository.findByName(spreadsheet.getName());
		
		if(dbSpreadsheet == null)
			return spreadsheetRepository.save(spreadsheet);
		
		return dbSpreadsheet;
	}

	public List<String> findAllNames() {
		return spreadsheetRepository.findAllNames();
	}
}
