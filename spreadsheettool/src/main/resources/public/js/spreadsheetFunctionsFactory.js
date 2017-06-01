spreadsheetapp.factory('spreadsheetFunctionsFactory', function(utilFactory) {
	var factory = {};
	
	factory.parseExpression = function(expr, cellData){
		var func = getFunction(expr),
			arguments = splitArguments(expr),
			numbers = parseArguments(arguments, cellData, utilFactory);
		
		return SpreadsheetFunctions[func](numbers);
	}
	
	factory.isNumberValue = function(expression){
		return !isNaN(parseFloat(expression)) && isFinite(expression);
	}
	
	factory.isValidExpression = function(value){
		var enableFuncRegex = getEnableFunctions().join("|");
		
		var validFormatRegex = new RegExp("^=("+enableFuncRegex+")\(((.;.)|(.))+\)$","g");
		
		var match = validFormatRegex.exec(value);

		if(match != null){
			return isArgsValid(match[0], utilFactory);
		}
		
		return false;
	}
	
	return factory;
});

function isArgsValid(str, utilFactory){
	var args = str.slice(str.indexOf("(") + 1, str.indexOf(")")),
		splitedArguments = args.split(/ *; */);
	
		for(var idx = 0; idx < splitedArguments.length; idx++){
			if(!isValidArg(splitedArguments[idx], utilFactory)){
				return false;
			}
		}

	return true;
}

function isValidArg(val, utilFactory){
	var existHeadersRegex = utilFactory.generateHeaders(Constants.firstHeadChar, Constants.tableSize).join("|"),
		validArgRegex = "^(((\\d+)|(\\d+\\.\\d+))|(("+existHeadersRegex+")((\\d+)|(\\d+\\.\\d+)))|(("
			+existHeadersRegex+")((\\d+)|(\\d+\\.\\d+)) *: *("+existHeadersRegex+")((\\d+)|(\\d+\\.\\d+))))+$";
	
	return new RegExp(validArgRegex).test(val);
	
}

function getEnableFunctions(){
	return Object.keys(SpreadsheetFunctions);
}

function parseArguments(args, cellData, utilFactory){
	var numbers = [];
	
	for(var argIdx = 0; argIdx < args.length; argIdx++){
		if(args[argIdx].includes(":")){
			var rangeNumbers = getRangeNumbers(args[argIdx], Constants.firstHeadChar, cellData);
			
			for(var rangeNumIdx = 0; rangeNumIdx < rangeNumbers.length; rangeNumIdx++){
				numbers.push(rangeNumbers[rangeNumIdx]);
			}
		} else if(isContainsReference(args[argIdx], utilFactory)){
			numbers.push(getValueByReference(args[argIdx], Constants.firstHeadChar, cellData));
		} else {
			numbers.push(+args[argIdx]);
		}
	}
	
	return numbers;
}

function getRangeNumbers(arg, firstChar, allCells){
	var splitedArg = arg.split(":"),
		firstCharCode = firstChar.charCodeAt(0);
		numbers = [];
	
	var rowIdxFirst = getRefRowIdx(splitedArg[0]);
		columnIdxFirst = getRefColumnIdx(splitedArg[0], firstChar);
		rowIdxLast = getRefRowIdx(splitedArg[1]);
		columnIdxLast = getRefColumnIdx(splitedArg[1], firstChar);
		
	for(var rowIdx = rowIdxFirst; rowIdx <= rowIdxLast; rowIdx++){
		for(var columnIdx = columnIdxFirst; columnIdx <= columnIdxLast; columnIdx++){
			var ref = getCellReference(firstCharCode, columnIdx, rowIdx);
			numbers.push(getValueByReference(ref, firstChar, allCells));
		}
	}
	
	return numbers;
}

function getCellReference(firstCharCode, columnIdx, rowIdx){
	return String.fromCharCode(firstCharCode + columnIdx) + (rowIdx + 1);
}

function getValueByReference(cell, firstChar, allCells){
	var cellRowIdx = getRefRowIdx(cell);
		cellColumnIdx = getRefColumnIdx(cell, firstChar);
		
	for(var rowIdx = 0; rowIdx < allCells.length; rowIdx++){
		for(var columnIdx = 0; columnIdx < allCells.length; columnIdx++){
			var currentCell = allCells[rowIdx][columnIdx];
			
			if(isSameCell(currentCell, cellRowIdx, cellColumnIdx)){
				var cellValue = currentCell.value;
				return (cellValue === "") ? 0 : cellValue;
			}
		}
	}
}

function isSameCell(cell, cellRowIdx, cellColumnIdx){
	return cell.rowIdx === cellRowIdx && cell.columnIdx === cellColumnIdx;
}

function getRefColumnIdx(arg, firstColumnsChar){
	var firstChar = arg.split("")[0];
	return firstChar.charCodeAt(0) - firstColumnsChar.charCodeAt(0);
}

function getRefRowIdx(arg){
	return Number(arg.slice(1)) - 1;
}

function isContainsReference(arg, utilFactory){
	var existHeadersRegex = utilFactory.generateHeaders(Constants.firstHeadChar, Constants.tableSize).join("|");
	return new RegExp(existHeadersRegex).test(arg);
}

function getFunction(expr){
	return expr.slice(expr.indexOf("=") + 1, expr.indexOf("("));
}

function splitArguments(expr){
	return expr.slice(expr.indexOf("(") + 1, expr.indexOf(")")).split(/ *; */);
}