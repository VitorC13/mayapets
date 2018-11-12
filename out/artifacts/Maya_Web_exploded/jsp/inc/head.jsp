<%--
  Created by IntelliJ IDEA.
  User: vitor
  Date: 09/10/18
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Maya - Pets Clothes</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css"
          crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="css/dashboard.css" rel="stylesheet">
    <link href="css/nav.css" rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark btn-outline-danger">


    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link"  id="customer" href="Customer">
                    <i class="fas fa-building"></i>
                    &nbsp&nbspCustomer
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" id="product" href="Product">
                    <i class="fas fa-paw"></i>
                    &nbsp&nbspProduct
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" id="user" href="User">
                    <i class="fas fa-users"></i>
                    &nbsp&nbspUser
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" id="collection" href="Collection">
                    <i class="fas fa-clipboard-list"></i>
                    &nbsp&nbspCollection
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" id="price" href="Price">
                    <i class="fas fa-dollar-sign"></i>
                    &nbsp&nbspPrice
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" id="stock" href="Stock">
                    <i class="fas fa-clipboard-list"></i>
                    &nbsp&nbspStock
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" id="barcode" href="Barcode">
                    <i class="fas fa-barcode"></i>
                    &nbsp&nbspBarcode
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#">
                    <i class="fas fa-list-alt"></i>
                    &nbsp&nbspRelatorios
                </a>
            </li>

        </ul>

        <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
                <a class="nav-link" id="logout" href="/maya">Sign out</a>
            </li>
        </ul>
    </div>

</nav>
