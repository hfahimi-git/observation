<form:form action="" id="deputy-justification-report-form" method="post" enctype="multipart/form-data"
           modelAttribute="sessionDeputyJustificationReportItem">

    <div class="form-row mb-3">
        <div class="col-md-2">
            <label for="letter_no"><fmt:message key="letter_no"/></label>
            <form:input path="sendLetterNo" id="letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="sendLetterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="send_letter_date"><fmt:message key="letter_date"/></label>
            <form:input path="sendLetterDate" id="send_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="sendLetterDate" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-12">
            <label for="expert_opinion"><fmt:message key="expert_opinion"/></label>
            <form:textarea path="expertOpinion" id="expert_opinion" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="expertOpinion" cssClass="invalid-feedback"/>
        </div>
    </div>

    <div class="form-row mb-3">
        <div class="col-md-6">
            <label for="document"><fmt:message key="document"/></label>
            <form:textarea path="document" id="document" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="document" cssClass="invalid-feedback"/>
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
               id="remove-djr-file" href="#"><fmt:message key="file.remove"/></a>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6 mt27">
            <button class="btn btn-primary" type="button" id="add-djr"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
            <button class="btn btn-primary hidden" type="button" id="edit-djr"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="edit"/></button>
            <button class="btn btn-danger hidden" type="button" id="remove-djr"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="remove"/></button>
        </div>
    </div>

<%--------------------------------------------------------------------------------------------------------------------%>
    <input type="hidden" name="fkSessionOid" id="fk_session_oid" value="${sessionItem.oid}">
</form:form>


<script type="text/javascript">
    class SessionDeputyJustificationReport {
        constructor(sessionOid, formId) {
            this.url='/session-deputy-justification-report-rest/' + sessionOid;
            this.sessionOid = sessionOid;
            this.formId = formId;
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
                success: function(data){self.fetchData();},
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
                success: function(data){self.fetchData();},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        remove(){
            let self = this;
            $.post(self.url + '/remove').done(function () {self.resetForm();});
        }

        removeFile(){
            let self = this;
            $.post(self.url + '/remove-file').done(function () {$(self.formId + ' #file-holder').addClass('hidden');});
        }

        fetchData(){
            let self = this;
            $.get(self.url, function (data) {self.fillForm(data)}, 'json');
        }

        fillForm(data) {
            let self = this;
            if(data == null) return;
            $(self.formId + ' #letter_no').val(data.sendLetterNo);
            if(data.sendLetterDate != null) $(self.formId + ' #send_letter_date').val(data.sendLetterDate.jalaliDate);
            $(self.formId + ' #expert_opinion').val(data.expertOpinion);
            $(self.formId + ' #document').val(data.document);
            $(self.formId + ' #description').val(data.description);
            $(self.formId + ' #filename').val(data.filename);
            $(self.formId + ' #fk_session_oid').val(data.fkSessionOid);
            if(data.filename) {
                $(self.formId + ' #file-holder > a:first').prop('href', '${CONTEXT}/uploads/' + data.filename);
                $(self.formId + ' #file-holder').removeClass('hidden');
            }

            $(self.formId + ' #edit-djr').removeClass('hidden');
            $(self.formId + ' #remove-djr').removeClass('hidden');
            $(self.formId + ' #add-djr').addClass('hidden');
        }
        resetForm() {
            let self = this;
            $(self.formId + ' #edit-djr').addClass('hidden');
            $(self.formId + ' #remove-djr').addClass('hidden');
            $(self.formId + ' #add-djr').removeClass('hidden');
            $(self.formId + ' #file-holder').addClass('hidden');
            $(self.formId + ' input[type="text"],[type="file"]').val('');
            $(self.formId + ' textarea').val('');
        }

    }
    $(document).ready(function () {
        let djr = new SessionDeputyJustificationReport(${sessionItem.oid}, '#deputy-justification-report-form');
        djr.fetchData();
        $(document).on('click', '#remove-djr-file', function (e) {djr.removeFile(); return false});
        $(document).on('click', '#remove-djr', function (e) {djr.remove();});
        $(document).on('click', '#edit-djr', function (e) {djr.edit()});
        $(document).on('click', '#add-djr', function (e) {djr.add()});
    });

</script>