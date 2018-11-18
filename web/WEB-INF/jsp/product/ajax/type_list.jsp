<%--
  Created by IntelliJ IDEA.
  User: vitor
  Date: 13/10/18
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List" %>
<%@ page import="model.TypeStock" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1" %>
<%
    Map<Integer, TypeStock> list = new LinkedHashMap<>();
    list.put(1, TypeStock.Normal);
    list.put(2, TypeStock.Varejo);
    list.put(3, TypeStock.Atacado);
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("listSize", list.size());
%>
<script type="text/javascript">
    var listType = {
    <c:forEach var="type" items="${list}" begin="0" end="${listSize}">
    ${type.value}:
    " ${type.value}",
    </c:forEach>
    }
    ;
</script>
