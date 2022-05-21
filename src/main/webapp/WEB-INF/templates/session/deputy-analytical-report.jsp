<form:form action="" id="deputy-analytical-report-form" method="post" enctype="multipart/form-data"
           modelAttribute="sessionDeputyAnalyticalReportItem">

    <div class="form-row mb-3">
        <div class="col-md-3">
            <label for="fk_commission_report_oid"><fmt:message key="fk_commission_report_oid"/></label>
            <form:select id="fk_commission_report_oid" name="fkCommissionReportOid" path="fkCommissionReportOid"
                         cssClass="form-control custom-select" cssErrorClass="form-control custom-select is-invalid">
                <c:forEach var="report" items="${commissionReportItems}">
                    <form:option value="${report.oid}"><c:out value="#${report.oid} - ${report.fkBoardCommissionOidTitle}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="fkCommissionReportOid" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="analytical_lu_grade_status"><fmt:message key="lu_grade_status"/></label>
            <form:select path="luGradeStatus" id="analytical_lu_grade_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${gradeItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luGradeStatus" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-4">
            <label for="beneficiary_letter_no"><fmt:message key="beneficiary_letter_no"/></label>
            <form:input path="beneficiaryLetterNo" id="beneficiary_letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="beneficiaryLetterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-4">
            <label for="beneficiary_letter_date"><fmt:message key="beneficiary_letter_date"/></label>
            <form:input path="beneficiaryLetterDate" id="beneficiary_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="beneficiaryLetterDate" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-4">
            <label for="evaluation_letter_no"><fmt:message key="evaluation_letter_no"/></label>
            <form:input path="evaluationLetterNo" id="evaluation_letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="evaluationLetterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-4">
            <label for="evaluation_letter_date"><fmt:message key="evaluation_letter_date"/></label>
            <form:input path="evaluationLetterDate" id="evaluation_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="evaluationLetterDate" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-12">
            <label for="analytical"><fmt:message key="analytical-text"/></label>
            <form:textarea path="analytical" id="analytical" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="analytical" cssClass="invalid-feedback"/>
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
               id="remove-deputy-analytical-file" href="#"><fmt:message key="file.remove"/></a>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6 mt27">
            <button class="btn btn-primary" type="button" id="add-deputy-analytical"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
            <button class="btn btn-primary hidden" type="button" id="edit-deputy-analytical"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="edit"/></button>
            <button class="btn btn-danger hidden" type="button" id="remove-deputy-analytical"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="remove"/></button>
            <button class="btn btn-secondary hidden" type="button" id="reset-deputy-analytical"><fmt:message key="cancel"/></button>
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
            <th width="200"><fmt:message key="fk_commission_report_oid"/></th>
            <th><fmt:message key="lu_grade_status"/></th>
            <th width="60"></th>
        </tr>
        </thead>
        <tbody id="dynamic-deputy-analytical-data-table">
        </tbody>
    </table>
</div>


<script type="text/javascript">
    class SessionDeputyAnalyticalReport {
        constructor(sessionOid, formId, tableHolderId) {
            this.url='/session-deputy-analytical-report-rest/' + sessionOid;
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
                    '<td>' + val.fkCommissionReportOidTitle + '</td>' +
                    '<td>' + val.luGradeStatusTitle + '</td>' +
                    '<td><a href="#' + val.oid + '" class="edit-deputy-analytical"><i class="fas fa-pen-square m-1"></i></a></td>' +
                    '</tr>');
            });
        }

        fillForm(data) {
            let self = this;
            if(data == null) return;
            $(self.formId + ' #oid').val(data.oid);
            $(self.formId + ' #fk_commission_report_oid').val(data.fkCommissionReportOid);
            $(self.formId + ' #beneficiary_letter_no').val(data.beneficiaryLetterNo);
            if(data.beneficiaryLetterDate != null) $(self.formId + ' #beneficiary_letter_date').val(data.beneficiaryLetterDate.jalaliDate);
            $(self.formId + ' #evaluation_letter_no').val(data.evaluationLetterNo);
            if(data.evaluationLetterDate != null) $(self.formId + ' #evaluation_letter_date').val(data.evaluationLetterDate.jalaliDate);
            $(self.formId + ' #analytical').val(data.analytical);
            $(self.formId + ' #analytical_lu_grade_status').val(data.luGradeStatus);
            $(self.formId + ' #filename').val(data.filename);
            $(self.formId + ' #fk_session_oid').val(data.fkSessionOid);
            if(data.filename) {
                $(self.formId + ' #file-holder > a:first').prop('href', '${CONTEXT}/uploads/' + data.filename);
                $(self.formId + ' #file-holder').removeClass('hidden');
            }

            $(self.formId + ' #edit-deputy-analytical').removeClass('hidden');
            $(self.formId + ' #remove-deputy-analytical').removeClass('hidden');
            $(self.formId + ' #reset-deputy-analytical').removeClass('hidden');
            $(self.formId + ' #add-deputy-analytical').addClass('hidden');
        }
        resetForm() {
            let self = this;
            $(self.formId + ' #edit-deputy-analytical').addClass('hidden');
            $(self.formId + ' #remove-deputy-analytical').addClass('hidden');
            $(self.formId + ' #reset-deputy-analytical').addClass('hidden');
            $(self.formId + ' #add-deputy-analytical').removeClass('hidden');
            $(self.formId + ' #file-holder').addClass('hidden');
            $(self.formId + ' input[type="text"],[type="file"]').val('');
            $(self.formId + ' textarea').val('');
        }

    }
    $(document).ready(function () {
        let deputyAnalytical = new SessionDeputyAnalyticalReport(${sessionItem.oid}, '#deputy-analytical-report-form',
            '#dynamic-deputy-analytical-data-table');
        deputyAnalytical.fetchAll();
        $(document).on('click', '#remove-deputy-analytical-file', function (e) {deputyAnalytical.removeFile($('#deputy-analytical-report-form #oid').val()); return false});
        $(document).on('click', '#remove-deputy-analytical', function (e) {deputyAnalytical.remove($('#deputy-analytical-report-form #oid').val());});
        $(document).on('click', '#edit-deputy-analytical', function (e) {deputyAnalytical.edit()});
        $(document).on('click', '.edit-deputy-analytical', function (e) {deputyAnalytical.fetch($(this).attr('href').substr(1))});
        $(document).on('click', '#add-deputy-analytical', function (e) {deputyAnalytical.add()});
        $(document).on('click', '#reset-deputy-analytical', function (e) {deputyAnalytical.resetForm()});
    });

</script>