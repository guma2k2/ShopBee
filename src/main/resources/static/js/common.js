$(document).ready(function() {
	$("#logoutLink").on("click", function(e) {
		e.preventDefault();
		document.logoutForm.submit();
	})
	accountDetail();
})
function accountDetail(){
	$("#photo").click(function(){
		location.href = this.href;
	});
}


