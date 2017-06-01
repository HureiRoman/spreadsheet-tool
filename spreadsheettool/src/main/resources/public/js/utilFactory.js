var spreadsheetapp = angular.module("spreadsheetapp", []);

spreadsheetapp.factory('utilFactory', function() {
	var factory = {};
	
	factory.generateHeaders = function(firstChar, countOfElements) {
		  var result = [];
		  var firstCharCode = firstChar.charCodeAt(0);
		  
		  for (var idx = firstCharCode; idx < firstCharCode + countOfElements; ++idx){
		    result.push(String.fromCharCode(idx));
		  }
		  
		  return result;
	};
	
	factory.parseGetSpreadsheetResponse = function(responseData){
		var cellData = [],
			responseCells = (responseData.data.cells) ? responseData.data.cells : [];
		
		for(var rowIdx = 0; rowIdx < Constants.tableSize; rowIdx++){
			var rawArray = [];
			for(var columnIdx = 0; columnIdx < Constants.tableSize; columnIdx++){
				var cellObj = getCellObjFromResponse(responseCells, rowIdx, columnIdx);
				
				rawArray.push(cellObj);
			}
			
			cellData.push(rawArray);
		}

		return cellData;
	}
	
	return factory;
});

function getResponseObjectByIndexes(responseData, rowIdx, columnIdx){
	for(var idx in responseData){
		if(responseData[idx].rowIndex === rowIdx && responseData[idx].columnIndex === columnIdx){
			return responseData[idx];
		}
	}
}

function getCellObjFromResponse(responseCells, rowIdx, columnIdx){
	var responseObj = getResponseObjectByIndexes(responseCells, rowIdx, columnIdx);
	var cellObj = {};
	
	cellObj.rowIdx = rowIdx;
	cellObj.columnIdx = columnIdx;
	
	if(responseObj){
		cellObj.value = (responseObj.value) ? responseObj.value : "";
		cellObj.expression = (responseObj.expression) ? responseObj.expression : "";
	} else {
		cellObj.value = "";
		cellObj.expression = "";
	}
	
	return cellObj;
}
