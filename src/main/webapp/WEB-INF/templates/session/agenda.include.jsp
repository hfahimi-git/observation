<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form action="" method="post" modelAttribute="sessionAgendaItem" id="session-agenda-form">

    <div class="form-row mb-3">
        <div class="col-md-1">
            <label for="orderby"><fmt:message key="orderby"/></label>
            <form:input path="orderby" id="orderby" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="orderby" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-9">
            <label for="description"><fmt:message key="description"/></label>
            <form:textarea path="description" id="description" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="description" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-1 mt27">
            <button class="btn btn-primary" type="button" id="add-agenda"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
        </div>
        <input type="hidden" id="oid" />
    </div>
<%--------------------------------------------------------------------------------------------------------------------%>
    <input type="hidden" name="fkBoardOid" value="${sessionItem.oid}">
</form:form>


<div class="table-responsive">
    <table class="table table-striped table-sm table-hover table-sortable">
        <thead>
        <tr>
            <th width="100">#</th>
            <th><fmt:message key="orderby"/></th>
            <th><fmt:message key="description"/></th>
            <th width="60"></th>
        </tr>
        </thead>
        <tbody id="dynamic-agenda-data-table">
        </tbody>
    </table>
</div>

<script type="text/javascript">
    class SessionAgenda {
        constructor(sessionOid, tableHolderId) {
            this.url='/session-agenda-rest/' + sessionOid;
            this.sessionOid = sessionOid;
            this.tableHolderId = tableHolderId;
        }

        add(obj) {
            let self = this;
            obj.fkSessionOid = self.sessionOid;
            $.ajax({
                type: "POST",
                url: self.url + '/add',
                data: JSON.stringify(obj),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(data){self.fetchData();self.reset();},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        edit(obj) {
            let self = this;
            obj.fkSessionOid = self.sessionOid;
            $.ajax({
                type: "POST",
                url: self.url + '/edit',
                data: JSON.stringify(obj),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(data){self.fetchData(); self.reset();},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        reset() {
            $('#orderby').val('');
            $('#description').val('');
            $('.agenda-cancel-button').remove();
            $('.btn-primary').text('<fmt:message key="add"/>');
            $('.btn-primary').val('<fmt:message key="add"/>').prop('id', 'add-agenda');
        }

        show(oid) {
            let self = this;
            $.ajax({
                type: "GET",
                url: self.url + '/show/' + oid,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(data){self.fillEditForm(data)},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        remove(oid){
            let self = this;
            $.post(self.url + '/remove/' + oid).done(function () {self.fetchData();});
        }

        fetchData(){
            let self = this;
            $.get(self.url, function (data) {self.fillTable(data)}, 'json');
        }

        fillTable(data) {
            let self = this;
            let jqId = '#' + this.tableHolderId;
            $(jqId).empty();
            let $body = $(jqId);
            $.each(data, function (idx, val) {
                $body.append('<tr>' +
                    '<td>' + val.oid + '</td>' +
                    '<td>' + val.orderby + '</td>' +
                    '<td>' + val.description + '</td>' +
                    '<td><a href="#' + val.oid + '" class="edit-agenda"><i class="fas fa-pen-square m-1"></i></a> ' +
                    '<a href="#' + val.oid + '" onclick="return confirm(\'<fmt:message key="are_you_sure" />\')" ' +
                        'class="remove-agenda"><i class="fas fa-minus-square m-1"></i></a></td>' +
                    '</tr>');
            });

        }

        fillEditForm(data) {
            $('#orderby').val(data.orderby);
            $('#description').val(data.description);
            $('#oid').val(data.oid);
            $('.btn-primary').text('<fmt:message key="edit"/>');
            $('.btn-primary').val('<fmt:message key="edit"/>').prop('id', 'edit-agenda');
            $('.form-row').append('<div class="col-md-1 agenda-cancel-button mt27">' +
                '<button class="btn btn-secondary" type="button" id="reset-form"><fmt:message key="cancel"/></button></div>');
        }
    }
    $(document).ready(function () {
        let sa = new SessionAgenda(${sessionItem.oid}, 'dynamic-agenda-data-table');
        sa.fetchData();
        $(document).on('click', 'a.remove-agenda', function (e) {sa.remove($(this).attr('href').substr(1));});
        $(document).on('click', '#add-agenda', function (e) {sa.add({
                'orderby': $('#orderby').val(),
                'description': $('#description').val(),
            });
        });
        $(document).on('click', '#edit-agenda', function (e) {sa.edit({
                'oid': $('#session-agenda-form #oid').val(),
                'orderby': $('#orderby').val(),
                'description': $('#description').val(),
            });
        });
        $(document).on('click', 'a.edit-agenda', function (e) {
            sa.show($(this).attr('href').substr(1));
        });
        $(document).on('click', '#reset-form', function(e) {
            sa.reset();
        });
    });

</script>