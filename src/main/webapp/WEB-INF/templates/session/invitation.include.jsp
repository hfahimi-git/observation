<form:form action="" method="post" modelAttribute="sessionInvitationItem" id="session-invitation-form">

    <div class="form-row mb-3">
        <div class="col-md-2">
            <label for="letter_no"><fmt:message key="letter_no"/></label>
            <form:input path="letterNo" id="letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="letterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="letter_received_date"><fmt:message key="letter_received_date"/></label>
            <form:input path="letterReceivedDate" id="letter_received_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="letterReceivedDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="session_start_time"><fmt:message key="start_time"/> <fmt:message key="session"/></label>
            <form:input path="sessionStartTime" id="session_start_time" cssClass="form-control ltr"
                        cssErrorClass="form-control is-invalid ltr" />
            <form:errors path="sessionStartTime" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6 mt27">
            <button class="btn btn-primary" type="button" id="add-inv"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
            <button class="btn btn-primary hidden" type="button" id="edit-inv"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="edit"/></button>
            <button class="btn btn-danger hidden" type="button" id="remove-inv"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="remove"/></button>
        </div>
    </div>

    <%--------------------------------------------------------------------------------------------------------------------%>
    <input type="hidden" name="fkSessionOid" id="fk_session_oid" value="${sessionItem.oid}">
</form:form>

<script type="text/javascript">
    class SessionInvitation {
        constructor(sessionOid, formId) {
            this.url='/session-invitation-rest/' + sessionOid;
            this.sessionOid = sessionOid;
            this.formId = formId;
        }

        add() {
            let self = this;
            let formData = objectifyForm($(self.formId).serializeArray());
            $.ajax({
                type: "POST",
                url: self.url + '/add',
                data: JSON.stringify(formData),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function(data){self.fetchData();},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        edit() {
            let self = this;
            let formData = objectifyForm($(self.formId).serializeArray());
            $.ajax({
                type: "POST",
                url: self.url + '/edit',
                data: JSON.stringify(formData),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function(data){self.fetchData();},
                error:function(obj){alert(obj.responseJSON.message);}
            });
        }

        remove(){
            let self = this;
            $.post(self.url + '/remove').done(function () {self.resetForm();});
        }

        fetchData(){
            let self = this;
            $.get(self.url, function (data) {self.fillForm(data)}, 'json');
        }

        fillForm(data) {
            let self = this;
            if(data == null) return;
            $(self.formId + ' #session_start_time').val(data.sessionStartTime);
            $(self.formId + ' #letter_received_date').val(data.letterReceivedDate.jalaliDate);
            $(self.formId + ' #letter_no').val(data.letterNo);
            $(self.formId + ' #fk_session_oid').val(data.fkSessionOid);
            $(self.formId + ' #edit-inv').removeClass('hidden');
            $(self.formId + ' #remove-inv').removeClass('hidden');
            $(self.formId + ' #add-inv').addClass('hidden');
        }

        resetForm() {
            let self = this;
            $(self.formId + ' #edit-inv').addClass('hidden');
            $(self.formId + ' #remove-inv').addClass('hidden');
            $(self.formId + ' #add-inv').removeClass('hidden');
            $(self.formId + ' #file-holder').addClass('hidden');
            $(self.formId + ' input[type="text"]').val('');
            $(self.formId + ' textarea').val('');
        }

    }
    $(document).ready(function () {
        let inv = new SessionInvitation(${sessionItem.oid}, '#session-invitation-form');
        inv.fetchData();
        $(document).on('click', inv.formId + ' #remove-inv', function (e) {inv.remove();});
        $(document).on('click', inv.formId + ' #edit-inv', function (e) {inv.edit()});
        $(document).on('click', inv.formId + ' #add-inv', function (e) {inv.add()});
    });

</script>