spreadsheetapp.factory('eventHandlerFactory', function() {
	var factory = {};
	
	factory.inputArrowKeysHandler = function(e){
		var closestTd = e.srcElement.closest('td')
			closestTr = e.srcElement.closest("tr");
		
		var elemIndex = Array.prototype.indexOf.call(closestTr.children, closestTd);
	
		switch(e.which){
			case 37:
				handleKeyBack(closestTd);
				break;
			case 38:
				handleKeyUp(closestTr, elemIndex);
				break;
			case 39:
				handleKeyNext(closestTd);
				break;
			case 40:
				handleKeyDown(closestTr, elemIndex);
				break;
		}
	}
	
	return factory;
});

function handleKeyDown(closestTr, elemIndex){
	var nextTr = closestTr.nextElementSibling;
	
	if(nextTr && nextTr.children[elemIndex]){
		nextTr.children[elemIndex].querySelector('input').focus();
	}
}

function handleKeyNext(closestTd){
	var nextTd = closestTd.nextElementSibling;
	
	if(nextTd && nextTd.querySelector('input')){
		nextTd.querySelector('input').focus();
	}
}

function handleKeyUp(closestTr, elemIndex){
	var prevTr = closestTr.previousElementSibling;
	
	if(prevTr && prevTr.children[elemIndex]){
		prevTr.children[elemIndex].querySelector('input').focus();
	}
}

function handleKeyBack(closestTd){
	var prevTd = closestTd.previousElementSibling;
	
	if(prevTd && prevTd.querySelector('input')){
		prevTd.querySelector('input').focus();
	}
}