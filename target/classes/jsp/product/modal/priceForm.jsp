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
<link href="/maya/js/autocompletepi/styles.css" rel="stylesheet"/>
<div class="modal fade bd-example-modal-md" tabindex="-1" id="priceForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${title} Preco</h5>
                <button type="button" class="close" data-dismiss="modal" onclick="reloadModal()"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="Price">
                    <maya:hidden id="edit" valuex="${edit}"/>
                    <fmt:message key='maya.table.price' var="price"/>
                    <fmt:message key='maya.table.price.var' var="price_var"/>
                    <fmt:message key='maya.table.price.atac' var="price_atac"/>
                    <fmt:message key='maya.table.price.product' var="product"/>
                    <fmt:message key='maya.table.price.collection' var="collection"/>

                    <maya:hidden id="txtId" valuex="${priceEdit.id}"/>

                    <maya:double id="txtPrice" label="${price}" maxlength="255" required="true"
                                 valuex="${priceEdit.price}" placeholder="${price}"/>

                    <maya:double id="txtPriceVar" label="${price_var}" maxlength="255" required="true"
                                 valuex="${priceEdit.priceVar}" placeholder="${price_var}"/>

                    <maya:double id="txtPriceAtac" label="${price_atac}" maxlength="255" required="true"
                                 valuex="${priceEdit.priceAtc}" placeholder="${price_atac}"/>

                    <maya:character id="txtProduct_autocomplete" placeholder="${product}" label="${product}"
                                    maxlength="255"
                                    required="false"
                                    valuex=""/>

                    <maya:hidden id="txtProduct" valuex="${priceEdit.product.id}"/>

                    <maya:character id="txtCollection_autocomplete" placeholder="${collection}"
                                    label="${collection}" maxlength="255"
                                    required="false"
                                    valuex=""/>

                    <maya:hidden id="txtCollection" valuex="${priceEdit.collection.id}"/>


            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" name="action" value="${action}">Save changes</button>
                <button type="button" class="btn btn-danger" onclick="reloadModal()" data-dismiss="modal">Close</button>
            </div>
            </form>
        </div>
    </div>
</div>
<c:import url="/jsp/product/ajax/collection_list.jsp"/>
<c:import url="/jsp/product/ajax/product_list.jsp"/>
<script src="/maya/js/jquery.min.js"></script>
<script src="/maya/js/jquery.mask.min.js"></script>
<script type="text/javascript" src="/maya/js/autocompletepi/jquery.autocomplete.js"></script>
<script type="text/javascript">
    var edit = document.querySelector("#edit");

    function reloadModal() {
        if (edit.value == "true") {
            window.location.href = "/maya/Price";
        }
    }

    var listCollectionArray = $.map(listCollection, function (value, key) {
        return {value: value, data: key};
    });
    console.log(listCollectionArray);
    $('#txtCollection_autocomplete').devbridgeAutocomplete({
        lookup: listCollectionArray,
        autoSelectFirst: true,
        onSelect: function (value) {
            $('#txtCollection').val(value.data);
        }
    });

    var listProductArray = $.map(listProduct, function (value, key) {
        return {value: value, data: key};
    });
    $('#txtProduct_autocomplete').devbridgeAutocomplete({
        lookup: listProductArray,
        autoSelectFirst: true,
        onSelect: function (value) {
            $('#txtProduct').val(value.data);
        }
    });
</script>

