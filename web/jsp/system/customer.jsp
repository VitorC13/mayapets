<%--
  Created by IntelliJ IDEA.
  User: vitor
  Date: 09/10/18
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<fmt:setLocale value="pt_BR"/>
<c:import url="/jsp/inc/head.jsp"/>

<fmt:message key='maya.table.customer.name' var="column_name"/>
<fmt:message key='maya.table.customer.razao' var="column_razao"/>
<fmt:message key='maya.table.customer.cpfcnpj' var="column_cnpj"/>
<fmt:message key='maya.table.customer.city' var="column_city"/>
<fmt:message key='maya.table.customer.address' var="column_address"/>
<fmt:message key='maya.table.customer.country' var="column_country"/>
<fmt:message key='maya.table.customer.state' var="column_state"/>

<div class="container-fluid">
    <c:import url="/jsp/inc/left_menu.jsp"/>

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">

        <button class="btn btn-primary" data-target="#customerForm" data-toggle="modal" data-backdrop="static"
                data-keyboard="false">
            <i class="fa fa-plus"></i>&nbsp&nbspNovo
        </button>

        <table id="report" class="stripe row-border order-column">
            <!--Table head-->
            <thead>
            <tr align="center" valign="middle">
                <th class="bg-dark text-white">${column_name}</th>
                <th class="bg-light text-black-50">${column_razao}</th>
                <th class="bg-light text-black-50">${column_cnpj}</th>
                <th class="bg-light text-black-50">${column_country}</th>
                <th class="bg-light text-black-50">${column_state}</th>
                <th class="bg-light text-black-50">${column_city}</th>
                <th class="bg-light text-black-50">${column_address}</th>
                <th class="bg-light text-black-50">Acoes</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="list" items="${listcustomer}" begin="0"
                       end="${listcustomerSize}">
                <tr>
                    <td align="right" valign="middle">${list.name}</td>
                    <td align="right" valign="middle">${list.razaoSocial}</td>
                    <td align="right" valign="middle">${list.cpfCnpj}</td>
                    <td align="right" valign="middle">${list.country}</td>
                    <td align="right" valign="middle">${list.state}</td>
                    <td align="right" valign="middle">${list.city}</td>
                    <td align="right" valign="middle">${list.address}</td>
                    <form action="Customer">
                        <td align="center" valign="middle">
                            <button class="btn btn-dark" disabled ><i class="fas fa-edit"></i></button>
                            <button class="btn btn-danger" name="action" value="delete_${list.id}"><i class="fas fa-user-times"></i></button>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tfoot>
        </table>

    </main>

</div>
<c:import url="/jsp/system/modal/customerForm.jsp"/>
<c:import url="/jsp/inc/bottom.jsp"/>
<script type="text/javascript">
    $('#report tfoot th').each(function () {
        var title = $(this).text();
        if ($(this).attr('pi-search') !== 'false') {
            $(this).html('<input type="hidden"  />');
        }
    });

    $(document).ready(function () {

        var table = $('#report').DataTable({
            responsive: true,
            "bPaginate": false,
            "lengthChange": false,
            fixedHeader: {
                header: true,
                footer: true
            },
            ordering: false,
            dom: 'Blfrtip',
            buttons: [

                {
                    text: '<i class="fa fa-file-pdf-o"></i> PDF',
                    title: 'Relatório ',
                    extend: 'pdfHtml5',
                    orientation: 'landscape',
                    pageSize: 'A4',
                    className: 'mt-2 mb-2 btn-sm'
                },
                {
                    text: ' <i class="fa fa-print fa-lg"></i> ',
                    title: 'Relatório ',
                    extend: 'print',
                    orientation: 'landscape',
                    pageSize: 'A4',
                    className: 'mt-2 mb-2 btn-sm'
                }
            ],
            language: {
                "sEmptyTable": "Nenhum registro encontrado",
                "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
                "sInfoFiltered": "(Filtrados de _MAX_ registros)",
                "sInfoPostFix": "",
                "sInfoThousands": ".",
                "sLengthMenu": "_MENU_ resultados por página",
                "sLoadingRecords": "Carregando...",
                "sProcessing": "Processando...",
                "sZeroRecords": "Nenhum registro encontrado",
                "sSearch": "&#x1F50D; Pesquisar",
                "oPaginate": {
                    "sNext": "Próximo",
                    "sPrevious": "Anterior",
                    "sFirst": "Primeiro",
                    "sLast": "Último"
                },
                "oAria": {
                    "sSortAscending": ": Ordenar colunas de forma ascendente",
                    "sSortDescending": ": Ordenar colunas de forma descendente"
                }
            }
        });
        table.columns().every(function () {
            var that = this;

            $('input', this.footer()).on('keyup change', function () {
                if (that.search() !== this.value) {
                    that
                        .search(this.value)
                        .draw();
                }
            });
        });

        $('input[type=button].toggle-vis').on('click', function (e) {
            e.preventDefault();
            var column = table.column($(this).attr('data-column'));
            if ($(this).attr('status') === '-') {
                $(this).attr('status', '+');
                $(this).removeClass('active');
            } else if ($(this).attr('status') === '+') {
                $(this).attr('status', '-');
                $(this).addClass('active');
            }
            column.visible(!column.visible());
        });
    });
</script>