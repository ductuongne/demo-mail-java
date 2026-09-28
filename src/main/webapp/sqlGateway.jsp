<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
    <h1>The SQL Gateway</h1>
    <p>Nhập một câu lệnh SQL và nhấn Execute để chạy thử.</p>

    <c:if test="${sqlStatement == null}">
        <c:set var="sqlStatement" value="select * from User" />
    </c:if>

    <form action="sqlGateway" method="post">
        <p><b>SQL statement:</b></p>
        <textarea name="sqlStatement" cols="60" rows="8">${sqlStatement}</textarea><br>
        <input type="submit" value="Execute">
    </form>

    <c:if test="${sqlResult != null}">
        <p><b>SQL result:</b></p>
        ${sqlResult}
    </c:if>
</body>
</html>
