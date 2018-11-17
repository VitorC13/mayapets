<%--
    Document   : currency
    Created on : Dec 12, 2017, 9:11:12 AM
    Author     : Vagner MALHEIROS
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Campo Inteiro" pageEncoding="iso-8859-1"%>

<%@ attribute name="id" required="true" %>
<%@ attribute name="valuex" required="false"   %>

<div class="form-group">
    <input class="form-control" type="hidden" name="${id}" id="${id}" value="${valuex}" />
</div>