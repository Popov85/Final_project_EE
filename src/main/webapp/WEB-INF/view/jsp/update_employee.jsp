<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
        <title>Edit Employee</title>
        <link rel="stylesheet" type="text/css" href="/css/lib/bootstrap-3.3.7.min.css"/>
</head>
<body>
<div class="container">
<h3 class="form-signin-heading" align="center">Edit Employee</h3>
<form:form id="update_employee" name="update_employee" action="${pageContext.request.contextPath}/update_employee"
           method="post" modelAttribute="employee" enctype="multipart/form-data"
           style="max-width: 320px; margin: 0 auto; font-size: larger;">

        <div class="form-group">
                <form:hidden path="id" value="${employee.id}" title="id" size="10"/>
        </div>

        <div class="form-group" align="center">
                <c:set var="photo" scope="session" value="${employee.photo}"/>
                <c:choose>
                        <c:when test="${fn:length(photo)==0}">
                                <img src="<c:url value='/img/noimage.jpg'/>" alt="NO image" height="150" width="150"/>
                        </c:when>
                        <c:otherwise>
                                <img src="/img/photo?id=${employee.id}"
                                     alt="photo" height="150" width="150"/></td>
                        </c:otherwise>
                </c:choose>
        </div>

        <div class="form-group">
                <label>Photo</label>
                <input type="file" method="POST" name="photo" title="Photo" value="${employee.photo}"/>
                <font color="red"><form:errors path="photo"/></font>
        </div>

        <div class="form-group">
                <label for="login">Login</label>
                <form:input path="login" value="${employee.login}" title="login" cssClass="form-control"/>
                <font color="red"><form:errors path="login" /></font>
        </div>

        <div class="form-group">
                <label for="password">Password</label>
                <form:password path="password" value="${employee.password}" title="login" cssClass="form-control"/>
                <font color="red"><form:errors path="password" /></font>
        </div>

        <div class="form-group">
                <label for="name">Name</label>
                <form:input path="name" value="${employee.name}" title="name" cssClass="form-control"/>
                <font color="red"><form:errors path="name"/></font>
        </div>

        <div class="form-group">
                <form:label cssClass="control-label" path="dob">DOB</form:label>
                <div class="controls">
                        <input type="date" path="dob" class= "date" name = "dob" cssClass="form-control"
                               value = "<fmt:formatDate value="${employee.dob}" pattern="yyyy-MM-dd" />"/>
                        <font color="red"><form:errors path="dob"/></font>
                </div>
        </div>

        <div class="form-group">
                <label for="phone">Phone</label>
                <form:input path="phone" value="${employee.phone}" title="phone" cssClass="form-control"/>
                <font color="red"><form:errors path="phone"/></font>
        </div>

        <div class="form-group">
                <label for="position">Position</label>
                        <br>
                <form:select path="position" value="${employee.position}" items="${positions}" />
                <font color="red"><form:errors path="position"/></font>
                <font color="red"><c:out value="${integrityViolationError}"/></font>
        </div>

        <div class="form-group">
                <label for="salary">Salary</label>
                <form:input path="salary" value="${employee.salary}" title="salary" cssClass="form-control"/>
                <font color="red"><form:errors path="salary"/></font>
        </div>

        <font color="red"><c:out value="${constraintViolationError}"/></font>
        <font color="red"><c:out value="${unexpectedError}"/></font>

        <div class="form-group">
                <button class="btn btn-primary btn-block" type="submit">Save</button>
        </div>

        <div class="form-group">
                <div class="row">
                        <div class="col-md-6">
                                <a class="hyperlink" href="/admin/employees">See all</a>
                        </div>
                        <div class="col-md-6" align="right">
                                <a class="hyperlink" href="/">Home</a>
                        </div>
                </div>
        </div>
</form:form>
</div>
</body>
</html>
