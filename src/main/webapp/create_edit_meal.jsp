<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Add meal</title>
</head>
<body>
<form method="POST" action="meals" name="frmAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="mealid"
                     value="<c:out value="${meal.getId()}" />"/> <br/>

    Date : <input
        type="datetime-local" name="date"
        value="<c:out value="${date}" />"/> <br/>

    Description : <input
        type="text" name="description"
        value="<c:out value="${description}" />"/> <br/>

    ¬³alories : <input
        type="number" name="calories"
        value="<c:out value="${calories}" />"/> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>