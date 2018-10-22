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
<script src="/maya/js/jquery.min.js"></script>
<script src="/maya/js/jquery-ui.min.js"></script>

<script src="/maya/js/popper.js"></script>

<script src="/maya/js/datatables/datatables.min.js"></script>

<script src="/maya/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var leftMenu = document.querySelector("#leftMenu");
    var url = document.URL;
    resultado = url.split("/");

    if (resultado[4] == 'User') {
        $('a#user').addClass("active");
    } else if (resultado[4] == 'Customer') {
        $('a#customer').addClass("active");
    }

    leftMenu.addEventListener("click", function () {
        if (resultado[4] == 'User') {
            $('a#user').addClass("active");
        } else if (resultado[4] == 'Customer') {
            $('a#customer').addClass("active");
        }
    });


</script>

</body>
</html>
