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
        <c:set var="title" value="Nova"/>
    </c:otherwise>
</c:choose>
<link href="${pageContext.request.contextPath}/js/autocompletepi/styles.css" rel="stylesheet"/>
<div class="modal fade bd-example-modal-md" tabindex="-1" id="customerForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${title} Empresa</h5>
                <button type="button" class="close" data-dismiss="modal" onclick="reloadModal()"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="Customer">
                    <maya:hidden id="edit" valuex="${edit}"/>
                    <fmt:message key='modal.customer.name' var="name"/>
                    <fmt:message key='modal.customer.razao' var="razao"/>
                    <fmt:message key='modal.customer.cpfcnpj' var="cpf"/>
                    <fmt:message key='modal.customer.city' var="city"/>
                    <fmt:message key='modal.customer.address' var="address"/>
                    <fmt:message key='modal.customer.country' var="country"/>
                    <fmt:message key='modal.customer.state' var="state"/>

                    <maya:hidden id="txtId" valuex="${customerEdit.id}"/>
                    <maya:character id="txtName" label="${name}" maxlength="255" required="true"
                                    valuex="${customerEdit.name}" placeholder="${name}"/>

                    <maya:character id="txtRazao" label="${razao}" maxlength="255" required="true"
                                    valuex="${customerEdit.razaoSocial}" placeholder="${razao}"/>

                    <maya:character id="txtCpf" label="${cpf}" maxlength="255" required="true"
                                    valuex="${customerEdit.cpfCnpj}" placeholder="${cpf}"/>

                    <maya:character id="txtCountry" label="${country}" maxlength="255" required="true"
                                    valuex="${customerEdit.country}" placeholder="${country}"/>

                    <maya:character id="txtState" label="${state}" maxlength="255" required="true"
                                    valuex="${customerEdit.state}" placeholder="${state}"/>

                    <maya:character id="txtCity" label="${city}" maxlength="255" required="true"
                                    valuex="${customerEdit.city}" placeholder="${city}"/>

                    <maya:character id="txtAddress" label="${address}" maxlength="255" required="true"
                                    valuex="${customerEdit.address}" placeholder="${address}"/>

            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" name="action" value="${action}">Save changes</button>
                <button type="button" class="btn btn-danger" onclick="reloadModal()" data-dismiss="modal">Close</button>
            </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mask.min.js"></script>
<script type="text/javascript">
    var edit = document.querySelector("#edit");

    function reloadModal() {
        if (edit.value == "true") {
            window.history.back();
        }
    }

    $("#txtCpf").keydown(function () {
        try {
            $("#txtCpf").unmask();
        } catch (e) {
        }
        var tamanho = $("#txtCpf").val().length;
        if (tamanho < 11) {
            $("#txtCpf").mask("999.999.999-99");
        } else if (tamanho >= 11) {
            $("#txtCpf").mask("99.999.999/9999-99");
        }
        var elem = this;
        setTimeout(function () {
            // mudo a posição do seletor
            elem.selectionStart = elem.selectionEnd = 10000;
        }, 0);
        var currentValue = $(this).val();
        $(this).val('');
        $(this).val(currentValue);
    });
</script>

