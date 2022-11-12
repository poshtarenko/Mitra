<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%@ include file="header.jsp" %>

<br><h2>Лайки</h2>

<h2>Мої лайки :</h2>
<c:forEach var="like" items="${requestScope.ownLikes}">
    <span>${like.getName()}   </span>
</c:forEach>


</body>
</html>
