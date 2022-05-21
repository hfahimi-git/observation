<form:form action="" id="observer-report-form" method="post" enctype="multipart/form-data"
           modelAttribute="sessionObserverReportItem">

    <div class="form-row mb-3">
        <div class="col-md-2">
            <label for="fk_board_observer_oid"><fmt:message key="fk_board_observer_oid"/></label>
            <form:select path="fkBoardObserverOid" id="fk_board_observer_oid" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${observerItems}" itemLabel="fkObserverOidTitle" itemValue="oid" />
            <form:errors path="fkBoardObserverOid" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="letter_no"><fmt:message key="letter_no"/></label>
            <form:input path="letterNo" id="letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="letterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="observer_letter_date"><fmt:message key="letter_date"/></label>
            <form:input path="letterDate" id="observer_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="letterDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="lu_abs_pres_status"><fmt:message key="lu_abs_pres_status"/></label>
            <form:select path="luAbsPresStatus" id="lu_abs_pres_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${absPresItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luAbsPresStatus" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="lu_hold_status"><fmt:message key="lu_hold_status"/></label>
            <form:select path="luHoldStatus" id="lu_hold_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${yesNoItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luHoldStatus" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-3">
            <label for="lu_invitation_status"><fmt:message key="lu_invitation_status"/></label>
            <form:select path="luInvitationStatus" id="lu_invitation_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${yesNoItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luInvitationStatus" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="lu_coordination_status"><fmt:message key="lu_coordination_status"/></label>
            <form:select path="luCoordinationStatus" id="lu_coordination_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${yesNoItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luCoordinationStatus" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="lu_member_abs_pres_status"><fmt:message key="lu_member_abs_pres_status"/></label>
            <form:select path="luMemberAbsPresStatus" id="lu_member_abs_pres_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${memberAbsPresItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luMemberAbsPresStatus" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6">
            <label for="topic"><fmt:message key="deputy-justification-report.topic"/></label>
            <form:textarea path="topic" id="topic" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="topic" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-6">
            <label for="suggestion"><fmt:message key="deputy-justification-report.suggestion"/></label>
            <form:textarea path="suggestion" id="suggestion" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="suggestion" cssClass="invalid-feedback"/>
        </div>
    </div>

    <div class="form-row mb-3">
        <div class="col-md-6">
            <label for="contrary"><fmt:message key="deputy-justification-report.contrary"/></label>
            <form:textarea path="contrary" id="contrary" cssClass="form-control"
                           cssErrorClass="form-control is-invalid" />
            <form:errors path="contrary" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-6">
            <label for="description"><fmt:message key="description"/></label>
            <form:textarea path="description" id="description" cssClass="form-control"
                           cssErrorClass="form-control is-invalid" />
            <form:errors path="description" cssClass="invalid-feedback"/>
        </div>
    </div>


    <div class="form-row mb-3">
        <div class="col-md-6">
            <label for="file"><fmt:message key="filename"/></label>
            <form:input type="file" path="file" id="file" cssClass="form-control"
                           cssErrorClass="form-control is-invalid" />
            <form:errors path="file" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-6 hidden pt-4" id="file-holder">
            <a class="btn btn-info btn-sm" href="#" target="_blank"><fmt:message key="download"/></a>
            <a class="btn btn-danger btn-sm" onclick="return confirm('<fmt:message key="are_you_sure"/>')"
               id="remove-observer-report-file" href="#"><fmt:message key="file.remove"/></a>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6 mt27">
            <button class="btn btn-primary" type="button" id="add-observer-report"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
            <button class="btn btn-primary hidden" type="button" id="edit-observer-report"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="edit"/></button>
            <button class="btn btn-danger hidden" type="button" id="remove-observer-report"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="remove"/></button>
            <button class="btn btn-secondary hidden" type="button" id="reset-observer-report"><fmt:message key="cancel"/></button>
        </div>
    </div>

<%--------------------------------------------------------------------------------------------------------------------%>
    <input type="hidden" name="fkSessionOid" id="fk_session_oid" value="${sessionItem.oid}">
    <input type="hidden" name="oid" id="oid" />
</form:form>

<div class="table-responsive">
    <table class="table table-striped table-sm table-hover table-sortable">
        <thead>
        <tr>
            <th width="60">#</th>
            <th width="200"><fmt:message key="board-observer"/></th>
            <th><fmt:message key="letter_no"/></th>
            <th><fmt:message key="letter_date"/></th>
            <th><fmt:message key="lu_abs_pres_status"/></th>
            <th width="60"></th>
        </tr>
        </thead>
        <tbody id="dynamic-observer-report-data-table">
        </tbody>
    </table>
</div>


<script type="text/javascript">
    class SessionObserver {
        constructor(sessionOid, formId, tableHolderId) {
            this.url='/session-observer-report-rest/' + sessionOid;
            this.sessionOid = sessionOid;
            this.formId = formId;
            this.tableHolderId = tableHolderId;
        }

        add() {
            let self = this;
            let formData = new FormData(document.querySelector(self.formId));
            $.ajax({
                type: "POST",
                url: self.url + '/add',
                data: formData,
                contentType: false,
                cache: false,
                processData:false,
                charset: 'utf-8',
                dataType: 'json',
                success: function(data){self.fetchAll();},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        edit() {
            let self = this;
            let formData = new FormData(document.querySelector(self.formId));
            $.ajax({
                type: "POST",
                url: self.url + '/edit',
                data: formData,
                contentType: false,
                cache: false,
                processData:false,
                charset: 'utf-8',
                dataType: 'json',
                success: function(data){self.fetchAll();},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        remove(oid){
            let self = this;
            $.post(self.url + '/remove/' + oid).done(function () {self.fetchAll();});
        }

        removeFile(oid){
            let self = this;
            $.post(self.url + '/remove-file/' + oid).done(function () {$(self.formId + ' #file-holder').addClass('hidden');});
        }

        fetchAll(){
            let self = this;
            $.get(self.url, function (data) {self.resetForm(); self.fillTable(data)}, 'json');
        }

        fetch(oid){
            let self = this;
            $.get(self.url + '/show/' + oid, function (data) {self.fillForm(data)}, 'json');
        }

        fillTable(data) {
            let self = this;
            let jqId = this.tableHolderId;
            $(jqId).empty();
            let $body = $(jqId);
            $.each(data, function (idx, val) {
                $body.append('<tr>' +
                    '<td>' + val.oid + '</td>' +
                    '<td>' + val.fkBoardObserverOidTitle + '</td>' +
                    '<td>' + val.letterNo + '</td>' +
                    '<td>' + (val.letterDate != null? val.letterDate.jalaliDate: '') + '</td>' +
                    '<td>' + val.luAbsPresStatusTitle + '</td>' +
                    '<td><a href="#' + val.oid + '" class="edit-observer-report"><i class="fas fa-pen-square m-1"></i></a></td>' +
                    '</tr>');
            });
        }
        fillForm(data) {
            let self = this;
            if(data == null) return;
            $(self.formId + ' #oid').val(data.oid);
            $(self.formId + ' #fk_board_observer_oid').val(data.fkBoardObserverOid);
            $(self.formId + ' #letter_no').val(data.letterNo);
            if(data.letterDate != null) $(self.formId + ' #observer_letter_date').val(data.letterDate.jalaliDate);
            $(self.formId + ' #lu_abs_pres_status').val(data.luAbsPresStatus);
            $(self.formId + ' #lu_invitation_status').val(data.luInvitationStatus);
            $(self.formId + ' #lu_coordination_status').val(data.luCoordinationStatus);
            $(self.formId + ' #lu_hold_status').val(data.luHoldStatus);
            $(self.formId + ' #lu_member_abs_pres_status').val(data.luMemberAbsPresStatus);
            $(self.formId + ' #topic').val(data.topic);
            $(self.formId + ' #suggestion').val(data.suggestion);
            $(self.formId + ' #contrary').val(data.contrary);
            $(self.formId + ' #description').val(data.description);
            $(self.formId + ' #filename').val(data.filename);
            $(self.formId + ' #fk_session_oid').val(data.fkSessionOid);
            if(data.filename) {
                $(self.formId + ' #file-holder > a:first').prop('href', '${CONTEXT}/uploads/' + data.filename);
                $(self.formId + ' #file-holder').removeClass('hidden');
            }

            $(self.formId + ' #edit-observer-report').removeClass('hidden');
            $(self.formId + ' #remove-observer-report').removeClass('hidden');
            $(self.formId + ' #reset-observer-report').removeClass('hidden');
            $(self.formId + ' #add-observer-report').addClass('hidden');
        }

        resetForm() {
            let self = this;
            $(self.formId + ' #edit-observer-report').addClass('hidden');
            $(self.formId + ' #remove-observer-report').addClass('hidden');
            $(self.formId + ' #reset-observer-report').addClass('hidden');
            $(self.formId + ' #add-observer-report').removeClass('hidden');
            $(self.formId + ' #file-holder').addClass('hidden');
            $(self.formId + ' input[type="text"],[type="file"]').val('');
            $(self.formId + ' textarea').val('');
            // $(self.formId + ' select').val('');
        }

    }
    $(document).ready(function () {
        let sessionObserver = new SessionObserver(${sessionItem.oid}, '#observer-report-form', '#dynamic-observer-report-data-table');
        sessionObserver.fetchAll();
        $(document).on('click', '#remove-observer-report-file', function (e) {sessionObserver.removeFile($('#observer-report-form #oid').val()); return false});
        $(document).on('click', '#remove-observer-report', function (e) {sessionObserver.remove($('#observer-report-form #oid').val());});
        $(document).on('click', '#edit-observer-report', function (e) {sessionObserver.edit()});
        $(document).on('click', '.edit-observer-report', function (e) {sessionObserver.fetch($(this).attr('href').substr(1))});
        $(document).on('click', '#add-observer-report', function (e) {sessionObserver.add()});
        $(document).on('click', '#reset-observer-report', function (e) {sessionObserver.resetForm()});
    });

</script>