<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<h1>Mitra</h1>

<div style="display: flex">
    <form action="${pageContext.request.contextPath}/app/me" method="get">
        <input type="submit" value="Моя анкета">
    </form>
    <form action="${pageContext.request.contextPath}/app/go" method="get">
        <input type="submit" value="Пошук свайпом">
    </form>
    <form action="${pageContext.request.contextPath}/app/search" method="get">
        <input type="submit" value="Дивитися всі анкети">
    </form>
    <form action="${pageContext.request.contextPath}/app/logout" method="post">
        <input type="submit" value="Вийти з акаунту">
    </form>
</div>
