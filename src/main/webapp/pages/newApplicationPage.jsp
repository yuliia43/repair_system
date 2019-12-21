<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page='base.jsp'/>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html>
<head>
    <title>New Application</title>
</head>
<body>
<div class="box">
    <h3><fmt:message key="label.mainMenu.newApplication"/></h3>
    <hr align="left">
    <form method="post">
        <table>
            <tr>
                <td style="padding: 20px">
                    <fmt:message key="label.newApplication.details"/>:
                </td>
                <td width="75%">
                    <textarea name="details" cols="25" rows="5"></textarea>
                </td>
            </tr>
        </table>
        <c:if test="${emptyFields}">
        <p class="error">
                <fmt:message key="error.wrongInput.allFieldsRequired"/>
            </c:if>
            <button class="btn" style="float: right; margin-right: 100px"><fmt:message
                    key="label.newApplication.send"/></button>
    </form>
</div>
</body>
</html>
