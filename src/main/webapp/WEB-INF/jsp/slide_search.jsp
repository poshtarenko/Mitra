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

<c:if test="${not empty profile.getPhotoPath()}">
    <img width="250" height="250" src="${pageContext.request.contextPath}/app/images?path=${profile.getPhotoPath()}"/>
</c:if>
<c:if test="${empty profile.getPhotoPath()}">
    <img width="250" height="250" src="${pageContext.request.contextPath}/resources/img/profile/no_photo.png"/>
</c:if>
<h3>${requestScope.profile.getName()}</h3>
<p><b>Вік :</b> ${requestScope.profile.getAge()} років</p>
<p><b>Місто :</b> ${requestScope.profile.getLocation()}</p>
<p><b>Стать :</b> ${requestScope.profile.getGender().name()}</p>
<p><b>Текст анкети :</b> ${requestScope.profile.getText()}</p>
<p><b>Інструменти :</b>
    <c:forEach var="instrument" items="${requestScope.profile.getInstruments()}">
        <span>${instrument.getName()}   </span>
    </c:forEach>
</p>
<p><b>Спеціальність :</b>
    <c:forEach var="speciality" items="${requestScope.profile.getSpecialities()}">
        <span>${speciality.getName()}   </span>
    </c:forEach>
</p>

<a href="${pageContext.request.contextPath}/app/go?i=${requestScope.nextProfileId}">Наступна анкета!</a>

</body>
</html>
