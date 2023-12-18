<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Generate Report</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="stylesheet" href="css/teacher-dashboard.css">
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
	<div class="nested-div">
	<h2>Generate Report</h2>
	<form method="get" action="/download-report" autocomplete="off">
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
        <button type="submit" class="get-project-button">Download Report</button>
        <button class="back-button" onclick="window.location.href='/teacher-dashboard'">Back</button>
    </div>
	</form>
    </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>