<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
        <title>Home</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css">
</head>
<style type="text/css">
        #header1 {float:left;}
        #header2 {float:right;}
        .footer1 {
                background-color:#606060 ;
                color: #ffffff;
                height: 20px;
                bottom:0px;
                position:fixed;
                width:100%;
        }
        .footer2 {
                background-color:#b5dcb3 ;
                color: #ffffff;
                height: 20px;
                bottom:20px;
                position:fixed;
                width:100%;
        }
</style>
<body>
<table width="100%" border="0">
        <tr>
                <td colspan="2" bgcolor="#b5dcb3">
                        <div id="header1">Welcome, guest!</div><div id = "header2"> <a href="/sign_in">Sign in</a></div>
                </td>
        </tr>
        <tr valign="top">
                <td bgcolor="white" width="50%" align="center">
                        <img src="http://www.logodesignconsultant.com/images/category_based_logos/restaurant_logo_design4.gif"
                             alt="badge" height="174px" width="300px">
                </td>
        </tr>
        <tr valign="top">
                <td bgcolor="#aaa" width="100%">
                        <details title="All existing employees:">
                                <label for="menu">Our menus:</label>
                                <table id="menu" class="table table-bordered" align="center">
                                        <thead>
                                        <tr>
                                                <th>Id</th>
                                                <th>Menu</th>
                                                <th>Dish</th>
                                                <th>Category</th>
                                                <th>Price</th>
                                                <th>Weight</th>
                                                <th>Like</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${menus}" var="menu">
                                                <tr>
                                                        <td><c:out value="${menu.id}"/></td>
                                                        <td><c:out value="${menu.name}"/></td>
                                                        <c:forEach items="${menu.dishes}" var="dish">
                                                                <tr>
                                                                        <td><c:out value="${dish.name}"/></td>
                                                                        <td><c:out value="${dish.catrgory}"/></td>
                                                                        <td><c:out value="${dish.price}"/></td>
                                                                        <td><c:out value="${dish.weightt}"/></td>
                                                                        <td><a href="/like_dish/${dish.id}">Like</a></td>
                                                                </tr>
                                                        </c:forEach>
                                                </tr>
                                        </c:forEach>
                                        </tbody>
                                </table>
                        </details>
                </td>
        </tr>
        <tr>
                <td class = "footer2">
                        <p align="center"><b>Address:</b> Ukraine, Kiev, Khreshatyk str, 10 <b>Tel.:</b> +380442365612, <b>Mob.:</b> +380501235231</p>
                </td>
        </tr>
        <tr>
                <td class = "footer1" colspan="2">
                        <p align="center">Copyright © Popov A., 2016</p>
                </td>
        </tr>
</table>

</body>
</html>
