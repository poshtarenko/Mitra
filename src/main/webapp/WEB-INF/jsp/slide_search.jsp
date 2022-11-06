<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="shortcut icon" href="../../resources/img/icon.png">
    <title>Title</title>
</head>
<body>

<h3>${requestScope.profile.getName()}</h3>
<p><b>Вік :</b> ${requestScope.profile.getAge()} років</p>
<p><b>Місто :</b> ${requestScope.profile.getLocation()}</p>
<p><b>Стать :</b> ${requestScope.profile.getGender().name()}</p>
<p><b>Текст анкети :</b> ${requestScope.profile.getText()}</p>

<a href="${pageContext.request.contextPath}/app/go?i=${requestScope.nextProfileId}">Наступна анкета!</a>

</body>
</html>
