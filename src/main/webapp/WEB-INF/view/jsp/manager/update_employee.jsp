<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Edit Employee</title>
    <link rel="stylesheet" type="text/css" href="/css/lib/bootstrap-3.3.7.min.css"/>
</head>
<body>
<div class="container">
    <h3 class="form-signin-heading" align="center">Edit Employee</h3>
    <form:form id="update_employee" name="update_employee" action="${pageContext.request.contextPath}/admin/update_employee"
               method="post" modelAttribute="employee" enctype="multipart/form-data"
               style="max-width: 250px; margin: 0 auto; font-size: larger;">

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
            <spring:bind path="photo">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Photo</label>
                    <input type="file" method="POST" name="photo" title="Photo" value="${employee.photo}" id="photo"/>
                    <label class="control-label" for="photo">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Name</label>
                    <input type="text"  name="name" title="Name" value="${employee.name}" id="name"/>
                    <label class="control-label" for="name">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="login">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Login</label>
                    <form:input path="login" value="${employee.login}" type="text" name="login" id="login"
                                class="form-control"/>
                    <label class="control-label"
                           for="login">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="login">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Password</label>
                    <form:password path="password" value="${employee.password}" title="login" cssClass="form-control"
                                   name="password" id="password"/>
                    <label class="control-label"
                           for="password">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="dob">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>DOB</label>
                    <input type="date" path="dob" class="date" id="dob" name="dob" cssClass="form-control"
                           value="<fmt:formatDate value="${employee.dob}" pattern="yyyy-MM-dd" />"/>
                    <label class="control-label"
                           for="dob">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="phone">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Phone</label><br/>
                    <form:input path="phone" value="${employee.phone}" title="phone" cssClass="form-control"
                               name="phone" id="phone"/>
                    <label class="control-label" for="phone">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="position">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Position</label><br/>
                    <form:select path="position" value="${employee.position}" items="${positions}"
                                 cssClass="form-control" name="position" id="position"
                                 width="150" style="width: 150px"/>
                    <label class="control-label" for="position">${status.error ? status.errorMessage : ''}</label>
                    <p class="label label-danger"><c:out value="${integrityViolationError}"/></p>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="salary">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Salary</label><br/>
                    <form:input path="salary" value="${employee.salary}" title="Salary" cssClass="form-control" name="salary" id="salary"/>
                    <label class="control-label" for="salary">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <p class="label label-danger"><c:out value="${constraintViolationError}"/></p>
        <p class="label label-danger"><c:out value="${unexpectedError}"/></p>

        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">Save</button>
        </div>

        <div class="form-group">
            <div class="row">
                <div class="col-md-6">
                    <a class="hyperlink" href="/admin/employees">See all</a>
                </div>
                <div class="col-md-6" align="right">
                    <a class="hyperlink" href="/admin">Home</a>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>
