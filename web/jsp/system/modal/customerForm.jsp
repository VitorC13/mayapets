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
<div class="modal fade bd-example-modal-md" tabindex="-1" id="customerForm" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Nova EMpresa</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="Customer">
                    <fmt:message key='modal.customer.name' var="name"/>
                    <fmt:message key='modal.customer.razao' var="razao"/>
                    <fmt:message key='modal.customer.cpfcnpj' var="cpf"/>
                    <fmt:message key='modal.customer.city' var="city"/>
                    <fmt:message key='modal.customer.address' var="address"/>
                    <fmt:message key='modal.customer.country' var="country"/>
                    <fmt:message key='modal.customer.state' var="state"/>

                    <maya:character id="txtName" label="${name}" maxlength="255" required="true"
                                    valuex="" placeholder="${name}"/>

                    <maya:character id="txtRazao" label="${razao}" maxlength="255" required="true"
                                    valuex="" placeholder="${razao}"/>

                    <maya:character id="txtCpf" label="${cpf}" maxlength="255" required="true"
                                    valuex="" placeholder="${cpf}"/>

                    <maya:character id="txtCountry" label="${country}" maxlength="255" required="true"
                                    valuex="" placeholder="${country}"/>

                    <maya:character id="txtState" label="${state}" maxlength="255" required="true"
                                    valuex="" placeholder="${state}"/>

                    <maya:character id="txtCity" label="${city}" maxlength="255" required="true"
                                    valuex="" placeholder="${city}"/>

                    <maya:character id="txtAddress" label="${address}" maxlength="255" required="true"
                                    valuex="" placeholder="${address}"/>

            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" name="action" value="new">Save changes</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>
            </form>
        </div>
    </div>
</div>

