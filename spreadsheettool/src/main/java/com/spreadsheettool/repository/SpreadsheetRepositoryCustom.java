package com.spreadsheettool.repository;

import java.util.List;

import com.spreadsheettool.entity.Cell;
import com.spreadsheettool.entity.Spreadsheet;

public interface SpreadsheetRepositoryCustom{

	void updateCell(Spreadsheet dbSpreadsheet, Cell cell);
	List<String> findAllNames();
}
