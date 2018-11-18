<%--
  Created by IntelliJ IDEA.
  User: vitor
  Date: 09/10/18
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>

<script src="${pageContext.request.contextPath}/js/popper.js"></script>

<script src="${pageContext.request.contextPath}/js/datatables/datatables.min.js"></script>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var url = document.URL;
    //var resultado = url.split("/");

    if (url.includes('User')) {
        $('a#user').addClass("active");
    } else if (url.includes('Customer')) {
        $('a#customer').addClass("active");
    } else if (url.includes('Product')) {
        $('a#product').addClass("active");
    }else if (url.includes('Collection')) {
        $('a#collection').addClass("active");
    }else if (url.includes('Price')) {
        $('a#price').addClass("active");
    }else if (url.includes('Stock')) {
        $('a#stock').addClass("active");
    }else if (url.includes('Barcode')) {
        $('a#barcode').addClass("active");
    }

    document.querySelector("#logout").addEventListener("click", function (){
        sessionStorage.removeItem('user');
        sessionStorage.clear();
    });


</script>

</body>
</html>
