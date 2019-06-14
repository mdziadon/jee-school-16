<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="fragments/header.jspf"%>

<form method="post">

    <p><strong>Zadanie:</strong> ${solution.exercise.title}</p>

    <p><strong>Użytkownik:</strong> ${solution.user.username}</p>

    <textarea rows="5" cols="80" name="description">${solution.description}</textarea><br><br>

    <input type="submit" value="Zapisz"/>
</form>

<%@ include file="fragments/footer.jspf"%>
</body>
</html>
