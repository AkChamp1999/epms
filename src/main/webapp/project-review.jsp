<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Project Review</title>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<link rel="stylesheet" href="css/page-style.css">
<script>
        function toggleMarksTextbox() {
            var reviewSelect = document.getElementById("status");
            var marksTextbox = document.getElementById("marksTextbox");

            // Check if the selected review is "Approved" (you can adjust this condition as needed)
            if (reviewSelect.value === "APPROVED") {
                marksTextbox.style.display = "block";
                document.getElementById("marks").setAttribute("required", "required");
            } else {
                marksTextbox.style.display = "none";
                document.getElementById("marks").removeAttribute("required");
                document.getElementById("marks").value=0;
            }
        }
    </script>
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
	<c:if test="${not empty project}">
		<div class="container">
			<div class="project-review-form">
				<form method="post" action="project-review" id="review-form">
					<h3>Project Details</h3>
					<p id="projectId">Project ID: ${project.projectId}</p>
					<p>Project Name: ${project.projectName}</p>
					<p>Project Description: ${project.description}</p>
					<p>
						Synopsis Document: <a
							href="/download-synopsis/${project.getStudent().getRollNo()}/${project.projectName}/${project.synopsisDocument}">${project.synopsisDocument}</a>
					</p>
					<p>
						Demo Video: <a
							href="/download-demo-video/${project.getStudent().getRollNo()}/${project.projectName}/${project.demoVideo}">${project.demoVideo}</a>
					</p>
					<p>Semester: ${project.semester}</p>

					<h3>Student Details</h3>
					<p id="rollNo">Student ID: ${project.student.rollNo}</p>
					<p>Student Name: ${project.student.fullName}</p>
					<p>Division: ${project.student.division}</p>
					<p>Course: ${project.student.course}</p>
					<p>Batch: ${project.student.batchYear}</p>

					<h3>Review</h3>
					<label for="status">Select Status:</label> 
                    <select id="status" name="status" onchange="toggleMarksTextbox()" required>
                        <option value="PENDING_FOR_REVIEW" ${project.status == 'PENDING_FOR_REVIEW' ? 'selected' : ''} disabled>PENDING_FOR_REVIEW</option>
                        <option value="APPROVED" ${project.status == 'APPROVED' ? 'selected' : ''}>APPROVED</option>
                        <option value="NEED_IMPROVEMENT" ${project.status == 'NEED_IMPROVEMENT' ? 'selected' : ''}>NEED_IMPROVEMENT</option>
                        <option value="REJECTED" ${project.status == 'REJECTED' ? 'selected' : ''}>REJECTED</option>
                    </select>
					
					<label for="remarks">Enter Comment:</label>
					<textarea id="remarks" name="remarks" rows="4" cols="50" required></textarea>

					<div class="marks-div" id="marksTextbox" style="display: none;">
						<label for="marks">Enter Marks:</label> <input type="number" id="marks" name="marks" required>
					</div>
					<div class="form-buttons">
        				<button type="submit">Submit Review</button>
        				<button class="back-button" onclick="window.history.back()">Back</button>
    				</div>
				</form>
			</div>
		</div>
	</c:if>
	<jsp:include page="footer.jsp" />
</body>
</html>