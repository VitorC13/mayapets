<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
    Document   : index
    Created on : Mar 5, 2017, 5:53:44 PM
    Author     : vitor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Autenticação</title>
    <c:import url="jsp/inc/head_default.jsp"/>
</head>
<body class="bg-light">

<div class="jumbotron jumbotron-fluid bg-dark">
    <div class="container" align="center">
        <div class="loginbox">
            <%
                String msg = (String) request.getAttribute("msg");
                if (msg != null) {
            %>
            <div class="alert alert-danger" role="alert">
                <%=msg%>
            </div>
            <%}%>
            <br>
            <form action="Login" method="post">

                <!-- Login -->
                <div class="form-group input-group" style="width: 250px">
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-user"></span>
                </span>
                    <input type="text" class="form-control"
                           id="txtLogin" name="txtLogin" placeholder="Login">
                </div>


                <!-- Senha -->
                <div class="form-group input-group" style="width: 250px">
                <span class="input-group-addon">
                   <span class="glyphicon glyphicon-lock"></span>
                </span>
                    <input type="password" class="form-control"
                           id="txtPassword" name="txtPassword" placeholder="Senha">
                </div>

                <button class="btn btn-info" width="10" type="submit" name="action" value="auth">Entrar
                </button>
            </form>
        </div>
    </div>
</div>
<c:import url="jsp/inc/bottom_default.jsp"/>
</body>
</html>
