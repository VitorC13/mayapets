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
    <!-- CSS -->
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            background-color: #17a2b8;
            background-size: cover;
            background-position: center;
            font-family: sans-serif;
        }


    </style>
    x

</head>
<body>

<div class="jumbotron jumbotron-fluid" style="background-color: aliceblue">
    <div class="container" align="center">
        <div class="page-header">
            <div class="loginbox">
                <img src="Imagens/Log.png" width="250" height="90">


                <%
                    String msg = (String) request.getAttribute("msg");
                    if (msg != null) {
                %>
                <div class="alert alert-danger" role="alert">
                    <%=msg%>
                </div>
                <%}%>
                <br>
                <form action="LoginController" method="POST">

                    <!-- Login -->
                    <div class="form-group input-group" style="width: 250px">
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-user"></span>
                </span>
                        <input type="text" class="form-control"
                               id="txtLogin" placeholder="Login">
                    </div>


                    <!-- Senha -->
                    <div class="form-group input-group" style="width: 250px">
                <span class="input-group-addon">
                   <span class="glyphicon glyphicon-lock"></span>
                </span>
                        <input type="password" class="form-control"
                               id="txtPassword" placeholder="Senha">
                    </div>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox"> Lembrar-me
                        </label>
                    </div>


                    <div class="input-group input-group-sm" style="width: 250px">
                        <button class="btn btn-primary" width="10" type="submit" name="action" value="auth">Entrar
                        </button>
                    </div>


                </form>
            </div>
        </div>
    </div>
</div>
<c:import url="jsp/inc/bottom_default.jsp"/>
</body>
</html>
