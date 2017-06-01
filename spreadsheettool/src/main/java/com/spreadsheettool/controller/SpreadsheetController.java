package com.spreadsheettool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spreadsheettool.dto.RequestConstants;
import com.spreadsheettool.entity.Cell;
import com.spreadsheettool.entity.Spreadsheet;
import com.spreadsheettool.service.SpreadsheetService;

@RestController
@RequestMapping(RequestConstants.SPREADSHEET)
public class SpreadsheetController {

	@Autowired
	private SpreadsheetService spreadsheetService;
	
	@RequestMapping(value = RequestConstants.ADD, method = RequestMethod.PUT)
	public Spreadsheet add(@RequestBody Spreadsheet spreadsheet){
		return spreadsheetService.add(spreadsheet);
	}
	
	@RequestMapping(value = RequestConstants.FIND_BY_NAME, method = RequestMethod.GET)
	public Spreadsheet findByName(@RequestParam String name){
		return spreadsheetService.findByName(name);
	}
	
	@RequestMapping(value = RequestConstants.FIND_BY_ID, method = RequestMethod.GET)
	public Spreadsheet findById(@RequestParam String id){
		return spreadsheetService.findById(id);
	}
	
	@RequestMapping(value = RequestConstants.UPDATE_CELL, method = RequestMethod.PUT)
	public void updateCell(@RequestParam String spreadsheetName, @RequestBody @Validated Cell cell){
		spreadsheetService.updateCell(spreadsheetName, cell);
	}
	
	@RequestMapping(value = RequestConstants.FIND_ALL_NAMES, method = RequestMethod.GET)
	public List<String> findAllNames(){
		return spreadsheetService.findAllNames();
	}
}
