<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Teacher Login</title>
	<!-- Font Icon -->
	<link rel="stylesheet"
		href="fonts/material-icon/css/material-design-iconic-font.min.css">
	
	<!-- Main css -->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
    <div class="container">
		<div class="signin-content">
			<div class="signin-image">
				<figure>
					<img src="images/signin-image.jpg" alt="sing up image">
				</figure>
				<a href="register-teacher" class="signup-image-link">Create an account</a>
			</div>
			<div class="signin-form">
			
				<c:if test="${not empty loginFailed}">
					<script>
                	Swal.fire({
                    	icon: 'error',
                    	title: 'Error!',
                    	text: '${loginFailed}',
                		});
            		</script>
				</c:if>
				
				<h2 class="form-title">Teacher Log In</h2>
				<form action="login-teacher" method="post" class="register-form" id="login-form">
					<div class="form-group">
						<label for="email"><i class="zmdi zmdi-email"></i></label> <input
						type="email" name="email" id="email" placeholder="Your Email" />
					</div>
					<div class="form-group">
						<label for="password"><i class="zmdi zmdi-lock"></i></label> <input
						type="password" name="password" id="password" placeholder="Password" />
					</div>
					<div class="form-group form-button">
						<input type="submit" name="login" id="login"
						class="form-submit" value="Log in" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<br>
    <jsp:include page="footer.jsp" />
</body>
</html>