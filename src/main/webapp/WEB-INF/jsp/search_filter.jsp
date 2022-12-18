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
                <form action="${pageContext.request.contextPath}/app/search" method="get">
                    <label for="name">Ім'я</label>
                    <input type="text" name="name" id="name" value="${requestScope.selectedName}">

                    <label for="gender">Стать</label>
                    <select name="gender" id="gender">
                        <option data-tokens="${requestScope.selectedGender.getId()}" value="${requestScope.selectedGender.getId()}" selected>${requestScope.selectedGender.name()}</option>
                        <c:forEach var="gender" items="${requestScope.genders}">
                            <option data-tokens="${gender.getId()}" value="${gender.getId()}">${gender.name()}</option>
                        </c:forEach>
                    </select><br>

                    <label for="minAge">Мін. вік</label>
                    <input type="number" name="minAge" id="minAge" value="${requestScope.selectedMinAge}">

                    <label for="maxAge">Макс. вік</label>
                    <input type="number" name="maxAge" id="maxAge" value="${requestScope.selectedMaxAge}"><br>


                    <label for="city">Місто</label>
                    <select name="city" id="city">
                        <option value="${requestScope.selectedCity}" selected>${requestScope.selectedCity}</option>
                        <c:forEach var="city" items="${requestScope.cities}">
                            <option value="${city}">${city}</option>
                        </c:forEach>
                    </select><br>

                    <label for="localArea">Місцевість</label>
                    <select name="localArea" id="localArea">
                        <option value="${requestScope.selectedLocalArea}" selected>${requestScope.selectedLocalArea}</option>
                        <c:forEach var="localArea" items="${requestScope.localAreas}">
                            <option value="${localArea}">${localArea}</option>
                        </c:forEach>
                    </select>

                    <label for="region">Область</label>
                    <select name="region" id="region">
                        <option value="${requestScope.selectedRegion}" selected>${requestScope.selectedRegion}</option>
                        <c:forEach var="region" items="${requestScope.regions}">
                            <option value="${region}">${region}</option>
                        </c:forEach>
                    </select><br>

                    <label for="instruments">Інструменти</label>
                    <select name="instruments" id="instruments" multiple>
                        <c:forEach var="instrument" items="${requestScope.selectedInstruments}">
                            <option data-tokens="${instrument.getId()}" value="${instrument.getId()}" selected>${instrument.getName()}</option>
                        </c:forEach>
                        <c:forEach var="instrument" items="${requestScope.instruments}">
                            <option data-tokens="${instrument.getId()}" value="${instrument.getId()}">${instrument.getName()}</option>
                        </c:forEach>
                    </select>

                    <label for="specialities">Спеціальності</label>
                    <select name="specialities" id="specialities" multiple>
                        <c:forEach var="speciality" items="${requestScope.selectedSpecialities}">
                            <option data-tokens="${speciality.getId()}" value="${speciality.getId()}" selected>${speciality.getName()}</option>
                        </c:forEach>
                        <c:forEach var="speciality" items="${requestScope.specialities}">
                            <option data-tokens="${speciality.getId()}" value="${speciality.getId()}">${speciality.getName()}</option>
                        </c:forEach>
                    </select><br>

                    <input type="submit" value="Пошук" style="width: 120px; text-align: center; font-weight: bold">

                    <hr>

                    <c:if test="${empty requestScope.profiles}">
                        <h4>Нічого не знайдено :(</h4>
                    </c:if>

                    <div style="display: inline-flex">
                        <c:forEach var="page" items="${requestScope.pages}">
                            <input style="width: 32px; text-align: center" type="submit" name="page" value="${page}">
                        </c:forEach>
                    </div>
                    <br><br>

                    <c:forEach var="profile" items="${requestScope.profiles}">
                        <c:if test="${not empty profile.getPhotoPath()}">
                            <img width="250" height="250" src="${pageContext.request.contextPath}/app/images?path=${profile.getPhotoPath()}"/>
                        </c:if>
                        <c:if test="${empty profile.getPhotoPath()}">
                            <img width="250" height="250" src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                        </c:if>

                        <h3><a href="${pageContext.request.contextPath}/app/profile?id=${profile.getId()}">${profile.getName()}</a></h3>
                        <p><b>${profile.getLocation().getCity()}, ${profile.getAge()} років, ${profile.getGender().name()}</b></p>
                        <c:if test="${not empty profile.getInstruments()}">
                            <p><b>Інструменти :</b>
                                <c:forEach var="instrument" items="${profile.getInstruments()}">
                                    <span>${instrument.getName()} </span>
                                </c:forEach>
                            </p>
                        </c:if>
                        <c:if test="${not empty profile.getSpecialities()}">
                            <p><b>Спеціальність :</b>
                                <c:forEach var="speciality" items="${profile.getSpecialities()}">
                                    <span>${speciality.getName()} </span>
                                </c:forEach>
                            </p>
                        </c:if>
                        <p>${profile.getText()}</p>
                        <hr>
                    </c:forEach>

                    <div style="display: inline-flex">
                        <c:forEach var="page" items="${requestScope.pages}">
                            <input style="width: 27px; text-align: center" type="submit" name="page" value="${page}">
                        </c:forEach>
                    </div>
                </form>
                <br>
                <br>

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

