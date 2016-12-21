<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <%--<link rel="stylesheet" href="resources/css/bootstrap.min.css"/>--%>
</head>
<body class="container">
<h2 class="page-header">${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
<nav class="navbar navbar-default">
    <div class="navbar-header">
        <ul class="nav navbar-nav">
            <li>
                <a class="navbar-brand" href="${pageContext.request.contextPath}">Home</a>
            </li>
            <li>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/meals">Meal list</a>
            </li>
        </ul>
    </div>
</nav>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<div class="panel panel-success">
    <div class="panel-body">
        <form id="mealEditForm" name="mealEditForm" class="form-horizontal" method="post" action="meals">
            <div class="form-group">
                <label for="dateTime" class="control-label col-xs-4">Дата:</label>
                <div class="col-xs-4">
                    <input type="hidden" id="id" name="id" value="${meal.id}"/>
                    <input id="dateTime"
                           name="dateTime"
                           class="form-control"
                           type="datetime-local"
                           value="${meal.dateTime}"
                           required/>
                </div>
            </div>

            <div class="form-group">
                <label for="description" class="control-label col-xs-4">Описание:</label>
                <div class="col-xs-4">
                    <input id="description"
                           name="description"
                           class="form-control"
                           type="text"
                           value="${meal.description}"
                           required/>
                </div>
            </div>

            <div class="form-group">
                <label for="calories" class="control-label col-xs-4">Калории:</label>
                <div class="col-xs-4">
                    <input id="calories"
                           name="calories"
                           class="form-control"
                           type="number"
                           value="${meal.calories}"
                           required/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-xs-offset-4 col-xs-1">
                    <button type="submit" id="saveMeal" class="btn btn-success">Save</button>
                </div>
                <div class="col-xs-2">
                    <button class="btn btn-danger" onclick="window.history.back()">Cancel</button>
                </div>
            </div>

        </form>
    </div>
</div>
</body>
</html>
