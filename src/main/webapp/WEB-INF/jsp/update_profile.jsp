<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/app/upd_profile" method="post">
    <label for="name">Name:</label><br>
    <input id="name" type="text" name="name" value="${requestScope.profileName}"><br>

    <label for="age">Age:</label><br>
    <input id="age" type="number" name="age" value="${requestScope.profileAge}"><br>

    <label for="gender">Gender:</label><br>
    <input type="radio" name="gender" id="gender" value="${requestScope.profileGender}" checked>${requestScope.profileGender}
    <c:forEach var="gender" items="${requestScope.otherGenders}">
        <input type="radio" name="gender" id="gender" value="${gender}">${gender}
    </c:forEach><br>

    <label for="text">Text:</label><br>
    <input id="text" type="text" name="text" value="${requestScope.profileText}"><br>

    <label for="location">Location:</label><br>
    <select name="location" id="location">
        <option value="${requestScope.profileLocation}" selected>${requestScope.profileLocation}</option>
        <c:forEach var="location" items="${requestScope.otherLocations}">
            <option value="${location}">${location}</option>
        </c:forEach>
    </select><br>

    <label for="instruments">Instruments:</label><br>
    <c:forEach var="instrument" items="${requestScope.profileInstruments}">
        <input type="checkbox" name="instruments" id="instruments" value="${instrument}" checked>${instrument}
    </c:forEach>
    <c:forEach var="instrument" items="${requestScope.otherInstruments}">
        <input type="checkbox" name="instruments" id="instruments" value="${instrument}">${instrument}
    </c:forEach>

    <br><br>
    <input type="submit" value="Update profile">
</form>
</body>
</html>
