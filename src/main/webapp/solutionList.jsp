<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%@include file="fragments/header.jspf"%>

<a href="/addSolution">Dodaj rozwiązanie</a>

<table border="1" style="margin-top: 20px">
    <tr>
        <th>Tytuł zadania</th>
        <th>Autor rozwiązania</th>
        <th>Data dodania</th>
        <th>Akcje</th>
    </tr>
    <c:forEach items="${solutions}" var="solution">
        <tr>
            <td>${solution.exercise.title}</td>
            <td>${solution.user.username}</td>
            <td>${solution.created}</td>
            <td>
                <a href="/updateSolution?id=${solution.id}">Edytuj</a>
                <a href="/deleteSolution?id=${solution.id}">Usuń</a>
            </td>
        </tr>
    </c:forEach>
</table>

<%@include file="fragments/footer.jspf"%>

</body>
</html>
