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
    <link rel="stylesheet" href="../../resources/css/update_profile.css">
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
                <div class="sidebar-item selected-sidebar-item" style="margin-top: 20px">
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
                <div class="sidebar-item" style="margin-top: 50px">
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
                <div id="profile-content">
                    <div id="profile-main">
                        <div id="photo-and-button">
                            <c:if test="${not empty requestScope.profile.getPhotoPath()}">
                                <img id="profile-photo"
                                     src="${pageContext.request.contextPath}/app/images?path=${requestScope.profile.getPhotoPath()}"/>
                            </c:if>
                            <c:if test="${empty requestScope.profile.getPhotoPath()}">
                                <img id="profile-photo"
                                     src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                            </c:if><br>
                            <form action="${pageContext.request.contextPath}/app/upd_photo" method="post"
                                  enctype="multipart/form-data">
                                <label class="like-button" for="photo">Завантажити нове фото</label>
                                <input style="display: none;" onchange="showPreview(event)" name="photo" type="file"
                                       id="photo">
                                <input type="submit" id="update-photo-button" style="display: none" name="">
                            </form>
                        </div>
                        <form id="profile-info" action="${pageContext.request.contextPath}/app/upd_profile"
                              method="post">
                            <div class="form-item white-background">
                                <label for="name">Ім'я</label>
                                <input spellcheck="false" class="form-input" type="text" id="name"
                                       name="name" value="${requestScope.profile.getName()}">
                            </div>
                            <div class="form-item white-background">
                                <label for="age">Вік</label>
                                <input class="form-input" type="number" id="age"
                                       name="age" value="${requestScope.profile.getAge()}">
                            </div>
                            <div class="form-item white-background">
                                <label for="gender">Стать</label>
                                <select class="form-select-large" name="gender" id="gender">
                                    <option value="${requestScope.profile.getGender().getId()}"
                                            selected>${requestScope.profile.getGender().name()}</option>
                                    <c:forEach var="gender" items="${requestScope.otherGenders}">
                                        <option data-tokens="${gender.getId()}"
                                                value="${gender.getId()}">${gender.name()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-item white-background">
                                <label for="city">Місто</label>
                                <select class="form-select-large" name="location" id="city">
                                    <option data-tokens="${requestScope.profile.getLocation().getId()}"
                                            value="${requestScope.profile.getLocation().getId()}"
                                            selected>${requestScope.profile.getLocation().getCity()}</option>
                                    <c:forEach var="location" items="${requestScope.otherLocations}">
                                        <option data-tokens="${location.getId()}" value="${location.getId()}">${location.getCity()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-item white-background">
                                <label for="instruments">Інструменти</label>
                                <select class="form-select-small" name="instruments" id="instruments" multiple>
                                    <c:forEach var="instrument" items="${requestScope.profile.getInstruments()}">
                                        <option data-tokens="${instrument.getId()}" value="${instrument.getId()}"
                                                selected>${instrument.getName()}</option>
                                    </c:forEach>
                                    <c:forEach var="instrument" items="${requestScope.otherInstruments}">
                                        <option data-tokens="${instrument.getId()}"
                                                value="${instrument.getId()}">${instrument.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-item white-background">
                                <label for="specialities">Спеціальність</label>
                                <select class="form-select-small" name="specialities" id="specialities" multiple>
                                    <c:forEach var="speciality" items="${requestScope.profile.getSpecialities()}">
                                        <option data-tokens="${speciality.getId()}" value="${speciality.getId()}"
                                                selected>${speciality.getName()}</option>
                                    </c:forEach>
                                    <c:forEach var="speciality" items="${requestScope.otherSpecialities}">
                                        <option data-tokens="${speciality.getId()}"
                                                value="${speciality.getId()}">${speciality.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-item white-background">
                                <label for="text">Текст</label>
                                <textarea spellcheck="false" class="form-input" id="text" name="text">${requestScope.profile.getText()}</textarea>
                            </div>

                            <button type="submit" class="like-button">Оновити анкету</button>
                            <a href="/" class="like-button" style="color: #FFFFFF">Скасувати зміни</a>
                        </form>
                    </div>
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
<script>
    $(document).ready(function () {
        $('select').selectpicker();
    });

    function showPreview(event) {
        if (event.target.files.length > 0) {
            let src = URL.createObjectURL(event.target.files[0]);
            let preview = document.getElementById("profile-photo");
            let update_photo_btn = document.getElementById("update-photo-button");
            update_photo_btn.click();
            preview.src = src;
        }
    }
</script>
</body>

</html>