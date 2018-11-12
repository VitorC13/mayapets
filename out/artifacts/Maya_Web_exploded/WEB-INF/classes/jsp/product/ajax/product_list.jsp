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
<%@ page import="dao.ProductDAO" %>
<%@ page import="model.Product" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1" %>
<%
    Connection connection = null;
    connection = ConnectionFactory.openConnection();
    ProductDAO dao = new ProductDAO(connection);
    List<Product> list = null;
    try {
        list = dao.getList();
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("listSize", list.size());
%>
<script type="text/javascript">
    var listProduct = {
    <c:forEach var="product" items="${list}" begin="0" end="${listSize}">
    ${product.id} :
    "${product.name} - ${product.type}",
    </c:forEach>
    }
    ;
</script>
