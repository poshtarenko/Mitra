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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/changeInfoStyle.css">
    <link rel="shortcut icon" href="../../resources/img/icon.png">
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
                <c:if test="${not empty requestScope.chat}">
                    <div style="display: inline-flex">
                        <c:if test="${not empty requestScope.chat.getSecondProfile().getPhotoPath()}">
                            <img width="50" height="50"
                                 src="${pageContext.request.contextPath}/app/images?path=${requestScope.chat.getSecondProfile().getPhotoPath()}"/>
                        </c:if>
                        <c:if test="${empty requestScope.chat.getSecondProfile().getPhotoPath()}">
                            <img width="50" height="50"
                                 src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/app/chat?c=${requestScope.chat.getId()}">
                                ${requestScope.chat.getSecondProfile().getName()}
                        </a>
                    </div>
                    <hr>
                    <c:if test="${not empty requestScope.messages}">
                        <c:forEach var="msg" items="${requestScope.messages}">
                            <c:if test="${msg.getSender().getId() == sessionScope.USER_ID}">
                                <div style="margin-left: 300px">
                                    <p>${msg.getMessage()}</p>
                                    <p>${msg.getTime().getHour()}:${msg.getTime().getMinute()}</p>
                                </div>
                            </c:if>
                            <c:if test="${msg.getSender().getId() != sessionScope.USER_ID}">
                                <div>
                                    <p>${msg.getMessage()}</p>
                                    <p>${msg.getTime().getHour()}:${msg.getTime().getMinute()}</p>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty requestScope.messages}">
                        <p>Повідомлень поки немає</p>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/app/chat" method="post">
                        <input type="hidden" name="chatId" value="${requestScope.chat.getId()}">
                        <input style="width: 330px" type="text" name="msg" placeholder="Повідомлення...">
                        <input type="submit" value="Надіслати">
                    </form>
                </c:if><br>
                <c:if test="${empty requestScope.chat}">
                    <p>Такого чату не існує</p><br>
                </c:if><br>
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