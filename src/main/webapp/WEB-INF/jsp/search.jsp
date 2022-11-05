<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="shortcut icon" href="../../resources/img/icon.png">
    <title>Title</title>
</head>
<body style="padding-left: 200px; margin-top: 30px; padding-right: 1100px">

<h2>Всі анкети користувачів :</h2>

<c:forEach var="profile" items="${requestScope.profiles}">
    <hr>
    <h3>${profile.getName()}</h3>
    <p><b>Вік :</b> ${profile.getAge()} років</p>
    <p><b>Місто :</b> ${profile.getLocation()}</p>
    <p><b>Стать :</b> ${profile.getGender().name()}</p>
    <p><b>Текст анкети :</b> ${profile.getText()}</p>
</c:forEach>
</body>
</html>
