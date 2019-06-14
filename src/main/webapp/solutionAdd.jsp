<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="fragments/header.jspf"%>

<form method="post">

    <label>Zadanie:
        <select name="exerciseId">
            <c:forEach items="${exercises}" var="exercise">
                <option value="${exercise.id}">${exercise.title}</option>
            </c:forEach>
        </select>
    </label><br><br>

    <label>Użytkownik:
        <select name="userId">
            <c:forEach items="${users}" var="user">
                <option value="${user.id}">${user.username}</option>
            </c:forEach>
        </select>
    </label><br><br>


    <textarea rows="5" cols="80" name="description"></textarea><br><br>

    <input type="submit" value="Zapisz"/>
</form>

<%@ include file="fragments/footer.jspf"%>
</body>
</html>