<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="authorizedUser" type="ru.javawebinar.topjava.AuthorizedUser"--%>
<html>
<head>
    <title>Java Enterprise (Topjava)</title>
    <%--<link rel="stylesheet" href="resources/css/bootstrap.min.css"/>--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
</head>
<body class="container">
<h2 class="page-header">
    Проект <a href="https://github.com/JavaWebinar/topjava09" target="_blank">Java Enterprise (Topjava)</a>
</h2>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li>
                <a class="navbar-brand" href="users">Users</a>
            </li>
            <li>
                <form class="navbar-form navbar-right" action="users" method="post">
                    <label for="loggedInUserId">Select user to log in:</label>
                    <select id="loggedInUserId" name="loggedInUserId" class="form-control">
                        <option value="1">User (id=1)</option>
                        <option value="2">Admin (id=2)</option>
                    </select>
                    <button type="submit" class="btn btn-info btn-sm">Login</button>
                </form>
            </li>
        </ul>

    </div>
</nav>
</body>
</html>
