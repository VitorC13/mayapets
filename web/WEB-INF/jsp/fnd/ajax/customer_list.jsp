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
<%@page import="dao.CustomerDAO" %>
<%@page import="model.Customer" %>
<%@page contentType="text/html" pageEncoding="iso-8859-1" %>
<%
    Connection connection = null;
    connection = ConnectionFactory.getConnection();
    CustomerDAO dao = new CustomerDAO(connection);
    List<Customer> list = dao.getList();
    pageContext.setAttribute("list", list);
    pageContext.setAttribute("listSize", list.size());
%>
<script type="text/javascript">
    var listCustomer = {
    <c:forEach var="customer" items="${list}" begin="0" end="${listSize}">
    ${customer.id} :
    "${customer.name} - ${customer.cpfCnpj}",
    </c:forEach>
    }
    ;
</script>
