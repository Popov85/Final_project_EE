<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
        <title>Employees</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
</head>
<body>
<h3 class="form-signin-heading" align="center">Employees:</h3>
<details title="All existing employees:">
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
                <td><a href="/edit_employee/${employee.id}">Edit</a></td>
                <td><a href="delete_employee/${employee.id}">Delete</a></td>
        </tr>
        </c:forEach>
        </tbody>
</table>
</details>
</body>
</html>
