<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
        <title>Employee</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
</head>
<body>
<div class="container">
        <form action="/update_employee" method="post" modelAttribute = "employee"
              style="max-width: 320px; margin: 0 auto; font-size: larger;">
                <h3 class="form-signin-heading" align="center">Employee:</h3>
                <div class="form-group">
                        <input type="text" readonly="readonly" name="id" title="ID" size="10"
                               value="<c:out value="${employee.id}"/>">
                </div>
                <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" id="name" class="form-control" placeholder=""
                               name="name" value="<c:out value="${employee.name}"/>"
                               required autofocus style="z-index: 2;">
                </div>
                <div class="form-group">
                        <label for="dob">DOB</label>
                        <input type="text" id="dob" class="form-control" placeholder=""
                               name="dob" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${employee.dob}"/>"
                               required autofocus style="z-index: 2;">
                </div>

                <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="text" id="phone" class="form-control" placeholder=""
                               name="phone" value="<c:out value="${employee.phone}"/>"
                               required autofocus style="z-index: 2;">
                </div>

                <div class="form-group">
                        <label for="position">Position</label>
                        <br>
                        <form:select id="position" name ="position" path="position" items="${positions}" />
                </div>

                <script>document.getElementById("position").value = '${employee.position.name}';</script>

                <div class="form-group">
                        <label for="salary">Salary</label>
                        <input type="text" id="salary" class="form-control" placeholder=""
                               name="salary" value="<c:out value="${employee.salary}"/>"
                               required autofocus style="z-index: 2;">
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
        </form>
</div>
</body>
</html>
