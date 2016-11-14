<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
        <title>Title</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
</head>
<body>
<table class="table table-bordered" align="center">
        <thead>
        <tr>
                <th>Id</th>
                <th>Name</th>
                <th>DOB</th>
                <th>Phone</th>
                <th>Position</th>
                <th>Salary</th>
                <th>Edit</th>
                <th>EditNew</th>
                <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee">
        <tr>
                <td><c:out value="${employee.id}"/></td>
                <td><c:out value="${employee.name}"/></td>
                <td><fmt:formatDate pattern='MM/dd/yyyy' value='${employee.dob}'/></td>
                <td><c:out value="${employee.phone}"/></td>
                <td><c:out value="${employee.position.name}"/></td>
                <td><c:out value="${employee.salary}"/></td>
                <td><a href="/edit/${employee.id}">Edit</a></td>
                <td><a href="deleteemp/${employee.id}">Delete</a></td>
        </tr>
        </c:forEach>
        </tbody>
</table>
</body>
</html>
