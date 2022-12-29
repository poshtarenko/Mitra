<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>

<head>
    <title>Mitra</title>
    <link rel="shortcut icon" href="../../resources/img/icon.png">

    <link rel="stylesheet" href="../../resources/css/main.css">
    <link rel="stylesheet" href="../../resources/css/profile.css">
    <link rel="stylesheet" href="../../resources/css/go.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
            rel="stylesheet">
</head>

<body>
<div id="background">
    <div id="app">
        <div id="sidebar">
            <a href="${pageContext.request.contextPath}/app/account">
                <div class="sidebar-item" id="user-item">
                    <c:if test="${not empty sessionScope.USER.getProfile().getPhotoPath()}">
                        <img id="user-img" src="${pageContext.request.contextPath}/app/images?path=${sessionScope.USER.getProfile().getPhotoPath()}"/>
                    </c:if>
                    <c:if test="${empty sessionScope.USER.getProfile().getPhotoPath()}">
                        <img id="user-img" src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                    </c:if>
                    <span class="sidebar-text">${sessionScope.USER.getProfile().getName()}</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/me">
                <div class="sidebar-item" style="margin-top: 20px">
                    <img src="../../resources/img/icon/profile.png" class="sidebar-img">
                    <span class="sidebar-text">Профіль</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/music">
                <div class="sidebar-item">
                    <img src="../../resources/img/icon/music.png" class="sidebar-img">
                    <span class="sidebar-text">Музика</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/go">
                <div class="sidebar-item selected-sidebar-item" style="margin-top: 50px">
                    <img src="../../resources/img/icon/go.png" class="sidebar-img">
                    <span class="sidebar-text">Погнали!</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/search">
                <div class="sidebar-item">
                    <img src="../../resources/img/icon/search.png" class="sidebar-img">
                    <span class="sidebar-text">Пошук</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/likes">
                <div class="sidebar-item">
                    <img src="../../resources/img/icon/likes.png" class="sidebar-img">
                    <span class="sidebar-text">Лайки</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/chats">
                <div class="sidebar-item">
                    <img src="../../resources/img/icon/chats.png" class="sidebar-img">
                    <span class="sidebar-text">Чати</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/me">
                <div class="sidebar-item" style="margin-top: 50%">
                    <span class="sidebar-text">Підтримка</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/me">
                <div class="sidebar-item">
                    <span class="sidebar-text">Контакти</span>
                </div>
            </a>
        </div>
        <div id="content-wrapper">
            <div id="content">
                <div id="go-content">
                    <c:if test="${not empty requestScope.profile.getPhotoPath()}">
                        <img id="profile-photo"
                             src="${pageContext.request.contextPath}/app/images?path=${requestScope.profile.getPhotoPath()}"/>
                    </c:if>
                    <c:if test="${empty requestScope.profile.getPhotoPath()}">
                        <img id="profile-photo"
                             src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                    </c:if>
                    <a class="profile-text" id="profile-name"
                       href="${pageContext.request.contextPath}/app/profile?id=${requestScope.profile.getId()}">
                        ${requestScope.profile.getName()}
                    </a>
                    <c:if test="${not empty requestScope.profile.getPreviewTrack()}">
                        <audio controls>
                            <source src="${pageContext.request.contextPath}/app/audio?path=${requestScope.profile.getPreviewTrack().getFilePath()}"
                                    type="audio/mpeg">
                        </audio>
                        <hr>
                    </c:if>
                    <div class="profile-text white-background">${requestScope.profile.getGender().name()}. ${requestScope.profile.getAge()}
                        років. Місто ${requestScope.profile.getLocation().getCity()}</div>
                    <c:if test="${not empty requestScope.profile.getInstruments()}">
                        <p><b>Інструменти :</b>
                            <c:forEach var="instrument" items="${requestScope.profile.getInstruments()}">
                                <span>${instrument.getName()} </span>
                            </c:forEach>
                        </p>
                    </c:if>
                    <c:if test="${not empty requestScope.profile.getInstruments()}">
                        <div class="profile-text">
                            <div class="white-background">Інструменти :</div>
                            <div>
                                <c:forEach var="instrument" items="${requestScope.profile.getInstruments()}">
                                    ${instrument.getName()}
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.profile.getSpecialities()}">
                        <div class="profile-text">
                            <div class="white-background">Спеціальність :</div>
                            <div>
                                <c:forEach var="speciality" items="${requestScope.profile.getSpecialities()}">
                                    ${speciality.getName()}
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    <div class="profile-text white-background" id="profile-text-attr">
                        ${requestScope.profile.getText()}
                    </div>
                    <div id="go-buttons">
                        <form action="${pageContext.request.contextPath}/app/likes" method="post" id="like-button-form">
                            <input type="hidden" name="type" value="LIKE">
                            <input type="hidden" name="id" value="${requestScope.profile.getId()}">
                            <button type="submit" class="like-button">Давай знайомитись!</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/app/go" class="like-button" style="color: #ffffff">Наступна анкета</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
