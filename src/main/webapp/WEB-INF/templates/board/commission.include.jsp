<form:form action="" method="post" modelAttribute="boardCommissionItem">

    <div class="form-row mb-3">
        <div class="col-md-4">
            <label for="lu_commission"><fmt:message key="lu_commission"/></label>
            <form:select path="luCommission" id="lu_commission" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${commissionItems}" itemLabel="value" itemValue="key"/>
            <form:errors path="luCommission" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2 mt27">
            <button class="btn btn-primary" type="button" id="add-commission"><fmt:message key="add"/></button>
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
            <th><fmt:message key="lu_commission"/></th>
            <th width="150"></th>
        </tr>
        </thead>
        <tbody id="data-table-commission">
        </tbody>
    </table>
</div>
<script type="text/javascript">
    class BoardCommission {
        constructor(boardPeriodOid, tableHolderId) {
            this.url='/board-period-rest/' + boardPeriodOid + '/commission';
            this.boardPeriodOid = boardPeriodOid;
            this.tableHolderId = tableHolderId;
        }

        add(luCommission) {
            let self = this;
            $.ajax({
                type: "POST",
                url: self.url + '/add',
                data: JSON.stringify({"fkBoardPeriodOid": self.boardPeriodOid, "luCommission": luCommission}),
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
                    '<td>' + val.luCommissionTitle + '</td>' +
                    '<td><a href="#' + val.oid + '" onclick="return confirm(\'<fmt:message key="are_you_sure" />\')" ' +
                        'class="remove-commission"><i class="fas ' +
                        'fa-minus-square m-1"></i></a></td>' +
                    '</tr>');
            });


        }
    }
    $(document).ready(function () {
        let bc = new BoardCommission(${boardPeriodItem.oid}, 'data-table-commission');
        bc.fetchData();
        $(document).on('click', 'a.remove-commission', function (e) {bc.remove($(this).attr('href').substr(1));});
        $(document).on('click', '#add-commission', function (e) {bc.add($('#lu_commission').val());});
    });

</script>