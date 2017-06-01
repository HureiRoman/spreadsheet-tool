package com.spreadsheettool.test.juint;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.spreadsheettool.entity.Cell;
import com.spreadsheettool.entity.Spreadsheet;
import com.spreadsheettool.repository.SpreadsheetRepository;
import com.spreadsheettool.service.SpreadsheetService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpreadsheetServiceTest {
	  
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private SpreadsheetService spreadsheetService;

    @MockBean
	private SpreadsheetRepository spreadsheetRepository;

    @Before
    public void setup() {
        given(this.spreadsheetRepository.findByName("spreadsheet1"))
        .willReturn(new Spreadsheet("spreadsheet1", new ArrayList<Cell>()));
        
        given(this.spreadsheetRepository.findById("test_id"))
        .willReturn(new Spreadsheet("spreadsheet2", new ArrayList<Cell>()));
 
        given(this.spreadsheetRepository.save(new Spreadsheet("spreadsheet3", new ArrayList<Cell>())))
        .willReturn(new Spreadsheet("spreadsheet3", new ArrayList<Cell>()));
 
    }

    @Test
    public void findByNameTest() {
    	Spreadsheet fetchedSpreadsheet = spreadsheetService.findByName("spreadsheet1");
    	
    	Mockito.verify(spreadsheetRepository, times(1)).findByName("spreadsheet1");
    	
    	assertEquals(fetchedSpreadsheet.getName(), "spreadsheet1");
    }
    
    @Test
    public void findByIdTest() {
       Spreadsheet fetchedSpreadsheet = spreadsheetService.findById("test_id");
    	
       Mockito.verify(spreadsheetRepository, times(1)).findById("test_id");
       
       assertEquals(fetchedSpreadsheet.getName(), "spreadsheet2");
    }
    
    @Test
    public void findAllNamesTest() {
    	spreadsheetService.findAllNames();
    	
    	Mockito.verify(spreadsheetRepository, times(1)).findAllNames();
    }
    
    @Test
    public void addTest() {
    	Spreadsheet spreadsheet = new Spreadsheet("spreadsheet3", new ArrayList<Cell>());
    	Spreadsheet fetchedSpreadsheet = spreadsheetService.add(spreadsheet);
    	
    	Mockito.verify(spreadsheetRepository, times(1)).findByName("spreadsheet3");
    	Mockito.verify(spreadsheetRepository, times(1)).save(spreadsheet);
       
    	assertEquals(fetchedSpreadsheet.getName(), "spreadsheet3");
    }
    
    @Test
    public void updateCellTest() {
    	Spreadsheet spreadsheet = new Spreadsheet("spreadsheet1", new ArrayList<Cell>());
    	Cell cell = new Cell(2.0, "testCell", 1, 1);
    	spreadsheetService.updateCell("spreadsheet1", cell);
    	
    	Mockito.verify(spreadsheetRepository, times(1)).findByName("spreadsheet1");
    	Mockito.verify(spreadsheetRepository, times(1)).updateCell(spreadsheet, cell);
    }
    
}
