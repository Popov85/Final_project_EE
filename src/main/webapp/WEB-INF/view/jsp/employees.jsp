<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
        <title>Employees</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.13/css/jquery.dataTables.css"/>
        <link rel="stylesheet" type="text/css" href="/css/filterDisabled.css"/>
        <script src ="/js/lib/jquery-1.12.1.js"/>
        <script src ="/js/lib/bootstrap-3.3.7.min.js"/>
        <script src ="/js/lib/jquery.dataTables-1.10.12.min.js"/>

</head>
<body>
<h3 class="form-signin-heading" align="center">Employees:</h3>
<table id="employees" name="employees" class="display" align="center">
        <thead>
        <tr>
                <th>Id</th>
                <th>Name</th>
                <th>DOB</th>
                <th>Phone</th>
                <th>Position</th>
                <th>Salary</th>
                <th>Photo</th>
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
                        <td align="center">
                                <c:set var="photo" scope="session" value="${employee.photo}"/>
                                <c:choose>
                                <c:when test="${fn:length(photo)==0}">
                                        <img src="<c:url value='/img/noimage.jpg'/>"
                                             alt="NO image" height="100" width="100"/>
                                </c:when>
                                <c:otherwise>
                                <img src="/img/photo?id=${employee.id}"
                                     alt="photo" height="100" width="100"/>
                        </td>
                        </c:otherwise>
                        </c:choose>
                        <td>
                                <a href="/edit_employee/${employee.id}">Edit</a>
                        </td>
                        <td>
                                <a href="/delete_employee/${employee.id}">Delete</a>
                        </td>
                </tr>
        </c:forEach>
        </tbody>
</table>
</body>
</html>
