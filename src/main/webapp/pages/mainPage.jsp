<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page='base.jsp'/>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <title>Main</title>
</head>
<body>
<div class="box">
    <h3><fmt:message key="label.mainPage.welcome"/></h3>
    <hr align="left">
    <fmt:message key="label.mainPage.systemInfo"/>
    <br><br><br>
    <a href="/authorisation" class="btn"><fmt:message key="label.mainMenu.signIn"/></a>
    <a href="/registration" class="btn"><fmt:message key="label.mainMenu.signUp"/></a>
</div>
</body>
</html>
