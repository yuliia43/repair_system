<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>

    <fmt:setLocale value="${locale}"/>
    <fmt:setBundle basename="${bundle}"/>

    <title>Base</title>
    <style>
        <%@include file="../styles/style.css" %>
    </style>
</head>
<body>
<ul class="menu">
    <li><a href="/"><fmt:message key="label.mainMenu.about"/></a></li>
    <c:choose>
        <c:when test="${user.getRole() eq 'user'}">
            <li><a href="/userPage"><fmt:message key="label.mainMenu.userPage"/></a></li>
            <li><a href="/usersApplication"><fmt:message key="label.mainMenu.myApplications"/></a></li>
            <li><a href="/newApplication"><fmt:message key="label.mainMenu.newApplication"/></a></li>
            <li><a href="/mastersStats"><fmt:message key="label.mainMenu.viewMasters"/></a></li>
            <li><a href="/signOut"><fmt:message key="label.mainMenu.signOut"/> </a></li>
        </c:when>
        <c:when test="${user.getRole() eq 'manager'}">
            <li><a href="/userPage"><fmt:message key="label.mainMenu.userPage"/></a></li>
            <li><a href="/managerApplication"><fmt:message key="label.mainMenu.viewApplications"/></a></li>
            <li><a href="/mastersStats"><fmt:message key="label.mainMenu.viewMasters"/></a></li>
            <li><a href="/signOut"><fmt:message key="label.mainMenu.signOut"/> </a></li>
        </c:when>
        <c:when test="${user.getRole() eq 'master'}">
            <li><a href="/userPage"><fmt:message key="label.mainMenu.userPage"/></a></li>
            <li><a href="/masterNewApplication"><fmt:message key="label.mainMenu.viewApplications"/></a></li>
            <li><a href="/mastersApplications"><fmt:message key="label.mainMenu.myApplications"/></a></li>
            <li><a href="/mastersStats"><fmt:message key="label.mainMenu.viewMasters"/></a></li>
            <li><a href="/signOut"><fmt:message key="label.mainMenu.signOut"/> </a></li>
        </c:when>
        <c:otherwise>
            <li><a href="/mastersStats"><fmt:message key="label.mainMenu.viewMasters"/></a></li>
            <li><a href="/authorisation"><fmt:message key="label.mainMenu.signIn"/></a></li>
            <li><a href="/registration"><fmt:message key="label.mainMenu.signUp"/> </a></li>
        </c:otherwise>
    </c:choose>
    <li>
    <form method="get">
    <select name="locale" style="margin: 20px 20px; float: right" onchange="this.form.submit()">
        <option value="en" ${locale eq 'en' ? 'selected' : ''}>EN</option>
        <option value="uk" ${locale eq 'uk' ? 'selected' : ''}>UK</option>
    </select>
    </form>
    </li>
</ul>
</body>
</html>
