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
<div class="modal fade bd-example-modal-md" tabindex="-1" id="stockForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${title} Stock</h5>
                <button type="button" class="close" data-dismiss="modal" onclick="reloadModal()"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="Stock">
                    <maya:hidden id="edit" valuex="${edit}"/>
                    <fmt:message key='maya.table.stock.amount' var="amount"/>
                    <fmt:message key='maya.table.stock.type' var="type"/>
                    <fmt:message key='maya.table.stock.customer' var="customer"/>
                    <fmt:message key='maya.table.stock.price' var="price"/>

                    <maya:hidden id="txtId" valuex="${stockEdit.id}"/>

                    <maya:character id="txtCustomer_autocomplete" placeholder="${customer}" label="${customer}"
                                    maxlength="255" required="false" valuex="${stockEdit.customer.name}"/>

                    <maya:hidden id="txtCustomer" valuex="${stockEdit.customer.id}"/>

                    <c:choose>
                    <c:when test="${stockEdit.type == 'Normal'}">
                        <c:set var="priceAt"
                               value="${stockEdit.price.product.name} / ${stockEditstockEdit.price.price}"/>
                    </c:when>
                    <c:when test="${stockEdit.type == 'Varejo'}">
                        <c:set var="priceAt"
                               value="${stockEdit.price.product.name} / ${stockEdit.price.priceVar}"/>
                    </c:when>
                    <c:when test="${stockEdit.type == 'Atacado'}">
                        <c:set var="priceAt"
                               value="${stockEdit.price.product.name} / ${stockEdit.price.priceAtc}"/>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                    </c:choose>

                    <maya:character id="txtPrice_autocomplete" placeholder="${price}"
                                    label="${price}" maxlength="255" required="false" valuex="${priceAt}"/>

                    <maya:hidden id="txtPrice" valuex="${stockEdit.price.id}"/>

                    <maya:double id="txtAmount" label="${amount}" maxlength="255" required="true"
                                 valuex="${stockEdit.amount}" placeholder="${amount}"/>

                    <maya:character id="txtType_autocomplete" label="${type}" maxlength="255" required="true"
                                    valuex="${stockEdit.type}" placeholder="${type}"/>

                    <maya:hidden id="txtType" valuex="${stockEdit.type}"/>

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
<c:import url="/WEB-INF/jsp/product/ajax/price_list.jsp"/>
<c:import url="/WEB-INF/jsp/product/ajax/type_list.jsp"/>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.mask.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autocompletepi/jquery.autocomplete.js"></script>
<script type="text/javascript">
    var edit = document.querySelector("#edit");

    function reloadModal() {
        if (edit.value == "true") {
            window.location.href = "/maya/Stock";
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

    var listPriceArray = $.map(listPrice, function (value, key) {
        return {value: value, data: key};
    });
    $('#txtPrice_autocomplete').devbridgeAutocomplete({
        lookup: listPriceArray,
        autoSelectFirst: true,
        onSelect: function (value) {
            $('#txtPrice').val(value.data);
        }
    });

    var listTypeArray = $.map(listType, function (value, key) {
        return {value: value, data: key};
    });
    $('#txtType_autocomplete').devbridgeAutocomplete({
        lookup: listTypeArray,
        autoSelectFirst: true,
        onSelect: function (value) {
            $('#txtType').val(value.data);
        }
    });
</script>

