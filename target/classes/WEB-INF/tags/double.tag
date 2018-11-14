<%--
    Document   : currency
    Created on : Dec 12, 2017, 9:11:12 AM
    Author     : Vagner MALHEIROS
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Campo Inteiro" pageEncoding="iso-8859-1"%>

<%@ attribute name="id" required="true" %>
<%@ attribute name="label" required="true"   %>
<%@ attribute name="maxlength" required="true"   %>
<%@ attribute name="required" required="false"   %>
<%@ attribute name="readonly" required="false"   %>
<%@ attribute name="valuex" required="false"   %>
<%@ attribute name="onkeypress" required="false"   %>
<%@ attribute name="onblur" required="false"   %>
<%@ attribute name="onfocus" required="false"   %>
<%@ attribute name="size" required="false"   %>
<%@ attribute name="placeholder" required="false"   %>

<c:set var="putRequired" value=""/>

<c:if test="${required == true}">
    <c:set var="putRequired" value="required"/>
</c:if>

<div class="form-group">
    <label>${label}</label>
    <input class="form-control" maxlength="${maxlength}" ${putRequired} ${readonly} placeholder="${placeholder}" type="numeric" step="any" name="${id}" id="${id}" value="${valuex}" onkeypress="${onkeypress}" onfocus="${onfocus}" onblur="${onblur}" size="${size}" />
</div>