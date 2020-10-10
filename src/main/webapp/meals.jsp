<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.jsp">Home</a></h3>
<hr>
    <table border="5" align="center">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan=2>Action</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="background-color:${meal.isExcess() ? 'red' : 'green'}">
            <td><b><c:out value="${meal.getId()}"/></b></td>
            <td><b><fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm"
                                  var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/></b></td>
            <td><b><c:out value="${meal.getDescription()}"/></b></td>
            <td><b><c:out value="${meal.getCalories()}"/></b></td>
            <td><b><a style="color: black" href="meals?action=edit&mealID=<c:out value="${meal.getId()}"/>">Edit</a></b>
        </td>
        <td><b><a style="color: black" href="meals?action=delete&mealID=<c:out value="${meal.getId()}"/>">Delete</a></b>
    </td>
    </tr>
</c:forEach>
</tbody>
</table>
<p><a href="meals?action=insert">Add User</a></p>
        </body>
        </html>