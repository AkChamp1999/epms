<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Project</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/page-style.css">
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
	<div class="update-project-div">
    <h2>Update Project</h2>
    <form method="post" action="/update-project/${project.projectId}" enctype="multipart/form-data">
        <label for="projectName">Project Name:</label>
        <input type="text" id="projectName" name="projectName" value="${project.projectName}">
        <label for="description">Description:</label>
        <textarea id="description" name="description" rows="4" cols="50" maxlength="1000" placeholder="Max description length is 1000 characters">${project.description}</textarea>
        <label for="synopsisDocument">Synopsis Document (Upload):</label>
        <input type="file" id="synopsisDocument" name="synopsisDocument" value="${project.synopsisDocument}" accept=".docx, .pdf">
        <label for="demoVideo">Demo Video (Upload):</label>
        <input type="file" id="demoVideo" name="demoVideo" value="${project.demoVideo}" accept=".mp4">
        <select class="form-control" id="semester" name="semester">
			<option value="" selected disabled>${project.semester}</option>
		    <option value="SEM-I">SEM-I</option>
		    <option value="SEM-II">SEM-II</option>
		    <option value="SEM-III">SEM-III</option>
		    <option value="SEM-IV">SEM-IV</option>
		</select>
		<div class="form-buttons">
        <button type="submit">Update</button>
        <button class="back-button" onclick="window.history.back()">Back</button>
        </div>
    </form>
    
    </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>