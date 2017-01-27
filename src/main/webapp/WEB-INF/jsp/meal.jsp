<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2>
        <c:choose>
            <c:when test="${meal.id == null}">
                <fmt:message key="meals.create"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="meals.edit"/>
            </c:otherwise>
        </c:choose>
    </h2>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals/save">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><fmt:message key="meals.date"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="meals.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt><fmt:message key="meals.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit"><fmt:message key="meals.save"/></button>
        <button onclick="window.history.back()"><fmt:message key="meals.cancel"/></button>
    </form>
</section>
</body>
</html>
