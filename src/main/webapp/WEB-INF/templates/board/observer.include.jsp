<form:form action="" method="post" modelAttribute="boardObserverItem">

    <div class="form-row mb-3">
        <div class="col-md-9">
            <label for="fkObserverOid"><fmt:message key="board-observer"/></label>
            <div class="row">
                <div class="col-md-2">
                    <form:input type="text" path="fkObserverOid" id="fkObserverOid" readonly="true"
                                cssClass="form-control" cssErrorClass="form-control is-invalid" />
                    <form:errors path="fkObserverOid" cssClass="invalid-feedback"/>
                </div>
                <div class="col-md-8 pl-0">
                    <form:input type="text" path="fkObserverOidTitle" id="fkObserverOidTitle" readonly="true"
                                cssClass="form-control" cssErrorClass="form-control is-invalid" />
                </div>
                <div class="col-md-1">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-codeholder="fkObserverOid" data-titleholder="fkObserverOidTitle"
                            data-target="#pmModalScrollable">
                        <fmt:message key="select" />
                    </button>
                </div>
            </div>
        </div>
    </div>
<%--------------------------------------------------------------------------------------------------------------------%>
    <div class="form-row mb-3">
        <div class="col-md-2">
            <label for="lu_membership_type"><fmt:message key="lu_membership_type"/></label>
            <form:select path="luMembershipType" id="lu_membership_type" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${boardMembershipItems}" itemLabel="value" itemValue="key"/>
            <form:errors path="luMembershipType" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="lu_how_to_elect"><fmt:message key="lu_how_to_elect"/></label>
            <form:select path="luHowToElect" id="lu_how_to_elect" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${boardElectionTypeItems}" itemLabel="value" itemValue="key"/>
            <form:errors path="luHowToElect" cssClass="invalid-feedback"/>
        </div>
    </div>

<%--------------------------------------------------------------------------------------------------------------------%>
    <div class="form-row mb-3">
        <div class="col-md-3">
            <label for="voting_date"><fmt:message key="voting_date"/></label>
            <form:input path="votingDate" id="voting_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="votingDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="statute_letter_no"><fmt:message key="statute_letter_no"/></label>
            <form:input path="statuteLetterNo" id="statute_letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="statuteLetterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="statute_letter_date"><fmt:message key="statute_letter_date"/></label>
            <form:input path="statuteLetterDate" id="statute_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="statuteLetterDate" cssClass="invalid-feedback"/>
        </div>
    </div>
<%--------------------------------------------------------------------------------------------------------------------%>
    <div class="form-row mb-3">
        <div class="col-md-3">
            <label for="communique_issuance_date"><fmt:message key="communique_issuance_date"/></label>
            <form:input path="communiqueIssuanceDate" id="communique_issuance_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="communiqueIssuanceDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="observation_start_date"><fmt:message key="observation_start_date"/></label>
            <form:input path="observationStartDate" id="observation_start_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="observationStartDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="observation_end_date"><fmt:message key="observation_end_date"/></label>
            <form:input path="observationEndDate" id="observation_end_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="observationEndDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-1 mt27">
            <button class="btn btn-primary" type="button" id="add-observer"
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
            <th width="50">#</th>
            <th><fmt:message key="board-observer"/></th>
            <th><fmt:message key="lu_membership_type"/></th>
            <th><fmt:message key="lu_how_to_elect"/></th>
            <th><fmt:message key="voting_date"/></th>
            <th><fmt:message key="statute_letter_no"/></th>
            <th><fmt:message key="statute_letter_date"/></th>
            <th><fmt:message key="communique_issuance_date"/></th>
            <th><fmt:message key="observation_start_date"/></th>
            <th><fmt:message key="observation_end_date"/></th>
            <th width="30"></th>
        </tr>
        </thead>
        <tbody id="dynamic-observer-data-table">
        </tbody>
    </table>
</div>
<%@ include file="../parliament-member/ajax.pm-index.jsp" %>
<%@ include file="../parliament-member/ajax.pm-js.jsp" %>


<script type="text/javascript">
    class BoardObserver {
        constructor(boardPeriodOid, tableHolderId) {
            this.url='/board-period-rest/' + boardPeriodOid + '/observer';
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
                    '<td>' + val.fkObserverOidTitle + '</td>' +
                    '<td>' + val.luMembershipTypeTitle + '</td>' +
                    '<td>' + val.luHowToElectTitle + '</td>' +
                    '<td>' + val.votingDate.jalaliDate + '</td>' +
                    '<td>' + val.statuteLetterNo + '</td>' +
                    '<td>' + val.statuteLetterDate.jalaliDate + '</td>' +
                    '<td>' + val.communiqueIssuanceDate.jalaliDate + '</td>' +
                    '<td>' + val.observationStartDate.jalaliDate + '</td>' +
                    '<td>' + val.observationEndDate.jalaliDate + '</td>' +
                    '<td><a href="#' + val.oid + '" onclick="return confirm(\'<fmt:message key="are_you_sure" />\')" ' +
                    'class="remove-observer"><i class="fas ' +
                    'fa-minus-square m-1"></i></a></td>' +
                    '</tr>');
            });


        }
    }

    $(document).ready(function () {
        let bs = new BoardObserver(${boardPeriodItem.oid}, 'dynamic-observer-data-table');
        bs.fetchData();
        $(document).on('click', 'a.remove-observer', function (e) {bs.remove($(this).attr('href').substr(1));});
        $(document).on('click', '#add-observer', function (e) {bs.add({
            'fkObserverOid': $('#fkObserverOid').val(),
            'luMembershipType': $('#lu_membership_type').val(),
            'luHowToElect': $('#lu_how_to_elect').val(),
            'votingDate': $('#voting_date').val(),
            'statuteLetterNo': $('#statute_letter_no').val(),
            'statuteLetterDate': $('#statute_letter_date').val(),
            'communiqueIssuanceDate': $('#communique_issuance_date').val(),
            'observationStartDate': $('#observation_start_date').val(),
            'observationEndDate': $('#observation_end_date').val(),
        });});
    });

</script>