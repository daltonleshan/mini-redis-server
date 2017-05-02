$(document).ready(() => {
	$('select').on('change', function() {
	  document.getElementById('error').style.display='none';
	  if( this.value == "all"){
		  updatePlaceHolder("ALL-KEYS");
		  $("#query").attr("disabled", "disabled");
	  }else{
		  if(this.value == "get" || this.value == "delete" || this.value == "pop" || this.value == "search"){
			  updatePlaceHolder("key");
		  }else if(this.value == "set" || this.value == "append"){
			  updatePlaceHolder("key, value1, value2 ...");
		  }else if(this.value == "mapget" || this.value == "mapdelete"){
			  updatePlaceHolder("key, mapkey");
		  }else if(this.value == "mapset"){
			  updatePlaceHolder("key, mapkey, mapvalue");
		  }
		  $("#query").removeAttr("disabled");
	  }
	})
	handler();
})

const updatePlaceHolder = (txt) =>{
	$('input[type=text]').each(function(){
	    $(this).attr('placeholder', txt);
	  });
}

const sendEnd = () => {
	if(check()){
		let currWord = '';
		currWord += document.getElementById('query').value;
		const commandP = $('#commands').find(":selected").text();
		let dataType = '';
		if( (string = document.getElementById('string')).checked){
			dataType = string.value;
		}else if( (list = document.getElementById('list')).checked){
			dataType = list.value;
		}else if( (map = document.getElementById('map')).checked){
			dataType = map.value
		};
		document.getElementById('query').value = "";
		const postParameters = {word: currWord, command: commandP, data: dataType};
		
		$.post("/results", postParameters, responseJSON => {
	        // Parse the JSON response into a JavaScript object.
	        const responseObject = JSON.parse(responseJSON);
	        printResults(responseObject);
	    });
	}
}

const printResults = (responseObject) => {
	 const list = document.getElementById('display'); 
     list.innerHTML = ' ';
     const currCommand = $('#commands').find(":selected").text();
     let toAdd = "";
     	if(currCommand == "GET"){
     		toAdd = "<li type ="+ "item" +">" + responseObject.returned +
     			"<li>";
     		list.innerHTML += toAdd;
     	}else if(currCommand == "GET-KEYS"){
     		for(let i = 0; i < responseObject.returned.length; i++){
     			toAdd = "<li type ="+ "item" +">" + responseObject.returned[i] +
     			"<li>";
     			list.innerHTML += toAdd;
     		}
     	}else if(currCommand == "SEARCH-KEYS"){
     		for(let i = 0; i < responseObject.returned.length; i++){
     			toAdd = "<li type ="+ "item" +">" + responseObject.returned[i] +
     			"<li>";
     			list.innerHTML += toAdd;
     		}
     	}else if(currCommand == "POP"){
     		toAdd = "<li type ="+ "item" +">" + responseObject.returned + "<li>";
 			list.innerHTML += toAdd;
     	}else if(currCommand == "MAPGET"){
     		toAdd = "<li type ="+ "item" +">" + responseObject.returned + "<li>";
 			list.innerHTML += toAdd;
     	}
}

const dropdown = (selectValues) => {
	$.each(selectValues, function(key, value) {   
	     $('#commands')
	         .append($("<option></option>")
	                    .attr("value",key)
	                    .text(value)); 
	});
}

const updateDropdown = () => {
	let string = document.getElementById('string');
	let list = document.getElementById('list');
	let map = document.getElementById('map');
	if(string.checked){
		$('select').children().remove();
		const selectValues = { "get": "GET", "set": "SET", "delete": "DELETE", "search": "SEARCH-KEYS", "all" : "GET-KEYS" };
		dropdown(selectValues);
	}else if(list.checked){
		$('select').children().remove();
		const selectValues = { "get": "GET", "set": "SET", "delete": "DELETE", "append": "APPEND", "pop" : "POP", "search": "SEARCH-KEYS", "all" : "GET-KEYS"};
		dropdown(selectValues);
	}else{
		$('select').children().remove();
		const selectValues = { "get": "GET", "set": "SET", "delete": "DELETE", "mapget": "MAPGET", "mapset" : "MAPSET", "mapdelete" : "MAPDELETE", "search": "SEARCH-KEYS", "all" : "GET-KEYS"};
		dropdown(selectValues);
	}
}

const handler = () => {
	document.getElementById('error').style.display='none';
	let radio = document.myForm.radio;
    let prev = null;
    for(var i = 0; i < radio.length; i++) {
        radio[i].onclick = function() {
            if(this !== prev) {
                prev = this;
                updateDropdown();
            }
        };
    }
}

const check = () => {
	let textBox =  $.trim( $('input[type=text]').val() );
    if (textBox == "" && $('#commands').find(":selected").text() != "GET-KEYS") {
        $("#error").show('slow');
        return false;
    }else{
    	document.getElementById('error').style.display='none';
    	return true;
    }
}