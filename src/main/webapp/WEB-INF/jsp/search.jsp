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
    <link rel="stylesheet" href="../../resources/css/search.css">
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
                    <c:if test="${empty requestScope.profile.getPhotoPath()}">
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
                <div class="sidebar-item selected-sidebar-item">
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
            <form id="content" action="${pageContext.request.contextPath}/app/search" method="get">
                <div id="filters-form">
                    <div class="filters-form-item">
                        <label for="name">Ім'я</label>
                        <input type="text" name="name" id="name" value="${requestScope.selectedName}">
                    </div>
                    <div class="filters-form-item">
                        <label for="gender">Стать</label>
                        <select class="form-select-large form-control" name="gender" id="gender">
                            <option data-tokens="${requestScope.selectedGender.getId()}" value="${requestScope.selectedGender.getId()}"
                                    selected>${requestScope.selectedGender.name()}</option>
                            <c:forEach var="gender" items="${requestScope.genders}">
                                <option data-tokens="${gender.getId()}" value="${gender.getId()}">${gender.name()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filters-form-item">
                        <label for="minAge">Мін.вік</label>
                        <input class="filters-number-input" type="number" name="minAge" id="minAge" value="${requestScope.selectedMinAge}">
                    </div>
                    <div class="filters-form-item">
                        <label for="maxAge">Макс.вік</label>
                        <input class="filters-number-input" type="number" name="maxAge" id="maxAge" value="${requestScope.selectedMaxAge}">
                    </div>
                    <br>
                    <div class="filters-form-item container w-25">
                        <label for="city">Місто</label>
                        <select class="filters-loc-select form-control w-100 h-100" name="city" id="city">
                            <option value="${requestScope.selectedCity}" selected>${requestScope.selectedCity}</option>
                            <c:forEach var="city" items="${requestScope.cities}">
                                <option value="${city}">${city}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filters-form-item container w-25">
                        <label for="localArea">Район</label>
                        <select class="filters-loc-select form-control w-100 h-100" name="localArea" id="localArea">
                            <option value="${requestScope.selectedLocalArea}" selected>${requestScope.selectedLocalArea}</option>
                            <c:forEach var="localArea" items="${requestScope.localAreas}">
                                <option value="${localArea}">${localArea}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filters-form-item container w-25">
                        <label for="region">Область</label>
                        <select class="filters-loc-select form-control w-100 h-100" name="region" id="region">
                            <option value="${requestScope.selectedRegion}" selected>${requestScope.selectedRegion}</option>
                            <c:forEach var="region" items="${requestScope.regions}">
                                <option value="${region}">${region}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br>
                    <div class="filters-form-item">
                        <label for="instruments">Інструменти</label>
                        <select class="form-select-small form-control" name="instruments" id="instruments" multiple>
                            <c:forEach var="instrument" items="${requestScope.selectedInstruments}">
                                <option data-tokens="${instrument.getId()}" value="${instrument.getId()}"
                                        selected>${instrument.getName()}</option>
                            </c:forEach>
                            <c:forEach var="instrument" items="${requestScope.instruments}">
                                <option data-tokens="${instrument.getId()}" value="${instrument.getId()}">${instrument.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filters-form-item">
                        <label for="specialities">Спеціальності</label>
                        <select class="form-select-small form-control" name="specialities" id="specialities" multiple>
                            <c:forEach var="speciality" items="${requestScope.selectedSpecialities}">
                                <option data-tokens="${speciality.getId()}" value="${speciality.getId()}"
                                        selected>${speciality.getName()}</option>
                            </c:forEach>
                            <c:forEach var="speciality" items="${requestScope.specialities}">
                                <option data-tokens="${speciality.getId()}" value="${speciality.getId()}">${speciality.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filters-form-item">
                        <button class="like-button">Пошук</button>
                    </div>
                </div>
                <div class="center-word">Анкети</div>
                <div id="search-content">

                    <c:if test="${empty requestScope.profiles}">
                        <p>Нічого не знайдено :(</p>
                    </c:if>

                    <c:forEach var="profile" items="${requestScope.profiles}">
                    <div id="profile-main">
                        <c:if test="${not empty profile.getPhotoPath()}">
                            <img id="profile-photo" src="${pageContext.request.contextPath}/app/images?path=${profile.getPhotoPath()}"/>
                        </c:if>
                        <c:if test="${empty profile.getPhotoPath()}">
                            <img id="profile-photo" src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                        </c:if>
                        <div id="profile-info">
                            <div class="profile-text" id="profile-name">${profile.getName()}</div>
                            <div class="profile-text white-background">${profile.getGender().name()}. ${profile.getAge()} років. Місто ${profile.getLocation().getCity()}</div>
                            <div class="profile-text white-background profile-text-attr">
                                    ${profile.getText()}
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>

                <div style="display: inline-flex">
                    <c:forEach var="page" items="${requestScope.pages}">
                        <input style="width: 27px; text-align: center" type="submit" name="page" value="${page}">
                    </c:forEach>
                </div>
            </form>
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
</script>
</body>

</html>