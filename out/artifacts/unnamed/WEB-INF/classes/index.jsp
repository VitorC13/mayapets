<%-- 
    Document   : index
    Created on : Mar 5, 2017, 5:53:44 PM
    Author     : vitor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Autenticação</title>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
   
     <!-- Icons -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
          
    <!-- CSS -->
    <style type="text/css">
        body{
           margin: 0;
           padding: 0;
           background-color: #9F332B;
           background-size: cover;
           background-position: center;
           font-family: sans-serif;
        }
        
        
    </style>
    
    
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
        <form action="AcessoController" method="POST">
            
             <!-- Login -->
            <div class ="form-group input-group"style="width: 250px">
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-user"></span>
                </span>
                <input type ="text" class="form-control"
                       name="txtLogin" placeholder="Login">
            </div>
           
            
             <!-- Senha -->
            <div class ="form-group input-group"style="width: 250px">
                <span class="input-group-addon">
                   <span class="glyphicon glyphicon-lock"></span>
                </span>
                <input type ="password" class="form-control"
                       name="txtSenha" placeholder="Senha">
            </div>
           
             <div class="checkbox">
                <label>
                    <input type="checkbox" > Lembrar-me
                </label>
            </div>
             
            
            
             
             
            <div class="input-group input-group-sm" style="width: 250px">
            <button class="btn btn-primary" width="10" type="submit" name="acao" value="Entrar">Entrar</button>
            </div>
             
             
        </form>
        </div>
         </div>
    </div>
</div>


<!-- Compiled and minified JavaScript -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>

</body>
</html>
