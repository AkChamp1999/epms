<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Topbar</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/page-style.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Educational Project Management System</a>
    
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
    
        <ul class="navbar-nav mx-auto user-name">
            <!-- Check if the student name is available in the session -->
            <c:if test="${not empty sessionScope.teacher.fullName}">
                <li class="nav-item">
                    <span class="nav-link">Welcome, ${sessionScope.teacher.fullName}!</span>
                </li>
            </c:if>
        </ul>

        <ul class="navbar-nav ml-auto">
        	<li class="nav-item">
                <a class="nav-link" href="${ContextPath}/teacher-dashboard">Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${ContextPath}/generate-report">Generate Report</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#" onclick="confirmLogout('${ContextPath}/logout-teacher')">Logout</a>
            </li>
        </ul>

    </div>

    <script>
        function confirmLogout(logoutUrl) {
            Swal.fire({
                title: 'Logout',
                text: 'Are you sure you want to logout?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, logout!',
            }).then((result) => {
                if (result.isConfirmed) {
                    // If user clicks "Yes, logout!" button, redirect to logout URL
                    window.location.href = logoutUrl;
                }
            });
        }
    </script>
	</nav>
</body>
</html>

