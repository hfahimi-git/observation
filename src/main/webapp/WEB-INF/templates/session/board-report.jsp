<form:form action="" id="board-report-form" method="post" enctype="multipart/form-data"
           modelAttribute="sessionBoardReportItem">

    <div class="form-row mb-3">
        <div class="col-md-12">
            <div class="form-group">
                <label><fmt:message key="absents_presents" /></label><br />
                <c:forEach var="rec" items="${observerItems}">
                    <div class="custom-control custom-checkbox col-md-2 dib">
                        <c:set var="present" value="" />
                        <c:if test="${presentItems.contains(rec.fkObserverOid)}"><c:set var="present" value="checked" /></c:if>
                        <input type="checkbox" name="presents" id="lbl${rec.fkObserverOid}"
                               class="custom-control-input" ${present} value="${rec.fkObserverOid}" />
                        <label class="custom-control-label" for="lbl${rec.fkObserverOid}">${rec.fkObserverOidTitle}</label>
                    </div>
                </c:forEach>
<%--                <form:errors path="languages" cssClass="invalid-feedback"/>--%>
            </div>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-2">
            <label for="letter_no"><fmt:message key="letter_no"/></label>
            <form:input path="letterNo" id="letter_no" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="letterNo" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-2">
            <label for="board_letter_date"><fmt:message key="letter_date"/></label>
            <form:input path="letterDate" id="board_letter_date" cssClass="form-control date-picker-input"
                        cssErrorClass="form-control date-picker-input is-invalid" />
            <form:errors path="letterDate" cssClass="invalid-feedback"/>
        </div>
        <div class="col-md-3">
            <label for="lu_hold_status"><fmt:message key="board-report.lu_hold_status"/></label>
            <form:select path="luHoldStatus" id="lu_hold_status" cssClass="form-control custom-select"
                         cssErrorClass="form-control custom-select is-invalid"
                         items="${yesNoItems}" itemLabel="value" itemValue="key" />
            <form:errors path="luHoldStatus" cssClass="invalid-feedback"/>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-12">
            <label for="approval"><fmt:message key="board-report.approval"/></label>
            <form:textarea path="approval" id="approval" cssClass="form-control"
                        cssErrorClass="form-control is-invalid" />
            <form:errors path="approval" cssClass="invalid-feedback"/>
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
               id="remove-board-file" href="#"><fmt:message key="file.remove"/></a>
        </div>
    </div>
    <div class="form-row mb-3">
        <div class="col-md-6 mt27">
            <button class="btn btn-primary" type="button" id="add-board"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="add"/></button>
            <button class="btn btn-primary hidden" type="button" id="edit-board"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="edit"/></button>
            <button class="btn btn-danger hidden" type="button" id="remove-board"
                    onclick="return confirm('<fmt:message key="are_you_sure"/>')"><fmt:message key="remove"/></button>
        </div>
    </div>

<%--------------------------------------------------------------------------------------------------------------------%>
    <input type="hidden" name="fkSessionOid" id="fk_session_oid" value="${sessionItem.oid}">
</form:form>


<script type="text/javascript">
    class SessionBoardReport {
        constructor(sessionOid, formId) {
            this.url='/session-board-report-rest/' + sessionOid;
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
            if(data.letterDate != null) $(self.formId + ' #board_letter_date').val(data.letterDate.jalaliDate);
            $(self.formId + ' #lu_hold_status').val(data.luHoldStatus);
            $(self.formId + ' #agenda_history').val(data.agendaHistory);
            $(self.formId + ' #approval').val(data.approval);
            $(self.formId + ' #filename').val(data.filename);
            $(self.formId + ' #fk_session_oid').val(data.fkSessionOid);
            if(data.filename) {
                $(self.formId + ' #file-holder > a:first').prop('href', '${CONTEXT}/uploads/' + data.filename);
                $(self.formId + ' #file-holder').removeClass('hidden');
            }

            $(self.formId + ' #edit-board').removeClass('hidden');
            $(self.formId + ' #remove-board').removeClass('hidden');
            $(self.formId + ' #add-board').addClass('hidden');
        }
        resetForm() {
            let self = this;
            $(self.formId + ' #edit-board').addClass('hidden');
            $(self.formId + ' #remove-board').addClass('hidden');
            $(self.formId + ' #add-board').removeClass('hidden');
            $(self.formId + ' #file-holder').addClass('hidden');
            $(self.formId + ' input[type="text"],[type="file"]').val('');
            $(self.formId + ' textarea').val('');
        }

    }
    $(document).ready(function () {
        let board = new SessionBoardReport(${sessionItem.oid}, '#board-report-form');
        board.fetchData();
        $(document).on('click', '#remove-board-file', function (e) {board.removeFile(); return false});
        $(document).on('click', '#remove-board', function (e) {board.remove();});
        $(document).on('click', '#edit-board', function (e) {board.edit()});
        $(document).on('click', '#add-board', function (e) {board.add()});
    });

</script>