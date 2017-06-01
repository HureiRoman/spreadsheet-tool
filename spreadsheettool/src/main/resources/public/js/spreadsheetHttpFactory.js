spreadsheetapp.factory('spreadsheetHttpFactory', function($http) {
	var factory = {};
	
	factory.addNewSpreadsheet = function(spreadheet, successCallback){
		$http.put(Constants.spreadsheetAddNewUrl, spreadheet).then(successCallback);
	}
	
	factory.saveCell = function(selectedSheet, cell){
		$http.put(Constants.spreadsheetUpdateCellUrl + selectedSheet, cell);
	}
	
	factory.findAllSpreadsheetNames = function(successCallback){
		$http.get(Constants.spreadsheetFindAllNamesUrl).then(successCallback);
	}
	
	factory.getSpreadsheetData = function(spreadsheetName, successCallback){
		$http.get(Constants.spreadsheetByNameUrl + spreadsheetName).then(successCallback);
	}
	
	return factory;
});