<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Restaurant</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
</head>
<body>
<form action="/menu" method="post" name="menu" style="max-width: 320px; margin: 0 auto; font-size: larger;">
    <h3 class="form-signin-heading" align="center">Restaurant <br> <b>"7 Nights by The Ocean"</b></h3>
    <input type="radio" name="menu" value="1" title="Manage employees" checked> Employees<br>
    <input type="radio" name="menu" value="2" disabled title="Not implemented yet"> Dishes<br>
    <input type="radio" name="menu" value="3" disabled title="Not implemented yet"> Menues<br>
    <input type="radio" name="menu" value="4" disabled title="Not implemented yet"> Orders<br>
    <input type="radio" name="menu" value="5" disabled title="Not implemented yet"> History<br>
    <input type="radio" name="menu" value="6" disabled title="Not implemented yet"> Stock<br>
    <div class="form-group">
        <button class="btn btn-primary btn-block" type="submit">Proceed</button>
    </div>
</form>
</body>
</html>