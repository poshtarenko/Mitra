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
        <!-- <div class="container"> -->


            <div class="asideAndContentInner">

                <%@ include file="sidebar.jsp" %>

                <main class="main">

                    <form class="main-content" action="${pageContext.request.contextPath}/app/upd_profile" method="post" enctype='multipart/form-data'>
    
                        <div class="nameUser">${requestScope.profile.getName()}</div>

                        <div class="block" id="imageBlock">
                            <!-- <h1 class="description">Аватар</h1> -->
                            
                            <!-- <div class="preview">

                                <img src="Images/fox.jpg" alt="" id="image-preview">


                            </div> -->

                            <!-- <div id="carouselExampleIndicators" class="carousel slide w-50" style="height: 500px !important;" data-ride="carousel">
                                <ol class="carousel-indicators">
                                  <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                                  <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                                  <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                                </ol>
                                <div class="carousel-inner" style=" height: 500px !important;">
                                  <div class="carousel-item active">
                                    <img class="d-block w-100" src="Images/Red_fox.jpg" alt="First slide" id="image-preview">
                                  </div>
                                  
                                </div>
                                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                  <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                  <span class="sr-only">Next</span>
                                </a>
                              </div> -->


                              <!-- <img alt="..." src="Images/Red_fox.jpg" class="img-thumbnail" id="image-preview"> -->


                            <c:if test="${not empty requestScope.profile.getPhotoPath()}">
                                <img class="rounded img-rounded" alt="..." id="image-preview" src="${pageContext.request.contextPath}/app/images?path=${requestScope.profile.getPhotoPath()}"/>
                            </c:if>
                            <c:if test="${empty requestScope.profile.getPhotoPath()}">
                                <img class="rounded img-rounded" alt="..." id="image-preview" src="${pageContext.request.contextPath}/resources/img/profile_no_photo.png"/>
                            </c:if><br>



                           
                            <input type="file" name="photo" onchange="showPreview(event)" class="form-control form-control-lg" style="font-size: 18px; width: 747px;" id="customFile" />

                            

                        </div>




                        <div class="block" id="nameBlock">

                            <h1 class="description">Ім'я</h1>

                            <div class="form-group">
                                <input type="text" class="form-control form-control-lg" style="font-size: 18px; width: 747px;" id="exampleInputEmail1" placeholder="Введіть ім'я"
                                       name="name" value="${requestScope.profile.getName()}">
                                <!-- <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small> -->
                              </div>

                        </div>


                        <div class="block" id="ageBlock">

                            <h1 class="description">Вік</h1>

                            <div class="form-group">
                                <input type="number" class="form-control form-control-lg raz" style="font-size: 18px; width: 747px;" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Введіть вік"
                                       name="age" value="${requestScope.profile.getAge()}">
                                <!-- <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small> -->
                              </div>

                        </div>


                        
                        <div class="block" id="ageBlock">

                            <h1 class="description">Стать</h1>

                            <select name="gender" class="selectpicker" data-width="747px"  style="font-size: 18px;" aria-label="Default select example"
                                        title="Виберіть стать">
                                    <option data-tokens="${requestScope.profileGender}" value="${requestScope.profileGender}" selected>${requestScope.profileGender}</option>
                                    <c:forEach var="gender" items="${requestScope.otherGenders}">
                                        <option data-tokens="${gender}" value="${gender}">${gender}</option>
                                    </c:forEach>
                                </select>

                        </div>




        







                        <div class="block" id="textBlock">

                            <h1 class="description">Текст анкети</h1>

                            <textarea name="text" class="form-control form-control-lg textArea"  style="font-size: 18px; width: 747px;" id="exampleFormControlTextarea1">${requestScope.profile.getText()}</textarea>


                        </div>
                        
                       


                        <div class="block" id="cityBlock">

                            <h1 class="description">Місто</h1>

                                <select name="location" class="selectpicker" data-width="747px" data-live-search="true"
                                        title="Виберіть місто">
                                    <c:forEach var="city" items="${requestScope.cities}">
                                        <option data-tokens="${city}" value="${city}">${city}</option>
                                    </c:forEach>
                                    <option data-tokens="${requestScope.profileLocation}" value="${requestScope.profileLocation}" selected>${requestScope.profileLocation}</option>
                                    <c:forEach var="location" items="${requestScope.otherLocations}">
                                        <option data-tokens="${location}" value="${location}">${location}</option>
                                    </c:forEach>
                                </select>

                        </div>

                    


                        <div class="block" id="instrumentsBlock">

                            <h1 class="description">Інструменти</h1>

                            <select name="instruments" class="selectpicker" data-width="747px" multiple data-live-search="true"
                                        title="Виберіть інструменти">
                                <c:forEach var="instrument" items="${requestScope.profileInstruments}">
                                    <option data-tokens="${instrument}" value="${instrument}" selected>${instrument}</option>
                                </c:forEach>
                                <c:forEach var="instrument" items="${requestScope.otherInstruments}">
                                    <option data-tokens="${instrument}" value="${instrument}">${instrument}</option>
                                </c:forEach><br>
                                </select>
                              
                              

                        </div>

                        <div class="block" id="specialitiesBlock">

                            <h1 class="description">Мої спеціальності</h1>

                            <select name="specialities" id="specialty" class="selectpicker" data-width="747px" multiple data-live-search="true"
                                        title="Виберіть спеціальності">
                                <c:forEach var="speciality" items="${requestScope.profileSpecialities}">
                                    <option data-tokens="${speciality}" value="${speciality}" selected>${speciality}</option>
                                </c:forEach>
                                <c:forEach var="speciality" items="${requestScope.otherSpecialities}">
                                    <option data-tokens="${speciality}" value="${speciality}">${speciality}</option>
                                </c:forEach><br>
                                </select>
                              
                              

                        </div>




                        <div class="block">
                        <input id="submitFormBtn" value="ОНОВИТИ АНКЕТУ"  type="submit" class="main-button-submit display-none"></input>
                    </div>
                    </form>
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