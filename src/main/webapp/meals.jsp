<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meal</h2>

<table class="tg">

    <tr>
        <th width="160">Date</th>
        <th width="160">Description</th>
        <th width="160">Calories</th>
        <th width="100"></th>
        <th width="100"></th>
    </tr>

    <c:if test="${!empty listToMeal}">


    <c:forEach items="${listToMeal}" var="mealTo">

        <tr style="color:${mealTo.getExcess() ? 'red' : 'green'}">
            <c:set var="mealDate" scope="session" value="${mealTo.getDateTime()}"/>
            <javatime:parseLocalDateTime value="${mealDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
            <javatime:format value="${parsedDate}" pattern="dd.MM.yyyy HH:mm" var="normParseDate"/>
            <td align="center">${normParseDate}</td>


            <td align="center">${mealTo.getDescription()}</td>
            <td align="center">${mealTo.getCalories()}</td>
            </td>
            <td align="center">
                Edit
            </td>
            <td align="center">
                Delete
            </td>
        </tr>

    </c:forEach>
</table>
</c:if>


</body>
</html>