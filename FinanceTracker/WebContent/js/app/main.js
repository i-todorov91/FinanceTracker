$(document).ready(function() {
	$('#main-logout').on("click", function(){
		console.log("asd");
		$.ajax({
			url: 'logout',
			type: 'POST',
			success: function(result){
				window.location.href='http://localhost:8080/FinanceTracker/';
			}
		});
	});
});