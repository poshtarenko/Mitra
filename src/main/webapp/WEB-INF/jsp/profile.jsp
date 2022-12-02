<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../resources/img/icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/changeInfoStyle.css">

    <!-- FONT AWESOME  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- BOOTSTRAP-SELECT -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.css" rel="stylesheet"/>

    <title>Mitra</title>
</head>
<body>

<!-- <div class="transition-2 isActive"></div> -->
<div class="asideAndContent">

    <div class="asideAndContentInner">

        <%@ include file="sidebar.jsp" %>

        <main class="main">
            <div class="main-content">
                <br>
                <c:if test="${not empty requestScope.profile.getPhotoPath()}">
                    <img width="250" height="250" src="${pageContext.request.contextPath}/app/images?path=${requestScope.profile.getPhotoPath()}"/>
                </c:if>
                <c:if test="${empty requestScope.profile.getPhotoPath()}">
                    <img width="250" height="250" src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                </c:if>
                <h3>${requestScope.profile.getName()}</h3>
                <p><b>${requestScope.profile.getLocation().getCity()}, ${requestScope.profile.getAge()} років, ${requestScope.profile.getGender().name()}</b></p>
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
                <p>${requestScope.profile.getText()}</p>

                <c:if test="${not empty requestScope.enableToLike}">
                    <form class="swipe_search_button" action="${pageContext.request.contextPath}/app/likes" method="post">
                        <input type="hidden" name="type" value="LIKE">
                        <input type="hidden" name="id" value="${requestScope.profile.getId()}">
                        <input type="submit" value="Давай знайомитись!">
                    </form>
                </c:if>
                <c:if test="${not empty requestScope.waitingForResponse}">
                    <p>Чекаємо на відповідь</p>
                </c:if>
                <c:if test="${not empty requestScope.alreadyMutualLikes}">
                    <p>Ми дружимо!</p>
                    <c:if test="${not empty requestScope.chatId}">
                        <form action="${pageContext.request.contextPath}/app/chat" method="get">
                            <input type="hidden" name="c" value="${requestScope.chatId}">
                            <input type="submit" value="Перейти до чату">
                        </form>
                        <br>
                    </c:if>
                    <c:if test="${empty requestScope.chatId}">
                        <form class="swipe_search_button" action="${pageContext.request.contextPath}/app/chats" method="post">
                            <input type="hidden" name="profileId" value="${requestScope.profile.getId()}">
                            <input type="submit" value="Почати чат">
                        </form>
                        <br>
                    </c:if>
                </c:if>
                <c:if test="${not empty requestScope.userRejectedUs}">
                    <p>Користувач відмовив нам</p>
                </c:if>
                <c:if test="${not empty requestScope.enableToResponse}">
                    <form class="swipe_search_button" action="${pageContext.request.contextPath}/app/likes" method="post">
                        <input type="hidden" name="type" value="RESPONSE">
                        <input type="hidden" name="reaction" value="LIKE">
                        <input type="hidden" name="id" value="${requestScope.profile.getId()}">
                        <input type="submit" value="Давай знайомитись!">
                    </form>
                    <form class="swipe_search_button" action="${pageContext.request.contextPath}/app/likes" method="post">
                        <input type="hidden" name="type" value="RESPONSE">
                        <input type="hidden" name="reaction" value="DISLIKE">
                        <input type="hidden" name="id" value="${requestScope.profile.getId()}">
                        <input type="submit" value="Відмовити">
                    </form>
                    <form class="swipe_search_button" action="${pageContext.request.contextPath}/app/likes" method="post">
                        <input type="hidden" name="type" value="RESPONSE">
                        <input type="hidden" name="reaction" value="IGNORE">
                        <input type="hidden" name="id" value="${requestScope.profile.getId()}">
                        <input type="submit" value="Ігнорувати">
                    </form>
                </c:if>
                <c:if test="${not empty requestScope.weRejectedUser}">
                    <p>Ми відмовили користувачеві</p>
                </c:if>
            </div>
        </main>
    </div>
    <!-- </div> -->
</div>


<!-- BOOTSTRAP SELECT -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/changeInfoScript.js"></script>

<!-- BOOTSTRAP SELECT -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/styleSelect.js"></script>
</body>
</html>
