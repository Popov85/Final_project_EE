<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 11/6/2016
  Time: 8:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
        <title>Employee</title>
</head>
<body>
<table class="table table-bordered">
        <thead>
        <tr>
                <th>Id</th>
                <th>Name</th>
                <th>DOB</th>
                <th>Phone</th>
                <th>Position</th>
                <th>Salary</th>
        </tr>
        </thead>
        <tbody>
        <tr>
                <td>${employee.id}</td>
                <td>${employee.name}</td>
                <td>${employee.dob}</td>
                <td>${employee.phone}</td>
                <td>${employee.position.name}</td>
                <td>${employee.salary}</td>
        </tr>
        </tbody>
</table>
</body>
</html>
