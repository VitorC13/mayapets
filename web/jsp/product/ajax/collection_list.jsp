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
<%@ page import="dao.CollectionDAO" %>
<%@ page import="model.Collection" %>
<%@ page import="java.sql.SQLException" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1" %>
<%
    Connection connection = null;
    connection = ConnectionFactory.openConnection();
    CollectionDAO dao = new CollectionDAO(connection);
    List<Collection> list = null;
    try {
        list = dao.getList();
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("listSize", list.size());
%>
<script type="text/javascript">
    var listCollection = {
    <c:forEach var="collection" items="${list}" begin="0" end="${listSize}">
    ${collection.id} :
    " ${collection.name}",
    </c:forEach>
    }
    ;
</script>
