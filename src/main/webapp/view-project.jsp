<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>View Project</title>
    <link rel="stylesheet" href="/css/page-style.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        #updateButton:disabled {
            background-color: #dddddd; /* Change to the desired disabled button color */
            cursor: not-allowed;
        }
    </style>
    <script>
    	function disableUpdateButton(){
    		var updateButton = document.getElementById('updateButton');
            var projectStatus = "${project.status}"
    		if (projectStatus === 'APPROVED') {
                updateButton.disabled = true;
            }else{
            	updateButton.disabled = false;
            }
    	}
    	
        function handleUpdateButton() {
            var updateButton = document.getElementById('updateButton');
            var projectStatus = "${project.status}";

            if (projectStatus === 'APPROVED') {
                updateButton.title = "Project has been approved and cannot be updated.";
            } else {
                updateButton.title = "";
            }
        }
    </script>
</head>
<body onload="disableUpdateButton()">
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
	<div class="view-project-form">
        <h3>Project Details</h3>
		<p>Project ID: ${project.projectId}</p>
        <p>Project Name: ${project.projectName}</p>
        <p>Project Description: ${project.description}</p>
        <p>Synopsis Document: <a href="/download-synopsis/${project.getStudent().getRollNo()}/${project.projectName}/${project.synopsisDocument}">${project.synopsisDocument}</a></p>
        <p>Demo Video: <a href="/download-demo-video/${project.getStudent().getRollNo()}/${project.projectName}/${project.demoVideo}">${project.demoVideo}</a></p>
        <p>Semester: ${project.semester}</p>
        <c:if test="${not empty latestReview}">
        	<h3>Project Review Details</h3>
        	<p>Status        : ${project.status}</p>
        	<p>Marks         : ${project.marks == 0 ? '-' : project.marks}</p>
            <p>Latest Remark : ${latestReview.remarks}</p>
            <p>Review done by: ${teacher.fullName}</p>
         </c:if>
        <div class="form-buttons">
        <a href="/update-project?projectId=${project.projectId}" class="review-link">
        	<button id="updateButton" onmouseover="handleUpdateButton()">Update Project</button>
    	</a>
        <button class="back-button" onclick="window.history.back()">Back</button>
        </div>
    </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>