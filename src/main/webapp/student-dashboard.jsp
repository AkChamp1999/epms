<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Student Dashboard</title>
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
<h2>Student Dashboard</h2>

<c:choose>
    <c:when test="${empty projects}">
        <p>You don't have any projects added.</p>
    </c:when>
    <c:otherwise>
	<table>
       <thead>
            <tr>
                <th>Project Name</th>
                <th>Review Status</th>
                <th>Marks</th>
                <th>View Project</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="project" items="${projects}">
                <tr>
                    <td>${project.projectName}</td>
                    <td>${project.status == null ? 'PENDING_FOR_REVIEW' : project.status}</td>
                    <td>${project.marks == 0 ? '-' : project.marks}</td>
                    <td><a href="/view-project?projectId=${project.projectId}" class="view-link">View</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </c:otherwise>
</c:choose>
	<div class="form-buttons">
        <button onclick="location.href='/add-project'">Add Project</button>
        <button class="back-button" onclick="window.history.back()">Back</button>
    </div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>