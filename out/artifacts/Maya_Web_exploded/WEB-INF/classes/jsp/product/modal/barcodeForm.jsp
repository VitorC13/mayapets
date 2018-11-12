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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link href="js/autocompletepi/styles.css" rel="stylesheet"/>
<div class="modal fade bd-example-modal-md" tabindex="-1" id="barcodeForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${title} Barcode</h5>
                <button type="button" class="close" data-dismiss="modal" onclick="reloadModal()"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="Barcode">
                    <maya:hidden id="barcodeStock" valuex="${barcodeStock}"/>
                    <fmt:message key='maya.barcode.stock' var="stock"/>
                    <fmt:message key='maya.barcode.barcode' var="barcode"/>
                    <maya:hidden id="arrayMultiple" valuex=""/>
                    <maya:hidden id="arrayMultipleIdBarcode" valuex=""/>

                    <c:if test="${barcodeStock == true}">
                        <c:forEach var="barcodeStk" items="${listBarcode}" begin="0"
                                   end="${listBarcodeSize}">
                            <maya:hidden id="txtStock" valuex="${barcodeStk.stock.id}"/>
                            <maya:hidden id="txtIdBarcode" valuex="${barcodeStk.id}"/>
                            <maya:character id="txtBarcode" placeholder="${barcode}" label="${barcode} do produto ${barcodeStk.stock.price.product.name}
                            da empresa ${barcodeStk.stock.customer.name}"
                                            maxlength="255" required="false" valuex="${barcodeStk.barcode}"/>
                        </c:forEach>
                    </c:if>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary" name="action" value="${action}">Save changes
                        </button>
                        <button type="button" class="btn btn-danger" onclick="reloadModal()" data-dismiss="modal">
                            Close
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="/maya/js/jquery.min.js"></script>
<script src="/maya/js/jquery.mask.min.js"></script>
<script type="text/javascript">

    var arrayMultiple = document.querySelector("#arrayMultiple");
    var arrayMultipleIdBarcode = document.querySelector("#arrayMultipleIdBarcode");
    var valores = [];
    var valoresId = [];

    $('input[type=text]#txtBarcode').on('change', function () {
        valores = $('input[type=text]#txtBarcode').get().map(el => el.value).join(',');
        valoresId = $('input[type=hidden]#txtIdBarcode').get().map(el => el.value).join(',');
        arrayMultiple.value = valores;
        arrayMultipleIdBarcode.value = valoresId;
    });

</script>

