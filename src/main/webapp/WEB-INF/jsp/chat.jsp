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
    <link rel="stylesheet" href="../../resources/css/chat.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>


    <link
            href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
            rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.css" rel="stylesheet"/>
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
                    <span class="sidebar-text">??????????????</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/music">
                <div class="sidebar-item">
                    <img src="../../resources/img/icon/music.png" class="sidebar-img">
                    <span class="sidebar-text">????????????</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/go">
                <div class="sidebar-item" style="margin-top: 50px">
                    <img src="../../resources/img/icon/go.png" class="sidebar-img">
                    <span class="sidebar-text">??????????????!</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/search">
                <div class="sidebar-item">
                    <img src="../../resources/img/icon/search.png" class="sidebar-img">
                    <span class="sidebar-text">??????????</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/likes">
                <div class="sidebar-item">
                    <img src="../../resources/img/icon/likes.png" class="sidebar-img">
                    <span class="sidebar-text">??????????</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/chats">
                <div class="sidebar-item selected-sidebar-item">
                    <img src="../../resources/img/icon/chats.png" class="sidebar-img">
                    <span class="sidebar-text">????????</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/me">
                <div class="sidebar-item" style="margin-top: 50%">
                    <span class="sidebar-text">??????????????????</span>
                </div>
            </a>
            <a href="${pageContext.request.contextPath}/app/me">
                <div class="sidebar-item">
                    <span class="sidebar-text">????????????????</span>
                </div>
            </a>
        </div>
        <div id="content-wrapper">
            <div id="content">
                <div id="chat-content">
                    <div id="chat-info">
                        <c:if test="${not empty requestScope.chat.getFriendProfile().getPhotoPath()}">
                            <img src="${pageContext.request.contextPath}/app/images?path=${requestScope.chat.getFriendProfile().getPhotoPath()}"/>
                        </c:if>
                        <c:if test="${empty requestScope.chat.getFriendProfile().getPhotoPath()}">
                            <img src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/app/chat?c=${requestScope.chat.getId()}">
                            ${requestScope.chat.getFriendProfile().getName()}
                        </a>
                    </div>

                    <div id="messages">
                        <c:if test="${not empty requestScope.chat.getMessages()}">
                            <c:forEach var="msg" items="${requestScope.chat.getMessages()}">
                                <c:if test="${msg.getSender().getId() == sessionScope.USER.getId()}">
                                    <div class="my-message">
                                        <div class="message">
                                            <p class="message-text">${msg.getMessage()}</p>
                                            <p class="message-time">${msg.getTime().getHour()}:${msg.getTime().getMinute()}</p>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${msg.getSender().getId() != sessionScope.USER.getId()}">
                                    <div class="friend-message">
                                        <div class="message">
                                            <p class="message-text">${msg.getMessage()}</p>
                                            <p class="message-time">${msg.getTime().getHour()}:${msg.getTime().getMinute()}</p>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty requestScope.chat.getMessages()}">
                            <p>?????????????????????? ???????? ??????????</p>
                        </c:if>
                    </div>
                    <form id="message-input" action="${pageContext.request.contextPath}/app/send_message" method="post">
                        <input type="hidden" name="chatId" value="${requestScope.chat.getId()}">
                        <input type="text" name="msg">
                        <button type="submit" class="like-button">OK</button>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>
</body>

</html>