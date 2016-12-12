<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"--%>
<%--@elvariable id="mealList" type="java.util.List"--%>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Список еды</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body class="container">
<h1 class="page-header">Список еды</h1>
<div>
    <div class="row" style="margin-bottom: 15px">
        <div class="col-md-2">
            <a class="btn btn-success" href="meals?action=add" role="button">Добавить запись</a>
        </div>
    </div>
    <c:choose>
        <c:when test="${mealList.size() > 0}">
            <table class="table table-striped table-hover">
                <tr>
                    <th class="col-sm-2">Время</th>
                    <th class="col-sm-2">Описание</th>
                    <th class="col-sm-2">Калории</th>
                    <th class="col-sm-1"></th>
                    <th class="col-sm-1"></th>
                </tr>
                <c:forEach items="${mealList}" var="meal">
                    <tr class="${meal.exceed ? "text-danger" : "text-success"}">
                        <td><c:out value='${DateTimeUtil.formatDateTime(meal.dateTime)}'/></td>
                        <td><c:out value='${meal.description}'/></td>
                        <td><c:out value='${meal.calories}'/></td>
                        <td class="text-center">
                            <a class="btn btn-warning btn-xs"
                               href="?action=edit&id=<c:out value='${meal.id}'/>"
                               role="button">Изменить</a>
                        </td>
                        <td class="text-center">
                            <a class="btn btn-danger btn-xs"
                               href="?action=remove&id=<c:out value='${meal.id}'/>"
                               role="button">Удалить</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <h3>Список еды пуст</h3>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
