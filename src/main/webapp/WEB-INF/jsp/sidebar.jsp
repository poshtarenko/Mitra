<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<aside class="headerAside">
<div class="logo">MITRA</div>
<nav class="navigation">
    <div class="userCabinet">
        <li class="link">
            <a href="${pageContext.request.contextPath}/app/me" class="linkInner">
                <div class="avatar">

                    <i class="fa-solid fa-user fa"></i>

                </div>

                <p id="name" class="userName">${sessionScope.get("USER").getProfile().getName()}</p>
            </a>
        </li>

    </div>


    <ul class="pagesLinks">

        <li class="link">
            <a href="${pageContext.request.contextPath}/app/go" class="linkInner">
                <div class="icon-space">
                    <i class="fa-solid fa-magnifying-glass fa"></i>
                </div>

                Пошук
            </a>
        </li>

        <li class="link">
            <a class="linkInner">
                <div class="icon-space">
                    <i class="fa-solid fa-comment-dots fa"></i>
                </div>

                Чат
            </a>
        </li>

        <li class="link">
            <a href="${pageContext.request.contextPath}/app/likes" class="linkInner">
                <div class="icon-space">
                    <i class="fa-solid fa-heart fa"></i>

                </div>

                Лайки
            </a>
        </li>

        <li class="link">
            <a class="linkInner">
                <div class="icon-space">
                    <i class="fa-solid fa-star fa"></i>

                </div>

                Обрані
            </a>
        </li>


    </ul>


</nav>
<button class="btn-blue buy-premium">
    <!-- <i class="fa-solid fa-crown fa"></i> -->
    Придбати преміум
</button>
</aside>

