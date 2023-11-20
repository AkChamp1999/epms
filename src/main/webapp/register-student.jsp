<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Registration</title>
	<!-- Font Icon -->
	<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.32/dist/sweetalert2.all.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.7.32/dist/sweetalert2.min.css" rel="stylesheet">
	<!-- Main css -->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="header.jsp" />
	<br>
	<div class="container">
		<div class="signup-content">
			<div class="signup-form">
			
				<c:if test="${not empty errorMessage}">
					<script>
                	Swal.fire({
                    	icon: 'error',
                    	title: 'Error!',
                    	text: '${errorMessage}',
                		});
            		</script>
				</c:if>
				
				<c:if test="${not empty successMessage}">
					<script>
                	Swal.fire({
                    	icon: 'success',
                    	title: 'Success!',
                    	text: '${successMessage}',
                		}).then(function() {
                		    window.location = "login-student";
                		});
            		</script>
				</c:if>
				
				<h2 class="form-title">Registration From</h2>
				<form method="post" action="register-student" class="register-form" id="register-form">
					<div class="form-group">
						<label for="rollNo"><i class="fa fa-id-card"></i></label> <input
							type="number" name="rollNo" id="rollNo" placeholder="Your Roll No" />
					</div>					
					<div class="form-group">
						<label for="fullName"><i
							class="zmdi zmdi-account material-icons-name"></i></label> <input
							type="text" name="fullName" id="fullName" placeholder="Your Full Name" />
					</div>
					<div class="form-group">
						<label for="email"><i class="zmdi zmdi-email"></i></label> <input
							type="email" name="email" id="email" placeholder="Your Email" />
					</div>
					<div class="form-group">
						<label for="password"><i class="zmdi zmdi-lock"></i></label> <input
							type="password" name="password" id="password" placeholder="Password" />
					</div>
					<div class="form-group">
		                <select class="form-control" id="course" name="course" required>
		                  <option value="" selected disabled>Course</option>
		                  <option value="MCA">MCA</option>
		                  <option value="MBA">MBA</option>
		                </select>
              		</div>
            		<div class="form-group">
                		<select class="form-control" id="division" name="division" required>
                			<option value="" selected disabled>Division</option>
		                    <option value="A">A</option>
		                    <option value="B">B</option>
		                    <option value="C">C</option>
                		</select>
            		</div>
					<div class="form-group form-button">
						<input type="submit" name="register" id="register"
							class="form-submit" value="Register" />
					</div>
				</form>
			</div>
			<div class="signup-image">
				<figure>
					<img src="images/signup-image.jpg" alt="sing up image">
				</figure>
				<a href="login-student" class="signup-image-link">I am already member</a>
			</div>
		</div>
	</div>
	<br>
    <jsp:include page="footer.jsp" />
</body>
</html>