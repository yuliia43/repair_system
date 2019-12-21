<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page='base.jsp'/>


<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <title>Applications</title>
</head>
<body>
<div class="box">
    <h3><fmt:message key="label.mainMenu.viewApplications"/></h3>
    <hr>
    <c:if test="${applications.size() != 0}">
        <c:if test="${notChecked}">
            <p class="error">
                <fmt:message key="error.wrongInput.NothingChosen"/></p>
        </c:if>
        <form method="post">
            <table>
                <tr>
                    <th></th>
                    <th><fmt:message key="label.applications.repairDetails"/></th>
                    <th><fmt:message key="label.applications.price"/></th>
                </tr>
                <c:forEach items="${applications}" var="application" varStatus="loop">
                    <tr>
                        <td><input name="applicationId" type="checkbox"
                                   value="${application.getApplicationId()}"></td>
                        <td>${application.getRepairDetails()}</td>
                        <td>${application.getPrice()}</td>
                    </tr>
                </c:forEach>
            </table>
            <button><fmt:message key="label.applications.finish"/></button>
        </form>
    </c:if>
    <c:if test="${applications.size() == 0}">
        <fmt:message key="label.applications.noApplications"/>!
        <br>
        <a href="/masterNewApplication" class="btn"><fmt:message key="label.mainMenu.viewApplications"/></a>
    </c:if>
</div>
</body>
</html>
