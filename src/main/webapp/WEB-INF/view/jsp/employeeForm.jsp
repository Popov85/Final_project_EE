<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
</head>
<body>
<h1>Edit Employee</h1>
<form:form method="POST" action="/editsave">
    <table >
        <tr>
            <td></td>
            <td><form:input path="id"/></td>
        </tr>
        <tr>
            <td>Name : </td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td>DOB :</td>
            <td><form:input path="dob"/></td>
        </tr>
        <tr>
            <td>Phone :</td>
            <td><form:input path="phone"/></td>
        </tr>

        <tr>
            <td>salary :</td>
            <td><form:input path="salary"/></td>
        </tr>

        <tr>
            <td>Position:</td>
            <td><form:input path="position"/></td>
        </tr>

        <tr>
            <td> </td>
            <td><input type="submit" value="Edit Save"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
