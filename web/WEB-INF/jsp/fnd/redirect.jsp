<%-- 
    Document   : redirect
    Created on : Apr 4, 2018, 9:32:58 AM
    Author     : S0074009
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="controller" value="${redirectParams.controller}"/>
<c:set var="actions" value="${redirectParams.actions}"/>

<c:url value="?controller=${controller}&actions=${actions}" var="url"/>

<% request.setAttribute("redirectParams", null); %>

<c:redirect url = "${url}"/>
