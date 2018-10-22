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
<c:set var="action" value="new"/>
<link href="/maya/js/autocompletepi/styles.css" rel="stylesheet"/>
<div class="modal fade bd-example-modal-md" tabindex="-1" id="userForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Novo Usuario</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="User">
                    <fmt:message key='modal.user.name' var="name"/>
                    <fmt:message key='modal.user.login' var="login"/>
                    <fmt:message key='modal.user.email' var="email"/>
                    <fmt:message key='modal.user.cpf' var="cpf"/>
                    <fmt:message key='modal.user.addres' var="addres"/>
                    <fmt:message key='modal.user.password' var="password"/>
                    <fmt:message key='modal.user.customer' var="customer"/>

                    <maya:character id="txtName" label="${name}" maxlength="255" required="true"
                                    valuex="" placeholder="${name}"/>

                    <maya:character id="txtLogin" label="${login}" maxlength="255" required="true"
                                    valuex="" placeholder="${login}"/>

                    <maya:character id="txtCpf" label="${cpf}" maxlength="255" required="true"
                                    valuex="" placeholder="${cpr}"/>

                    <maya:email id="txtEmail" label="${email}" maxlength="255" required="true"
                                valuex="" placeholder="${email}"/>

                    <maya:character id="txtAddres" label="${addres}" maxlength="255" required="true"
                                    valuex="" placeholder="${addres}"/>

                    <maya:character id="txtCustomer_autocomplete" label="${customer}" maxlength="255" required="false"
                                    valuex=""/>

                    <maya:hidden id="txtCustomer" valuex=""/>

                    <maya:password id="txtPassword" label="${password}" required="true"
                                   valuex="" placeholder="${password}"/>


            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" name="action" value="new">Save changes</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>
            </form>
        </div>
    </div>
</div>
<c:import url="/jsp/fnd/ajax/customer_list.jsp"/>
<script src="/maya/js/jquery.min.js"></script>
<script type="text/javascript" src="/maya/js/autocompletepi/jquery.autocomplete.js"></script>
<script type="text/javascript">
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
</script>
