<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Edit Ingredient</title>
    <link rel="stylesheet" type="text/css" href="/css/lib/bootstrap-3.3.7.min.css"/>
</head>
<body>
<div class="container">
    <form:form action="${pageContext.request.contextPath}/admin/update_ingredient/${ingredient.id}" method="post"
               commandName="ingredient" style="max-width: 250px; margin: 0 auto; font-size: larger;">
        <h3 class="form-signin-heading" align="center">Edit Ingredient</h3>
        <div class="form-group">
            <form:hidden path="id" readonly="true" title="id" size="10"/>
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" title="name" cssClass="form-control" size="15"/>
            <p><form:errors path="name" class="label label-danger"/></p>
        </div>

        <div class="form-group">
            <label for="unit">Unit</label>
            <br>
            <form:select path="unit" value="${ingredient.unit}" >
                <form:options items="${units}" />
            </form:select>
            <p><form:errors path="unit" class="label label-danger"/></p>
        </div>

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


