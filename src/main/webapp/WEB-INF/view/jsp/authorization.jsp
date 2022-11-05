<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/additional.css"> -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <title>Mitra</title>
</head>
<body>

<div class="transition-2 isActive"></div>

<div class="background">
    <div class="formBlock">
        <div class="left">
            <div class="leftInner">


                <div class="login-sign">Авторизація </div>

                <form action="${pageContext.request.contextPath}/app/auth" method="post">
                    <div class="input-box email-box">
                        <i class="fas fa-envelope"></i>
                        <input name="email" type="text" placeholder="Введіть ваш email" required>
                    </div>

                    <div class="input-box password-box">
                        <i class="fas fa-lock"></i>
                        <input name="password" type="password" placeholder="Введіть ваш пароль" required>
                    </div>
                    <a href="D:\ОНПУ_РАБОТЫ\3 КУРС\Гнучки методології\Project\Pages\forgotPassword.html" class="forgot-password">Забули пароль?</a>

                    <button type="submit" class="main-button">
                        Увійти
                    </button>
                </form>

                <div class="to-login">Немає аккаунта? <a href="${pageContext.request.contextPath}/app/register" class="authorization">Зареєструйтесь</a></div>


            </div>
        </div>


        <div class="right">
            <div class="rightInner">
                <div class="logo">MITRA</div>
                <div class="tagline">Ми об'єднуємо!</div>


            </div>
        </div>


    </div>
</div>
    <script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>
</html>