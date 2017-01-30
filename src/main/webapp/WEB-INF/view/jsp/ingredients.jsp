<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
        <th>Unit</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ingredients}" var="ingredient">
    <tr>
        <td><c:out value="${ingredient.id}"/></td>
        <td><c:out value="${ingredient.name}"/></td>
        <td><c:out value="${ingredient.unit.name}"/></td>
        <td><a href="/admin/update_ingredient/${ingredient.id}">Edit</a></td>
        <td><a href="/admin/delete_ingredient/${ingredient.id}">Delete</a></td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

