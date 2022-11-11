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

<a href="${pageContext.request.contextPath}/app/go">Пошук свайпом</a>
<a href="${pageContext.request.contextPath}/app/upd_profile"> Оновити анкету</a>

<h2>Всі анкети користувачів :</h2>

<c:forEach var="profile" items="${requestScope.profiles}">
    <hr>
    <c:if test="${not empty profile.getPhotoPath()}">
        <img width="250" height="250" src="${pageContext.request.contextPath}/app/images?path=${profile.getPhotoPath()}"/>
    </c:if>
    <c:if test="${empty profile.getPhotoPath()}">
        <img width="250" height="250" src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
    </c:if>

    <h3>${profile.getName()}</h3>
    <p><b>Вік :</b> ${profile.getAge()} років</p>
    <p><b>Місто :</b> ${profile.getLocation()}</p>
    <p><b>Стать :</b> ${profile.getGender().name()}</p>
    <p><b>Текст анкети :</b> ${profile.getText()}</p>
    <p><b>Інструменти :</b>
        <c:forEach var="instrument" items="${profile.getInstruments()}">
            <span>${instrument.getName()} </span>
        </c:forEach>
    </p>
    <p><b>Спеціальність :</b>
        <c:forEach var="speciality" items="${profile.getSpecialities()}">
            <span>${speciality.getName()} </span>
        </c:forEach>
    </p>

</c:forEach>
</body>
</html>
