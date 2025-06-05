<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<body>
	    <h1>reset</h1>
	
	    <label>email</label>
	    <input id="email" name="email" type="email" value="" />
	    <button type="submit">reset</button>
	
	<a>
	    registration
	</a>
	<a>login</a>
	
	<script src="jquery.min.js"></script>
	<script th:inline="javascript">
	var serverContext = [[@{/}]];
	function resetPass(){
	    var email = $("#email").val();
	    $.post(serverContext + "user/resetPassword",{email: email} ,
	      function(data){
	          window.location.href = 
	           serverContext + "login?message=" + data.message;
	    })
	    .fail(function(data) {
	    	if(data.responseJSON.error.indexOf("MailError") > -1)
	        {
	            window.location.href = serverContext + "emailError.html";
	        }
	        else{
	            window.location.href = 
	              serverContext + "login?message=" + data.responseJSON.message;
	        }
	    });
	}
	
	</script>
	</body>
</html>