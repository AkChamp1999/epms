<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Education Project Management System</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 50px;
        }
        .jumbotron {
            text-align: center;
            margin-top: 50px;
        }
        .btn-lg {
            margin-top: 10px;
        }
        .btn{
        	margin: 20px 20px 0 20px;
        }
        a{
        	margin: 25px 35px 0 35px;
          	font-size: 14px;
  			color: #222;
        }    
    </style>
</head>
<body>
    <div class="container">
        <div class="jumbotron">
            <h1 class="display-4">Educational Project Management System</h1>
            <p class="lead">Empowering students and teachers to manage educational projects efficiently.</p>
            <hr class="my-4">
            <p>Register, submit, review, and download project reports!</p>
            <p class="lead">
                <a class="btn btn-primary btn-lg" href="/register-student" role="button">Register as Student</a>
                <a class="btn btn-success btn-lg" href="/register-teacher" role="button">Register as Teacher</a>
                <br>
                <a href="login-student">Already registered as Student</a>
                <a href="login-teacher">Already registered as Teacher</a>
            </p>
        </div>
    </div>
</body>
</html>
