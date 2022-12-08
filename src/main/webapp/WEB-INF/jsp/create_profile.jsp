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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styleFormCreate.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet"/>


    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.css" rel="stylesheet"/>


    <!-- BOOTSTRAP-SELECT -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">


    <title>Створення Анкети</title>
</head>
<body>

<div class="transition-2 isActive"></div>
<div class="background">
    <div class="formBlock">
        <div class="left">
            <div class="leftInner">
                <div class="title">Як тебе звати?</div>
                <div class="iconAndDescription">

                    <form class="forma" action="${pageContext.request.contextPath}/app/create_profile" method="post">



                            <div id="name-input-box" class="inpt">
                                <div class="input-box email-box">
                                    <input name="name" type="text" placeholder="" required>
                                </div>
                            </div>


                            <div id="age-input-box" class="inpt display-none">
                                <div class="input-box name-box">
                                    <input name="age" class="raz" type="number" placeholder="" required>
                                </div>
                            </div>


                            <div id="sex-input-box" class="inpt display-none">
                                <select name="gender" class="selectpicker w-100" aria-label="Default select example"
                                        title="Виберіть стать">
                                    <c:forEach var="gender" items="${requestScope.genders}">
                                        <option data-tokens="${gender}" value="${gender}">${gender}</option>
                                    </c:forEach>
                                </select>
                            </div>


                            <div id="text-input-box" class="inpt display-none">

                                <textarea name="text" class="form-control" id="exampleFormControlTextarea1"></textarea>

                            </div>


                            <div id="location-input-box" class="inpt display-none">
                                <select name="city" class="selectpicker w-100" data-live-search="true"
                                        title="Виберіть місто">
                                    <c:forEach var="city" items="${requestScope.cities}">
                                        <option data-tokens="${city}" value="${city}">${city}</option>
                                    </c:forEach>
                                </select>
                            </div>


                            <!-- <div id="age-input-box" class="input-box email-box display-none">
                                <input class="raz" type="number" placeholder="" required>
                            </div>

                            <select id="sex-input-box" class="form-select display-none" aria-label="Default select example">
                                <option selected>Чоловік</option>
                                <option value="1">Жінка</option>
                              </select> -->
                            
                              <div class="main-button">
                                Далі
                            </div>


                            <input id="submitFormBtn" value="ВІДПРАВИТИ АНКЕТУ"  type="submit" class="main-button-submit display-none"></input>

                    </form>
                        <!-- <div class="buttons-container"> -->     
                        <!-- </div> -->
                </div>

            </div>

        </div>

        <div class="right">
            <div class="rightInner">
                <div class="title">Почнемо Знайомитись!</div>
                <div class="iconAndDescription">
                <div class="rightContainer">
                    

                    <div class="icon-box">
                        <svg id="icon-name" class="icon display-true opacity-all" xmlns="http://www.w3.org/2000/svg"
                             viewBox="0 0 512 512">
                            <path d="M154.9 162c.3 .7 .7 1.5 1.1 2.2l17.8 30.9c11.1-12.6 27.4-19.8 44.4-19.1l-20.7-35.8c-6.6-11.5-21.3-15.4-32.8-8.8c-10.8 6.2-14.9 19.5-9.9 30.6zm173.6 47C399.7 231.7 448 297.8 448 372.5c0 1.5 0 3-.1 4.5c39.7-25.6 64.1-69.7 64.1-117.4V136c0-13.3-10.7-24-24-24s-24 10.7-24 24l0 81.7L347.8 16.5C341.2 5 326.5 1.1 315.1 7.7s-15.4 21.3-8.8 32.8l64 110.9c2.2 3.8 .9 8.7-2.9 10.9s-8.7 .9-10.9-2.9l-80-138.6C269.8 9.3 255.1 5.4 243.6 12s-15.4 21.3-8.8 32.8l80 138.6c2.2 3.8 .9 8.7-2.9 10.9s-8.7 .9-10.9-2.9L237 80.5c-6.6-11.5-21.3-15.4-32.8-8.8s-15.4 21.3-8.8 32.8l44 76.2L328.5 209zM64 488c0 12.4 9.4 22.6 21.5 23.9c.8 .1 1.6 .1 2.5 .1H288.7 296c66.3 0 120-53.7 120-120c0-1.2 0-2.4-.1-3.6c0-1.2 .1-2.5 .1-3.7c0-68-44-128.2-108.9-148.9l-83.9-26.7c-12.6-4-26.1 3-30.1 15.6s3 26.1 15.6 30.1L262.6 272H56c-13.3 0-24 10.7-24 24s10.7 24 24 24H184c4.4 0 8 3.6 8 8s-3.6 8-8 8H24c-13.3 0-24 10.7-24 24s10.7 24 24 24H184c4.4 0 8 3.6 8 8s-3.6 8-8 8H56c-13.3 0-24 10.7-24 24s10.7 24 24 24H184c4.4 0 8 3.6 8 8s-3.6 8-8 8H88c-13.3 0-24 10.7-24 24z"/>
                        </svg>
                        <svg id="icon-age" class="icon display-none" xmlns="http://www.w3.org/2000/svg"
                             viewBox="0 0 448 512">
                            <path d="M86.4 5.5L61.8 47.6C58 54.1 56 61.6 56 69.2V72c0 22.1 17.9 40 40 40s40-17.9 40-40V69.2c0-7.6-2-15-5.8-21.6L105.6 5.5C103.6 2.1 100 0 96 0s-7.6 2.1-9.6 5.5zm128 0L189.8 47.6c-3.8 6.5-5.8 14-5.8 21.6V72c0 22.1 17.9 40 40 40s40-17.9 40-40V69.2c0-7.6-2-15-5.8-21.6L233.6 5.5C231.6 2.1 228 0 224 0s-7.6 2.1-9.6 5.5zM317.8 47.6c-3.8 6.5-5.8 14-5.8 21.6V72c0 22.1 17.9 40 40 40s40-17.9 40-40V69.2c0-7.6-2-15-5.8-21.6L361.6 5.5C359.6 2.1 356 0 352 0s-7.6 2.1-9.6 5.5L317.8 47.6zM128 176c0-17.7-14.3-32-32-32s-32 14.3-32 32v48c-35.3 0-64 28.7-64 64v71c8.3 5.2 18.1 9 28.8 9c13.5 0 27.2-6.1 38.4-13.4c5.4-3.5 9.9-7.1 13-9.7c1.5-1.3 2.7-2.4 3.5-3.1c.4-.4 .7-.6 .8-.8l.1-.1 0 0 0 0s0 0 0 0s0 0 0 0c3.1-3.2 7.4-4.9 11.9-4.8s8.6 2.1 11.6 5.4l0 0 0 0 .1 .1c.1 .1 .4 .4 .7 .7c.7 .7 1.7 1.7 3.1 3c2.8 2.6 6.8 6.1 11.8 9.5c10.2 7.1 23 13.1 36.3 13.1s26.1-6 36.3-13.1c5-3.5 9-6.9 11.8-9.5c1.4-1.3 2.4-2.3 3.1-3c.3-.3 .6-.6 .7-.7l.1-.1c3-3.5 7.4-5.4 12-5.4s9 2 12 5.4l.1 .1c.1 .1 .4 .4 .7 .7c.7 .7 1.7 1.7 3.1 3c2.8 2.6 6.8 6.1 11.8 9.5c10.2 7.1 23 13.1 36.3 13.1s26.1-6 36.3-13.1c5-3.5 9-6.9 11.8-9.5c1.4-1.3 2.4-2.3 3.1-3c.3-.3 .6-.6 .7-.7l.1-.1c2.9-3.4 7.1-5.3 11.6-5.4s8.7 1.6 11.9 4.8l0 0 0 0 0 0 .1 .1c.2 .2 .4 .4 .8 .8c.8 .7 1.9 1.8 3.5 3.1c3.1 2.6 7.5 6.2 13 9.7c11.2 7.3 24.9 13.4 38.4 13.4c10.7 0 20.5-3.9 28.8-9V288c0-35.3-28.7-64-64-64V176c0-17.7-14.3-32-32-32s-32 14.3-32 32v48H256V176c0-17.7-14.3-32-32-32s-32 14.3-32 32v48H128V176zM448 394.6c-8.5 3.3-18.2 5.4-28.8 5.4c-22.5 0-42.4-9.9-55.8-18.6c-4.1-2.7-7.8-5.4-10.9-7.8c-2.8 2.4-6.1 5-9.8 7.5C329.8 390 310.6 400 288 400s-41.8-10-54.6-18.9c-3.5-2.4-6.7-4.9-9.4-7.2c-2.7 2.3-5.9 4.7-9.4 7.2C201.8 390 182.6 400 160 400s-41.8-10-54.6-18.9c-3.7-2.6-7-5.2-9.8-7.5c-3.1 2.4-6.8 5.1-10.9 7.8C71.2 390.1 51.3 400 28.8 400c-10.6 0-20.3-2.2-28.8-5.4V480c0 17.7 14.3 32 32 32H416c17.7 0 32-14.3 32-32V394.6z"/>
                        </svg>
                        <svg id="icon-sex" class="icon display-none" xmlns="http://www.w3.org/2000/svg"
                             viewBox="0 0 320 512">
                            <path d="M160 96c-26.5 0-48-21.5-48-48s21.5-48 48-48s48 21.5 48 48s-21.5 48-48 48zm8 256V128h6.9c33.7 0 64.9 17.7 82.3 46.6l58.3 97c9.1 15.1 4.2 34.8-10.9 43.9s-34.8 4.2-43.9-10.9L232 256.9V480c0 17.7-14.3 32-32 32s-32-14.3-32-32V352h0zM58.2 182.3c19.9-33.1 55.3-53.5 93.8-54.3V384h0v96c0 17.7-14.3 32-32 32s-32-14.3-32-32V384H70.2c-10.9 0-18.6-10.7-15.2-21.1L93.3 248.1 59.4 304.5c-9.1 15.1-28.8 20-43.9 10.9s-20-28.8-10.9-43.9l53.6-89.2z"/>
                        </svg>
                        <svg id="icon-text" class="icon display-none" xmlns="http://www.w3.org/2000/svg"
                             viewBox="0 0 512 512">
                            <path d="M373.1 24.97C401.2-3.147 446.8-3.147 474.9 24.97L487 37.09C515.1 65.21 515.1 110.8 487 138.9L289.8 336.2C281.1 344.8 270.4 351.1 258.6 354.5L158.6 383.1C150.2 385.5 141.2 383.1 135 376.1C128.9 370.8 126.5 361.8 128.9 353.4L157.5 253.4C160.9 241.6 167.2 230.9 175.8 222.2L373.1 24.97zM440.1 58.91C431.6 49.54 416.4 49.54 407 58.91L377.9 88L424 134.1L453.1 104.1C462.5 95.6 462.5 80.4 453.1 71.03L440.1 58.91zM203.7 266.6L186.9 325.1L245.4 308.3C249.4 307.2 252.9 305.1 255.8 302.2L390.1 168L344 121.9L209.8 256.2C206.9 259.1 204.8 262.6 203.7 266.6zM200 64C213.3 64 224 74.75 224 88C224 101.3 213.3 112 200 112H88C65.91 112 48 129.9 48 152V424C48 446.1 65.91 464 88 464H360C382.1 464 400 446.1 400 424V312C400 298.7 410.7 288 424 288C437.3 288 448 298.7 448 312V424C448 472.6 408.6 512 360 512H88C39.4 512 0 472.6 0 424V152C0 103.4 39.4 64 88 64H200z"/>
                        </svg>
                        <svg id="icon-location" class="icon display-none" xmlns="http://www.w3.org/2000/svg"
                             viewBox="0 0 576 512">
                            <path d="M408 120c0 54.6-73.1 151.9-105.2 192c-7.7 9.6-22 9.6-29.6 0C241.1 271.9 168 174.6 168 120C168 53.7 221.7 0 288 0s120 53.7 120 120zm8 80.4c3.5-6.9 6.7-13.8 9.6-20.6c.5-1.2 1-2.5 1.5-3.7l116-46.4C558.9 123.4 576 135 576 152V422.8c0 9.8-6 18.6-15.1 22.3L416 503V200.4zM137.6 138.3c2.4 14.1 7.2 28.3 12.8 41.5c2.9 6.8 6.1 13.7 9.6 20.6V451.8L32.9 502.7C17.1 509 0 497.4 0 480.4V209.6c0-9.8 6-18.6 15.1-22.3l122.6-49zM327.8 332c13.9-17.4 35.7-45.7 56.2-77V504.3L192 449.4V255c20.5 31.3 42.3 59.6 56.2 77c20.5 25.6 59.1 25.6 79.6 0zM288 152c22.1 0 40-17.9 40-40s-17.9-40-40-40s-40 17.9-40 40s17.9 40 40 40z"/>
                        </svg>
                    </div>
                    <h2 class="description">Напиши як тебе звуть. </h2>

                </div>
                </div>

            </div>
        </div>


        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>


<script src="${pageContext.request.contextPath}/resources/js/formCreateScript.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/i18n/defaults-*.min.js"></script>


<script src="${pageContext.request.contextPath}/resources/js/styleSelect.js"></script>
</body>
</html>