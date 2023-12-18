<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Teacher Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        .mandatory {
            color: red;
        }
    </style>
</head>
<body>
	<%

	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
	
	if(session.getAttribute("teacher")==null){
		response.sendRedirect("/login-teacher");
	}
	
	%>
	<jsp:include page="teacher-header.jsp" />
	<div class="container">
	<div class="teacher-dashboard-form">
	<c:if test="${not empty errorMessage}">
		<script>
           Swal.fire({
        	icon: 'error',
           	title: 'Error!',
           	text: '${errorMessage}',
           });
        </script>
	</c:if>
	<c:if test="${not empty reviewSuccess}">
		<script>
              Swal.fire({
                 icon: 'success',
                 title: 'Success!',
                 text: '${reviewSuccess}',
                });
        </script>
	</c:if>
	<h2>Teacher Dashboard</h2>
	<form method="get" action="/get-projects">
	<label for="course">Select Course<span class="mandatory">*</span>:</label>
	<select class="form-control" id="course" name="course" required>
		<option value="" selected disabled>Course</option>
   		<option value="MCA">MCA</option>
   		<option value="MBA">MBA</option>
	</select>

	<label for="semester">Select Semester<span class="mandatory">*</span>:</label>
    <select class="form-control" id="semester" name="semester" required>
		<option value="" selected disabled>Semester</option>
		<option value="SEM-I">SEM-I</option>
		<option value="SEM-II">SEM-II</option>
		<option value="SEM-III">SEM-III</option>
		<option value="SEM-IV">SEM-IV</option>
	</select>
	
	<label for="batchYear">Select Batch<span class="mandatory">*</span>:</label>
	<select class="form-control" id="batchYear" name="batchYear" required>
        <option value="" selected disabled>Batch</option>
		<option value="2023-batch">2023-batch</option>
		<option value="2024-batch">2024-batch</option>
		<option value="2025-batch">2025-batch</option>
    </select>
    
    <label for="division">Select Division:</label>
	<select class="form-control" id="division" name="division">
        <option value="" selected disabled>Division</option>
		<option value="A">A</option>
		<option value="B">B</option>
		<option value="C">C</option>
    </select>
    
    <label for="status">Select Status:</label>
    <select class="form-control" id="status" name="status">
       	<option value="" selected disabled>Status</option>
        <option value="PENDING_FOR_REVIEW">PENDING_FOR_REVIEW</option>
        <option value="APPROVED">APPROVED</option>
        <option value="NEED_IMPROVEMENT">NEED_IMPROVEMENT</option>
        <option value="REJECTED">REJECTED</option>
    </select>
	<div class="form-buttons">
        <button type="submit" class="get-project-button">Get Projects</button>
        <button class="back-button" onclick="window.history.back()">Back</button>
    </div>
	</form>
    </div>
    </div>
    <c:if test="${not empty projects}">
    <table>
        <thead>
            <tr>
                <th>Project ID</th>
                <th>Student Roll No</th>
                <th>Student Name</th>
                <th>Project Name</th>
                <th>Marks</th>
                <th>Status</th>
                <th>Review</th>
            </tr>
         </thead>

         <tbody>
            <c:forEach var="project" items="${projects}">
                <tr>
                    <td>${project.projectId}</td>
                    <td>${project.student.rollNo}</td>
                    <td>${project.student.fullName}</td>
                    <td>${project.projectName}</td>
                    <td>${project.marks == 0 ? '-' : project.marks}</td>
                    <td>${project.status}</td>
					<td>
						<c:choose>
                            <c:when test="${project.status ne 'APPROVED'}">
                                <a href="/project-review?projectId=${project.projectId}" class="review-link">Review</a>
                            </c:when>
                            <c:otherwise>
                                <span class="review-link" style="color: gray;">Review</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
         </tbody>
    </table>
    </c:if>
    <jsp:include page="footer.jsp" />
</body>
</html>