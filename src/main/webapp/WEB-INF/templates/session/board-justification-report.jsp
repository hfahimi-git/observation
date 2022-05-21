<form:form action="" id="board-justification-report-form" method="post" enctype="multipart/form-data"
           modelAttribute="sessionBoardJustificationReportItem">

    <div class="form-row mb-3">
        <div class="col-md-2">
            <label for="letter_no"><fmt:message key="letter_no"/></label>
            <form:input path="letterNo" id="letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="letterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="letter_date"><fmt:message key="letter_date"/></label>
            <form:input path="letterDate" id="letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="letterDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-8">
            <label for="location"><fmt:message key="location"/></label>
            <form:input path="location" id="location" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="location" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-12">
            <label for="agenda_history"><fmt:message key="agenda_history"/></label>
            <form:textarea path="agendaHistory" id="agenda_history" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="agendaHistory" cssClass="invalid-feedback"/>
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
               id="remove-bjr-file" href="#"><fmt:message key="file.remove"/></a>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6 mt27">
            <button class="btn btn-primary" type="button" id="add-bjr"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
            <button class="btn btn-primary hidden" type="button" id="edit-bjr"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="edit"/></button>
            <button class="btn btn-danger hidden" type="button" id="remove-bjr"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="remove"/></button>
        </div>
    </div>

<%--------------------------------------------------------------------------------------------------------------------%>
    <input type="hidden" name="fkSessionOid" id="fk_session_oid" value="${sessionItem.oid}">
</form:form>


<script type="text/javascript">
    class SessionBoardJustificationReport {
        constructor(sessionOid, formId) {
            this.url='/session-board-justification-report-rest/' + sessionOid;
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
            $(self.formId + ' #letter_no').val(data.letterNo);
            if(data.letterDate != null) $(self.formId + ' #letter_date').val(data.letterDate.jalaliDate);
            $(self.formId + ' #location').val(data.location);
            $(self.formId + ' #agenda_history').val(data.agendaHistory);
            $(self.formId + ' #document').val(data.document);
            $(self.formId + ' #description').val(data.description);
            $(self.formId + ' #filename').val(data.filename);
            $(self.formId + ' #fk_session_oid').val(data.fkSessionOid);
            if(data.filename) {
                $(self.formId + ' #file-holder > a:first').prop('href', '${CONTEXT}/uploads/' + data.filename);
                $(self.formId + ' #file-holder').removeClass('hidden');
            }

            $(self.formId + ' #edit-bjr').removeClass('hidden');
            $(self.formId + ' #remove-bjr').removeClass('hidden');
            $(self.formId + ' #add-bjr').addClass('hidden');
        }
        resetForm() {
            let self = this;
            $(self.formId + ' #edit-bjr').addClass('hidden');
            $(self.formId + ' #remove-bjr').addClass('hidden');
            $(self.formId + ' #add-bjr').removeClass('hidden');
            $(self.formId + ' #file-holder').addClass('hidden');
            $(self.formId + ' input[type="text"],[type="file"]').val('');
            $(self.formId + ' textarea').val('');
        }

    }
    $(document).ready(function () {
        let bjr = new SessionBoardJustificationReport(${sessionItem.oid}, '#board-justification-report-form');
        bjr.fetchData();
        $(document).on('click', '#remove-bjr-file', function (e) {bjr.removeFile(); return false});
        $(document).on('click', '#remove-bjr', function (e) {bjr.remove();});
        $(document).on('click', '#edit-bjr', function (e) {bjr.edit()});
        $(document).on('click', '#add-bjr', function (e) {bjr.add()});
    });

</script>