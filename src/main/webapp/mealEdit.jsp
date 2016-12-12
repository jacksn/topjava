<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="meal" type="ru.javawebinar.topjava.model.Meal"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Редактирование еды</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body class="container">
<h1 class="page-header">Редактирование записи</h1>
<div>
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
                        <a class="btn btn-danger btn-sm" href="meals" role="button">Отмена</a>
                    </div>
                    <div class="col-xs-2">
                        <button type="submit" id="saveMeal" class="btn btn-success btn-sm">
                            Сохранить
                        </button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
