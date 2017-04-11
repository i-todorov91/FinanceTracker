function logout(){
	$.ajax({
		url: 'logout',
		type: 'POST',
		success: function(result){
			window.location.href='http://localhost:8080/FinanceTracker/';
		}
	});	
}