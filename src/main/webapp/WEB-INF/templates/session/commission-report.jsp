<form:form action="" id="commission-report-form" method="post" enctype="multipart/form-data"
           modelAttribute="sessionCommissionReportItem">

    <div class="form-row mb-3">
        <div class="col-md-2">
            <label for="fk_observer_report_oid"><fmt:message key="fk_observer_report_oid"/></label>
            <form:select id="fk_observer_report_oid" name="fkObserverReportOid" path="fkObserverReportOid"
                         cssClass="form-control custom-select" cssErrorClass="form-control custom-select is-invalid">
                <c:forEach var="report" items="${observerReportItems}">
                    <form:option value="${report.oid}"><c:out value="#${report.oid} - ${report.fkBoardObserverOidTitle}"/></form:option>
                </c:forEach>
            </form:select>

<%--
            <form:select path="fkObserverReportOid" id="fk_observer_report_oid" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${observerReportItems}" itemLabel="fkBoardObserverOidTitle" itemValue="oid" />
--%>
            <form:errors path="fkObserverReportOid" cssClass="invalid-feedback"/>
        </div>
<%--
        <div class="col-md-2">
            <label for="fk_board_observer_oid"><fmt:message key="fk_board_observer_oid"/></label>
            <form:select path="fkBoardObserverOid" id="fk_board_observer_oid" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${observerItems}" itemLabel="fkObserverOidTitle" itemValue="oid" />
            <form:errors path="fkBoardObserverOid" cssClass="invalid-feedback"/>
        </div>
--%>

        <div class="col-md-2">
            <label for="fk_board_commission_oid"><fmt:message key="lu_commission"/></label>
            <form:select path="fkBoardCommissionOid" id="fk_board_commission_oid" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${commissionItems}" itemLabel="luCommissionTitle" itemValue="oid" />
            <form:errors path="fkBoardCommissionOid" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="lu_grade_status"><fmt:message key="lu_grade_status"/></label>
            <form:select path="luGradeStatus" id="lu_grade_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${gradeItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luGradeStatus" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-4">
            <label for="to_cmsn_letter_no"><fmt:message key="to_cmsn_letter_no"/></label>
            <form:input path="toCmsnLetterNo" id="to_cmsn_letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="toCmsnLetterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-4">
            <label for="to_cmsn_letter_date"><fmt:message key="to_cmsn_letter_date"/></label>
            <form:input path="toCmsnLetterDate" id="to_cmsn_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="toCmsnLetterDate" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-4">
            <label for="to_dpt_letter_no"><fmt:message key="to_dpt_letter_no"/></label>
            <form:input path="toDptLetterNo" id="to_dpt_letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="toDptLetterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-4">
            <label for="to_cmsn_letter_date"><fmt:message key="to_dpt_letter_date"/></label>
            <form:input path="toDptLetterDate" id="to_dpt_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="toDptLetterDate" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-12">
            <label for="evaluation"><fmt:message key="evaluation"/></label>
            <form:textarea path="evaluation" id="evaluation" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="evaluation" cssClass="invalid-feedback"/>
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
               id="remove-commission-file" href="#"><fmt:message key="file.remove"/></a>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6 mt27">
            <button class="btn btn-primary" type="button" id="add-commission"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
            <button class="btn btn-primary hidden" type="button" id="edit-commission"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="edit"/></button>
            <button class="btn btn-danger hidden" type="button" id="remove-commission"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="remove"/></button>
            <button class="btn btn-secondary hidden" type="button" id="reset-commission"><fmt:message key="cancel"/></button>
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
            <th width="200"><fmt:message key="fk_board_observer_oid"/></th>
            <th><fmt:message key="lu_commission"/></th>
            <th><fmt:message key="lu_grade_status"/></th>
            <th width="60"></th>
        </tr>
        </thead>
        <tbody id="dynamic-commission-report-data-table">
        </tbody>
    </table>
</div>

<script type="text/javascript">
    class SessionCommissionReport {
        constructor(sessionOid, formId, tableHolderId) {
            this.url='/session-commission-report-rest/' + sessionOid;
            this.sessionOid = sessionOid;
            this.formId = formId;
            this.tableHolderId = tableHolderId;
        }

        add() {
            let self = this;
            let formData = new FormData(document.querySelector(self.formId));
            formData.delete("oid");
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
                    '<td>' + val.fkObserverReportOidTitle + '</td>' +
                    '<td>' + val.fkBoardCommissionOidTitle + '</td>' +
                    '<td>' + val.luGradeStatusTitle + '</td>' +
                    '<td><a href="#' + val.oid + '" class="edit-commission"><i class="fas fa-pen-square m-1"></i></a></td>' +
                    '</tr>');
            });
        }

        fillForm(data) {
            let self = this;
            if(data == null) return;
            $(self.formId + ' #oid').val(data.oid);
            $(self.formId + ' #fk_observer_report_oid').val(data.fkObserverReportOid);
            $(self.formId + ' #fk_board_commission_oid').val(data.fkBoardCommissionOid);
            $(self.formId + ' #to_cmsn_letter_no').val(data.toCmsnLetterNo);
            if(data.toCmsnLetterDate != null) $(self.formId + ' #to_cmsn_letter_date').val(data.toCmsnLetterDate.jalaliDate);
            $(self.formId + ' #to_dpt_letter_no').val(data.toDptLetterNo);
            if(data.toDptLetterDate != null) $(self.formId + ' #to_dpt_letter_date').val(data.toDptLetterDate.jalaliDate);
            $(self.formId + ' #evaluation').val(data.evaluation);
            $(self.formId + ' #lu_grade_status').val(data.luGradeStatus);
            $(self.formId + ' #filename').val(data.filename);
            $(self.formId + ' #fk_session_oid').val(data.fkSessionOid);
            if(data.filename) {
                $(self.formId + ' #file-holder > a:first').prop('href', '${CONTEXT}/uploads/' + data.filename);
                $(self.formId + ' #file-holder').removeClass('hidden');
            }

            $(self.formId + ' #edit-commission').removeClass('hidden');
            $(self.formId + ' #remove-commission').removeClass('hidden');
            $(self.formId + ' #reset-commission').removeClass('hidden');
            $(self.formId + ' #add-commission').addClass('hidden');
        }
        resetForm() {
            let self = this;
            $(self.formId + ' #edit-commission').addClass('hidden');
            $(self.formId + ' #remove-commission').addClass('hidden');
            $(self.formId + ' #reset-commission').addClass('hidden');
            $(self.formId + ' #add-commission').removeClass('hidden');
            $(self.formId + ' #file-holder').addClass('hidden');
            $(self.formId + ' input[type="text"],[type="file"]').val('');
            $(self.formId + ' textarea').val('');
            // $(self.formId + ' select').val('-');
        }

    }
    $(document).ready(function () {
        let commission = new SessionCommissionReport(${sessionItem.oid}, '#commission-report-form',
            '#dynamic-commission-report-data-table');
        commission.fetchAll();
        $(document).on('click', '#remove-commission-file', function (e) {commission.removeFile($('#commission-report-form #oid').val()); return false});
        $(document).on('click', '#remove-commission', function (e) {commission.remove($('#commission-report-form #oid').val());});
        $(document).on('click', '#edit-commission', function (e) {commission.edit()});
        $(document).on('click', '.edit-commission', function (e) {commission.fetch($(this).attr('href').substr(1))});
        $(document).on('click', '#add-commission', function (e) {commission.add()});
        $(document).on('click', '#reset-commission', function (e) {commission.resetForm()});
    });

</script>