<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Project</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="/css/page-style.css">
</head>
<body>
	<%

	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
	
	if(session.getAttribute("student")==null){
		response.sendRedirect("/login-student");
	}
	
	%>
	<jsp:include page="student-header.jsp" /> 
	<div class="container">
	<div class="nested-div">
    <h2>Add Project</h2>
    
    <c:if test="${not empty projectSubmissionFailed}">
		<script>
          Swal.fire({
            icon: 'error',
            title: 'Error!',
            text: '${projectSubmissionFailed}',
            }).then(function() {
               	window.location = "add-project";
            });
        </script>
	</c:if>
				
	<c:if test="${not empty projectSubmissionSuccess}">
		<script>
           Swal.fire({
           icon: 'success',
           title: 'Success!',
           text: '${projectSubmissionSuccess}',
           }).then(function() {
           	window.location = "student-dashboard";
           });
        </script>
	</c:if>
	
    <form method="post" action="/add-project" enctype="multipart/form-data">
        <label for="projectName">Project Name:</label>
        <input type="text" id="projectName" name="projectName" required>
        <label for="description">Description:</label>
        <textarea id="description" name="description" rows="4" cols="50" maxlength="1000" placeholder="Max description length is 1000 characters" required></textarea>
        <label for="synopsisDocument">Synopsis Document (Upload):</label>
        <input type="file" id="synopsisDocument" name="synopsisDocument" accept=".docx, .pdf" required>
        <label for="demoVideo">Demo Video (Upload):</label>
        <input type="file" id="demoVideo" name="demoVideo" accept=".mp4" required>
        <select class="form-control" id="semester" name="semester" required>
			<option value="" selected disabled>Semester</option>
		    <option value="SEM-I">SEM-I</option>
		    <option value="SEM-II">SEM-II</option>
		    <option value="SEM-III">SEM-III</option>
		    <option value="SEM-IV">SEM-IV</option>
		</select>
		<div class="form-buttons">
        <button type="submit">Submit Project</button>
        <button class="back-button" onclick="window.location.href='/student-dashboard'">Back</button>
        </div>
    </form>
    
    </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>