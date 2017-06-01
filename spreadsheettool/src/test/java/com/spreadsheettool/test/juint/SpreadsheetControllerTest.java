package com.spreadsheettool.test.juint;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.spreadsheettool.dto.RequestConstants;
import com.spreadsheettool.entity.Cell;
import com.spreadsheettool.entity.Spreadsheet;
import com.spreadsheettool.service.SpreadsheetService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpreadsheetControllerTest {
   
	@Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private SpreadsheetService spreadsheetService;
    
    @Before
    public void setup() {
        given(this.spreadsheetService.findByName("spreadsheet1"))
        .willReturn(new Spreadsheet("spreadsheet1", new ArrayList<Cell>()));

        given(this.spreadsheetService.findAllNames())
        .willReturn(Arrays.asList("spreadsheet2"));
        
        given(this.spreadsheetService.findById("test_id"))
        .willReturn(new Spreadsheet("spreadsheet3", new ArrayList<Cell>()));
    }

    @Test
    public void findByNameTest() {
       Spreadsheet spreadsheet = this.restTemplate.getForEntity(RequestConstants.SPREADSHEET
    		   + RequestConstants.FIND_BY_NAME + "?name=spreadsheet1", Spreadsheet.class).getBody();
       
       Mockito.verify(spreadsheetService, times(1)).findByName("spreadsheet1");
       
       assertEquals("spreadsheet1", spreadsheet.getName());
    }
    
    @Test
    public void findAllNamesTest() {
    	String[] spreadsheetNames = this.restTemplate.getForEntity(RequestConstants.SPREADSHEET
    		   + RequestConstants.FIND_ALL_NAMES, String[].class).getBody();
    	
    	  Mockito.verify(spreadsheetService, times(1)).findAllNames();
       
       assertEquals("spreadsheet2", spreadsheetNames[0]);
    }
    
    @Test
    public void spreadsheetUpdateCellTest() {
    	Cell cell = new Cell(2.0, "cellTest", 1, 1);
    	
    	this.restTemplate.put(RequestConstants.SPREADSHEET + RequestConstants.UPDATE_CELL
    			+ "?spreadsheetName=spreadsheet1", cell);
    	
    	  Mockito.verify(spreadsheetService, times(1)).updateCell("spreadsheet1", cell);
    }
    
    @Test
    public void spreadsheetfindByIdTest() {
    	Spreadsheet fetchedSpreadsheet = this.restTemplate.getForEntity(
    			RequestConstants.SPREADSHEET + RequestConstants.FIND_BY_ID
    			+ "?id=test_id", Spreadsheet.class).getBody();
    	
    	  Mockito.verify(spreadsheetService, times(1)).findById("test_id");
    	  
    	  assertEquals(fetchedSpreadsheet.getName(), "spreadsheet3");
    }
}
