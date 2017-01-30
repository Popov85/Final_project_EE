<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title>New Ingredient</title>
    <link rel="stylesheet" type="text/css" href="/css/lib/bootstrap-3.3.7.min.css"/>
</head>
<body>
<div class="container">
    <form:form action="/admin/save_ingredient" method="post" modelAttribute="ingredient"
               style="max-width: 250px; margin: 0 auto; font-size: larger;">
    <h3 class="form-signin-heading" align="center">New Ingredient</h3>
    <div class="form-group">
        <form:hidden path="id" readonly="true" title="id" size="10"/>
    </div>


    <spring:bind path="name">
    <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
        <label>Name</label>
        <form:input path="name" type="text" id="input" class="form-control"/>
        <label class="control-label" for="input">${status.error ? status.errorMessage : ''}</label>
    </div>
    </spring:bind>


    <spring:bind path="unit">
    <div class="form-group ${status.error ? 'has-error has-feedback' : ''}">
        <label>Unit</label>
        <form:select path="unit" items="${units}" id="unitInput" class="form-control"/>
        <label class="control-label" for="unitInput">${status.error ? status.errorMessage : ''}</label>
    </div>
    </spring:bind>


    <div class="form-group">
        <div class="btn-group btn-group-justified">
            <div class="btn-group">
                <button type="submit" class="btn btn-primary ">Save</button>
            </div>
            <div class="btn-group">
                <a href="/admin/ingredients">
                    <input type="button" class="btn btn-default" value="Cancel"/>
                </a>
            </div>
        </div>

        <div class="form-group">
            <div class="row">
                <div class="col-md-6">
                    <a class="hyperlink" href="/admin/ingredients">See all</a>
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

