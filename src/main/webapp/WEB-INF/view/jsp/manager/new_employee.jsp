<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>New Employee</title>
    <link rel="stylesheet" type="text/css" href="/css/lib/bootstrap-3.3.7.min.css"/>
</head>
<body>
<div class="container">
    <h3 class="form-signin-heading" align="center">New Employee:</h3>
    <form:form id="new_employee" name="new_employee" action="${pageContext.request.contextPath}/admin/save_employee"
               method="post" modelAttribute="employee" enctype="multipart/form-data"
               style="max-width: 250px; margin: 0 auto; font-size: larger;">
        <div class="form-group">
            <form:hidden path="id" title="id" size="10"/>
        </div>

        <div class="form-group">
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Name</label>
                    <form:input path="name" type="text" id="name" class="form-control"/>
                    <label class="control-label" for="name">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="login">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Login</label>
                    <form:input path="login" type="text" id="inputLogin" class="form-control"/>
                    <label class="control-label" for="inputLogin">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Password</label>
                    <form:input path="password" type="password" id="inputPassword" class="form-control"/>
                    <label class="control-label" for="inputPassword">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="dob">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>DOB</label><br/>
                    <input type="date" path="dob" class="date" name="dob" id="dob"/>
                    <label class="control-label" for="dob">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="phone">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Phone</label><br/>
                    <form:input path="phone" title="phone" cssClass="form-control" id="inputPhone"/>
                    <label class="control-label" for="inputPhone">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <label>Photo</label>
            <input type="file" method="POST" accept=".jpg" name="photo" title="Select a photo"/>
            <p><form:errors path="photo" class="label label-danger"/></p>
        </div>

        <div class="form-group">
            <spring:bind path="position">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Position</label><br/>
                    <form:select path="position" items="${positions}" cssClass="form-control" id="inputPosition"
                                 width="150" style="width: 150px"/>
                    <label class="control-label" for="inputPosition">${status.error ? status.errorMessage : ''}</label>
                </div>
            </spring:bind>
        </div>

        <div class="form-group">
            <spring:bind path="salary">
                <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
                    <label>Salary</label><br/>
                    <form:input path="salary" title="Salary" cssClass="form-control" id="inputSalary"/>
                    <label class="control-label" for="inputSalary">${status.error ? status.errorMessage : ''}</label>
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

