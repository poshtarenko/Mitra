<%--
  Created by IntelliJ IDEA.
  User: catem
  Date: 01.11.2022
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="margin-left: 20px; margin-top: 30px;">

<br><h4>Заповніть вашу анкету :</h4>
<form action="${pageContext.request.contextPath}/app/create_profile" method="post">
    <label for="name">Ім'я :
        <input type="text" name="name" id="name" required>
    </label><br><br>


    <label for="age">Вік :
        <input type="number" name="age" id="age" required>
    </label><br><br>

    <label for="gender"> Стать :
        <select name="gender" id="gender" required>
            <c:forEach var="gender" items="${requestScope.genders}">
                <option value="${gender}">${gender}</option>
            </c:forEach>
        </select>
    </label><br><br>

    <label for="text">Текст анкети :
        <input type="text" name="text" id="text" required>
    </label><br><br>

    <label for="city"> Місто :
        <select name="city" id="city" required>
            <c:forEach var="city" items="${requestScope.cities}">
                <option value="${city}">${city}</option>
            </c:forEach>
        </select>
    </label><br><br>

    <br>
    <input type="submit" value="Створити анкету">
</form>
</body>
</html>
