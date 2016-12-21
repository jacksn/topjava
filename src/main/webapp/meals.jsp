<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"--%>
<%--@elvariable id="meals" type="java.util.List"--%>
<%--@elvariable id="user" type="ru.javawebinar.topjava.model.User"--%>
<%--@elvariable id="dateFrom" type="java.time.LocalDate"--%>
<%--@elvariable id="dateTo" type="java.time.LocalDate"--%>
<%--@elvariable id="timeFrom" type="java.time.LocalTime"--%>
<%--@elvariable id="timeTo" type="java.time.LocalTime"--%>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <%--<link rel="stylesheet" href="resources/css/bootstrap.min.css"/>--%>
</head>
<body class="container">
<h2 class="page-header">Meal list for ${user.name} (id=${user.id})</h2>
<nav class="navbar navbar-default">
    <div class="navbar-header">
        <ul class="nav navbar-nav">
            <li>
                <a class="navbar-brand" href="${pageContext.request.contextPath}">Home</a>
            </li>
        </ul>
    </div>
</nav>
<div class="row panel">
    <div class="navbar-header">
        <form class="navbar-form navbar-left" role="form" name="dateTimeFilter" id="dateTimeFilter" method="post" action="meals">
            <div class="form-group">
                <label for="dateFrom" class="control-label">Date:</label>
                <input type="date" class="form-control" id="dateFrom" name="dateFrom" value="${dateFrom}">
                <label for="dateTo" class="control-label"> to </label>
                <input type="date" class="form-control" id="dateTo" name="dateTo" value="${dateTo}">
            </div>
            <div class="form-group">
                <label for="timeFrom" class="control-label">Time:</label>
                <input type="time" class="form-control" id="timeFrom" name="timeFrom" value="${timeFrom}">
                <label for="timeTo" class="control-label"> to </label>
                <input type="time" class="form-control" id="timeTo" name="timeTo" value="${timeTo}">
            </div>
            <button type="submit" class="btn btn-info">Filter</button>
        </form>
    </div>
</div>
<div>
    <div class="row" style="margin-bottom: 15px">
        <div class="col-md-2">
            <a class="btn btn-success" href="meals?action=create" role="button">Create</a>
        </div>
    </div>
    <c:choose>
        <c:when test="${meals.size() > 0}">
            <table class="table table-striped table-hover">
                <tr>
                    <th class="col-sm-2">Date</th>
                    <th class="col-sm-2">Description</th>
                    <th class="col-sm-2">Calories</th>
                    <th class="col-sm-1"></th>
                    <th class="col-sm-1"></th>
                </tr>
                <c:forEach items="${meals}" var="meal">
                    <tr class="${meal.exceed ? "text-danger" : "text-success"}">
                        <td><c:out value='${fn:formatDateTime(meal.dateTime)}'/></td>
                        <td><c:out value='${meal.description}'/></td>
                        <td><c:out value='${meal.calories}'/></td>
                        <td class="text-center">
                            <a class="btn btn-warning btn-sm"
                               href="?action=update&id=<c:out value='${meal.id}'/>"
                               role="button">Edit</a>
                        </td>
                        <td class="text-center">
                            <a class="btn btn-danger btn-sm"
                               href="?action=delete&id=<c:out value='${meal.id}'/>"
                               role="button">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h3>Meal list is empty</h3>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>

