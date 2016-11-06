<%--
  Created by IntelliJ IDEA.
  User: Andrey
  Date: 11/6/2016
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
        <title>Title</title>
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
                <th>Edit</th>
                <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee">
                <tr>
                        <td><c:out value="${employee.id}"/></td>
                        <td><c:out value="${employee.name}"/></td>
                        <td><c:out value="${employee.dob}"/></td>
                        <td><c:out value="${employee.phone}"/></td>
                        <td><c:out value="${employee.position.name}"/></td>
                        <td><c:out value="${employee.salary}"/></td>
                        <td><a href="/employee?employee=${employee.id}">Open</a></td>
                </tr>
        </c:forEach>
        </tbody>
</table>
</body>
</html>
