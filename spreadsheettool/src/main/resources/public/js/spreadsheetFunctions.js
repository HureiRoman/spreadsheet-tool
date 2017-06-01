var SpreadsheetFunctions = {
	sqrt: function sqrt(numbers){
		if(numbers.length === 1)
			return  Math.sqrt(numbers[0]);
	},
	sum: function(array){
		return array.reduce(function(a, b) { return +a + +b; }, 0);
	}
}
