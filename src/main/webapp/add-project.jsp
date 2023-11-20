<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Project</title>
    <link rel="stylesheet" href="css/add-project.css">
</head>
<body>
	<div class="container">
	<div class="add-project-form">
    <h2>Add Project</h2>
    <form method="post" action="/add-project">
        <label for="projectName">Project Name:</label>
        <input type="text" id="projectName" name="projectName" required>
        <br>
        <label for="description">Description:</label>
        <textarea id="description" name="description" rows="4" cols="50" required></textarea>
        <br>
        <label for="synopsisDocument">Synopsis Document (Upload):</label>
        <input type="file" id="synopsisDocument" name="synopsisDocument">
        <br>
        <label for="demoVideo">Demo Video (Upload):</label>
        <input type="file" id="demoVideo" name="demoVideo">
        <br>
        <button type="submit">Submit Project</button>
    </form>
    </div>
    </div>
</body>
</html>