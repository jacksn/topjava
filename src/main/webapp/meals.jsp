<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="mealList" type="ru.javawebinar.topjava.web.MealServlet"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body class="container">
<h1 class="page-header">Список еды</h1>
<div class="container">
    <table class="table table-striped table-hover">
        <tr>
            <th class="col-sm-4">Время</th>
            <th class="col-sm-4">Описание</th>
            <th class="col-sm-4">Калории</th>
        </tr>
        <c:forEach items="${mealList}" var="meal">
            <tr class="${meal.exceed ? "danger" : "success"}">
                <td>${meal.getDateTimeString()}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>

</div>

</body>
</html>
