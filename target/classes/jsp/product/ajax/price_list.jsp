<%--
  Created by IntelliJ IDEA.
  User: vitor
  Date: 13/10/18
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List" %>
<%@page import="java.sql.Connection" %>
<%@page import="jndi.ConnectionFactory" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="dao.PriceDAO" %>
<%@ page import="model.Price" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1" %>
<%
    Connection connection = null;
    connection = ConnectionFactory.openConnection();
    PriceDAO dao = new PriceDAO(connection);
    List<Price> list = null;
    try {
        list = dao.getList();
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("listSize", list.size());
%>
<script type="text/javascript">
    var listPrice = {
    <c:forEach var="price" items="${list}" begin="0" end="${listSize}">
    ${price.id} :
    " ${price.product.name}  -    N=R$${price.price} / V=R$${price.priceVar} / A=R$${price.priceAtc}",
    </c:forEach>
    }
    ;
</script>
