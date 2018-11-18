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
<div class="modal fade bd-example-modal-md" tabindex="-1" id="productForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${title} Colecao</h5>
                <button type="button" class="close" data-dismiss="modal" onclick="reloadModal()"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="Collection">
                    <maya:hidden id="edit" valuex="${edit}"/>
                    <fmt:message key='maya.table.collection.name' var="name"/>

                    <maya:hidden id="txtId" valuex="${collectionEdit.id}"/>

                    <maya:character id="txtName" label="${name}" maxlength="255" required="true"
                                    valuex="${collectionEdit.name}" placeholder="${name}"/>

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
</script>

