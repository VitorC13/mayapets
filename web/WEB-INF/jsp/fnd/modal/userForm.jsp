<%--
  Created by IntelliJ IDEA.
  User: vitor
  Date: 13/10/18
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="maya" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="pt_BR"/>

<c:choose>
    <c:when test="${edit == true}">
        <c:set var="action" value="edit"/>
        <c:set var="title" value="Editar"/>
    </c:when>
    <c:otherwise>
        <c:set var="action" value="new"/>
        <c:set var="title" value="Novo"/>
    </c:otherwise>
</c:choose>

<link href="${pageContext.request.contextPath}/js/autocompletepi/styles.css" rel="stylesheet"/>
<div class="modal fade bd-example-modal-md" tabindex="-1" id="userForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${title} Usuario</h5>
                <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="reloadModal()"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="User">
                    <maya:hidden id="edit" valuex="${edit}"/>
                    <fmt:message key='modal.user.name' var="name"/>
                    <fmt:message key='modal.user.login' var="login"/>
                    <fmt:message key='modal.user.email' var="email"/>
                    <fmt:message key='modal.user.cpf' var="cpf"/>
                    <fmt:message key='modal.user.addres' var="addres"/>
                    <fmt:message key='modal.user.password' var="password"/>
                    <fmt:message key='modal.user.customer' var="customer"/>

                    <maya:hidden id="txtId" valuex="${userEdit.id}"/>
                    <maya:character id="txtName" label="${name}" maxlength="255" required="true"
                                    valuex="${userEdit.name}" placeholder="${name}"/>

                    <maya:character id="txtLogin" label="${login}" maxlength="255" required="true"
                                    valuex="${userEdit.login}" placeholder="${login}"
                                    onblur="checkLogin(document.getElementById('txtLogin').value);"/>

                    <maya:character id="txtCpf" label="${cpf}" maxlength="255" required="true"
                                    valuex="${userEdit.cpf}" placeholder="${cpr}"/>

                    <maya:email id="txtEmail" label="${email}" maxlength="255" required="true"
                                valuex="${userEdit.email}" placeholder="${email}"/>

                    <maya:character id="txtAddress" label="${addres}" maxlength="255" required="true"
                                    valuex="${userEdit.address}" placeholder="${addres}"/>

                    <maya:character id="txtCustomer_autocomplete" label="${customer}" maxlength="255" required="false"
                                    valuex="${userEdit.customer.name}"/>

                    <maya:hidden id="txtCustomer" valuex="${userEdit.customer.id}"/>

                    <maya:password id="txtPassword" label="${password}" required="true"
                                   valuex="" placeholder="${password}"/>


            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" name="action" value="${action}">Save changes</button>
                <button type="button" class="btn btn-danger" onclick="reloadModal()" data-dismiss="modal">Close</button>
            </div>
            </form>
        </div>
    </div>
</div>
<c:import url="/WEB-INF/jsp/fnd/ajax/customer_list.jsp"/>
<c:import url="/WEB-INF/jsp/fnd/ajax/check_login.jsp"/>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mask.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autocompletepi/jquery.autocomplete.js"></script>
<script type="text/javascript">
    var edit = document.querySelector("#edit");

    function reloadModal() {
        if (edit.value == "true") {
            window.location.href = "/maya/User";
        }
    }

    var listCustomerArray = $.map(listCustomer, function (value, key) {
        return {value: value, data: key};
    });
    $('#txtCustomer_autocomplete').devbridgeAutocomplete({
        lookup: listCustomerArray,
        autoSelectFirst: true,
        onSelect: function (value) {
            $('#txtCustomer').val(value.data);
        }
    });


    $("#txtCpf").keydown(function () {
        try {
            $("#txtCpf").unmask();
        } catch (e) {
        }
        var tamanho = $("#txtCpf").val().length;
        $("#txtCpf").mask("999.999.999-99");
        var elem = this;
        setTimeout(function () {
            // mudo a posição do seletor
            elem.selectionStart = elem.selectionEnd = 10000;
        }, 0);
        var currentValue = $(this).val();
        $(this).val('');
        $(this).val(currentValue);
    });

    function checkLogin(obj1) {
        var path = '/maya/User?action=checkLogin&loginTry=';
        console.log(path);
        $.ajax({
            type: "GET",
            cache: false,
            url: path + obj1,
            success: function (data) {
                if (data.trim() == "true") {
                    txtLogin.value = "";
                    txtLogin.focus();
                    alert("Login em uso!!");

                }
            }

        });
    }

</script>
