<form:form action="" method="post" modelAttribute="boardExpertItem">

    <div class="form-row mb-3">
        <div class="col-md-6">
            <label for="fkUserOid"><fmt:message key="board-expert"/></label>
            <div class="row">
                <div class="col-md-2">
                    <form:input type="text" path="fkUserOid" id="fkUserOid" readonly="true"
                                cssClass="form-control" cssErrorClass="form-control is-invalid" />
                    <form:errors path="fkUserOid" cssClass="invalid-feedback"/>
                </div>
                <div class="col-md-7 pl-0">
                    <form:input type="text" path="fkUserOidTitle" id="fkUserOidTitle" readonly="true"
                                cssClass="form-control" cssErrorClass="form-control is-invalid" />
                </div>
                <div class="col-md-1">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-codeholder="fkUserOid" data-titleholder="fkUserOidTitle"
                            data-target="#userModalScrollable">
                        <fmt:message key="select" />
                    </button>
                </div>
            </div>
        </div>
        <div class="col-md-2">
            <label for="start_date"><fmt:message key="start_date"/></label>
            <form:input path="startDate" id="start_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="startDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="end_date"><fmt:message key="end_date"/></label>
            <form:input path="endDate" id="end_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="endDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-1 mt27">
            <button class="btn btn-primary" type="button" id="add-expert"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
        </div>
    </div>
<%--------------------------------------------------------------------------------------------------------------------%>
    <input type="hidden" name="fkBoardOid" value="${boardPeriodItem.oid}">
</form:form>


<div class="table-responsive">
    <table class="table table-striped table-sm table-hover table-sortable">
        <thead>
        <tr>
            <th width="100">#</th>
            <th><fmt:message key="board-expert"/></th>
            <th><fmt:message key="start_date"/></th>
            <th><fmt:message key="end_date"/></th>
            <th width="150"></th>
        </tr>
        </thead>
        <tbody id="dynamic-expert-data-table">
        </tbody>
    </table>
</div>
<%@ include file="../security/ajax.user-index.jsp" %>
<%@ include file="../security/ajax.user-js.jsp" %>


<script type="text/javascript">
    class BoardExpert {
        constructor(boardPeriodOid, tableHolderId) {
            this.url='/board-period-rest/' + boardPeriodOid + '/expert';
            this.boardPeriodOid = boardPeriodOid;
            this.tableHolderId = tableHolderId;
        }

        add(obj) {
            let self = this;
            obj.fkBoardPeriodOid = self.boardPeriodOid;
            $.ajax({
                type: "POST",
                url: self.url + '/add',
                data: JSON.stringify(obj),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(data){self.fetchData();},
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
                    '<td>' + val.fkUserOidTitle + '</td>' +
                    '<td>' + val.startDate.jalaliDate + '</td>' +
                    '<td>' + val.endDate.jalaliDate + '</td>' +
                    '<td><a href="#' + val.oid + '" onclick="return confirm(\'<fmt:message key="are_you_sure" />\')" ' +
                    'class="remove-expert"><i class="fas ' +
                    'fa-minus-square m-1"></i></a></td>' +
                    '</tr>');
            });


        }
    }
    $(document).ready(function () {
        let bp = new BoardExpert(${boardPeriodItem.oid}, 'dynamic-expert-data-table');
        bp.fetchData();
        $(document).on('click', 'a.remove-expert', function (e) {bp.remove($(this).attr('href').substr(1));});
        $(document).on('click', '#add-expert', function (e) {bp.add({
            'fkUserOid': $('#fkUserOid').val(),
            'startDate': $('#start_date').val(),
            'endDate': $('#end_date').val(),
        });});
    });

</script>