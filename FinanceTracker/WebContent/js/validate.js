	function validateEmail(email){
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(email);
	}
	
	function validatePassword(password){
		var re = /(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\*^&+=]).{8,}/;
	    return re.test(password);
	}
	
	function validateString(string){
		return !(string == "");
	}