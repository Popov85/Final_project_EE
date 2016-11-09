<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Ingredient</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
</head>
<body>
<div class="container">
    <form:form action="save_ingredient" method="post" modelAttribute="ingredient" style="max-width: 320px; margin: 0 auto; font-size: larger;">
        <h3 class="form-signin-heading" align="center">Ingredient:</h3>
        <div class="form-group">
            <form:input path="id" readonly="true" title="id" size="10"/>
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" title="name" cssClass="form-control"/>
        </div>

        <div class="form-group">
            <label for="unit">Unit</label>
            <form:input path="unit" title="unit" cssClass="form-control"/>
        </div>

        <div class="form-group">
            <button class="btn btn-primary btn-block" type="submit">Save</button>
        </div>

        <div class="form-group">
            <div class="row">
                <div class="col-md-6">
                    <a class="hyperlink" href="/employees">See all</a>
                </div>
                <div class="col-md-6" align="right">
                    <a class="hyperlink" href="/index.jsp">Home</a>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>

