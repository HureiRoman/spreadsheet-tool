spreadsheetapp.controller("baseCtrl", function($scope, utilFactory,
		eventHandlerFactory, spreadsheetFunctionsFactory, spreadsheetHttpFactory){
	$scope.headers = utilFactory.generateHeaders(Constants.firstHeadChar, Constants.tableSize);
	
	$scope.isActiveColumn = isActiveColumn;
	$scope.isActiveRow = isActiveRow;
	$scope.inputFocusHandler = inputFocusHandler;
	$scope.addNewSpreadsheet = addNewSpreadsheetHandler;
	$scope.inputArrowKeysHandler = eventHandlerFactory.inputArrowKeysHandler;
	$scope.formulaInputKeyPressHandler = formulaInputKeyPressHandler; 
	$scope.formulaChangeHandler = handleChangedExpression;
	$scope.inputChangeHandler = inputChangeHandler;
	$scope.updateSpreadsheet = updateSpreadsheet;
	
	loadSpreadsheets();

	function isActiveRow(rowIdx){
		if($scope.focusInput){
			return $scope.focusInput.rowIdx === rowIdx;
		}
	}
	
	function isActiveColumn(header){
		if($scope.focusInput){
			return $scope.focusInput.columnIdx === (header.charCodeAt(0) - Constants.firstHeadChar.charCodeAt(0));
		}
	}
	
	function inputFocusHandler(rowElement){
		$scope.focusInput = rowElement;
		$scope.formulaInput = rowElement;
	}
	
	function addNewSpreadsheetHandler(spreadhseetName){
		var spreadsheet = {
				"name": spreadhseetName
			};
		
		spreadsheetHttpFactory.addNewSpreadsheet(spreadsheet, function(resp){
			localStorage.setItem("lastSheet", resp.data.name);
			loadSpreadsheets();
			
			$scope.spreadhseetName = "";
		});
	}
	
	function formulaInputKeyPressHandler($event, rowElem){
		if($event.charCode === 13) {
			$scope.formulaChangeHandler(rowElem);
		}
	}
	
	function handleChangedExpression(rowElem){
		if(spreadsheetFunctionsFactory.isNumberValue(rowElem.expression)){
			rowElem.value = rowElem.expression;
			saveCellValue(rowElem);
		} else if(spreadsheetFunctionsFactory.isValidExpression(rowElem.expression)) {
			handleExpression(rowElem);
		} else {
			rowElem.value = "#Invalid data";
		}
	}
	
	function handleExpression(rowElem){
		var parsedValue = spreadsheetFunctionsFactory.parseExpression(rowElem.expression, $scope.cellData);
		
		if(spreadsheetFunctionsFactory.isNumberValue(parsedValue)){
			rowElem.value = parsedValue;
			saveCellValue(rowElem);
		} else {
			rowElem.value = "#Invalid data";
		}
	}
	
	function inputChangeHandler(rowElem){
		rowElem.expression = rowElem.value;

		handleChangedExpression(rowElem);
	}

	function saveCellValue(rowElem){
		var cell = {
			"rowIndex": rowElem.rowIdx,
			"columnIndex": rowElem.columnIdx,
			"value": rowElem.value,
			"expression": rowElem.expression
		}
		
		spreadsheetHttpFactory.saveCell($scope.selectedSheet, cell);
	}
	
	function loadSpreadsheets(){
		spreadsheetHttpFactory.findAllSpreadsheetNames(function(namesResponse){
			$scope.spreadsheetNames = namesResponse.data;
			
			var rememberedSheet = localStorage.getItem("lastSheet");
			var firstElem = namesResponse.data[0];
			
			if(!rememberedSheet){
				$scope.selectedSheet = firstElem;
				$scope.updateSpreadsheet(firstElem);
			} else {
				$scope.selectedSheet = rememberedSheet;
				$scope.updateSpreadsheet(rememberedSheet);
			}
		});
	}

	function updateSpreadsheet(spreadsheetName){
		$scope.formulaInput = "";
		localStorage.setItem("lastSheet", spreadsheetName);
		
		spreadsheetHttpFactory.getSpreadsheetData(spreadsheetName, function(data){
			$scope.cellData = utilFactory.parseGetSpreadsheetResponse(data);
		});
	}
	
});
