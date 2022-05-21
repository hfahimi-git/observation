<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="miscReportItem" cssClass="col-lg-10 float-right">
                <%@ include file="../includes/errors.jsp" %>
                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-3">
                        <label for="lu_type"><fmt:message key="lu_type"/></label>
                        <form:select path="luType" id="lu_type" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${typeItems}" itemLabel="value" itemValue="key" disabled="true" />
                        <form:errors path="luType" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-8">
                        <label for="title"><fmt:message key="title"/></label>
                        <form:input type="text" path="title" id="title" required="required"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="title" cssClass="invalid-feedback"/>
                    </div>

                </div>
                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-9">
                        <label for="fkBoardPeriodOid"><fmt:message key="fk_board_period_oid"/></label>
                        <div class="row">
                            <div class="col-md-2">
                                <form:input type="text" path="fkBoardPeriodOid" id="fkBoardPeriodOid" readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid"/>
                                <form:errors path="fkBoardPeriodOid" cssClass="invalid-feedback"/>
                            </div>
                            <div class="col-md-7 pl-0">
                                <form:input type="text" path="fkBoardPeriodOidTitle" id="fkBoardPeriodOidTitle"
                                            readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid"/>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-codeholder="fkBoardPeriodOid" data-titleholder="fkBoardPeriodOidTitle"
                                        data-target="#bpModalScrollable">
                                    <fmt:message key="select"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">

                    <div class="col-md-2">
                        <label for="letter_no"><fmt:message key="letter_no"/></label>
                        <form:input path="letterNo" id="letter_no" cssClass="form-control"
                                    cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="letterNo" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-2">
                        <label for="letter_date"><fmt:message key="letter_date"/></label>
                        <form:input path="letterDate" id="letter_date" cssClass="form-control date-picker-input"
                                    cssErrorClass="form-control date-picker-input is-invalid" disabled="true" />
                        <form:errors path="letterDate" cssClass="invalid-feedback"/>
                    </div>

                </div>
                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <label for="filename"><fmt:message key="filename"/></label>
                        <form:input type="file" path="file" id="filename" cssClass="form-control-file"
                                    cssErrorClass="form-control-file is-invalid" disabled="true" />
                        <form:errors path="file" cssClass="invalid-feedback"/>
                        <c:if test="${not empty miscReportItem.filename }">
                            <div>
                                <a href="/uploads/${miscReportItem.filename}" target="_blank"
                                   class="btn btn-sm btn-info vab"><fmt:message key="download" /></a>
                            </div>
                        </c:if>
                    </div>

                </div>
                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <label for="description"><fmt:message key="description" /></label>
                            <form:textarea class="form-control" id="description" path="description" rows="3" disabled="true"  />
                        </div>
                    </div>
                </div>
                <%--------------------------------------------------------------------------------------------------------------------%>

                <button class="btn btn-danger" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="submit" /></button>
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />"
                       onclick="window.location='/${URI_CLASS}'" />


            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}"><fmt:message key="list" /></a></li>
                <li><a href="/${URI_CLASS}/edit/${miscReportItem.oid}"><fmt:message key="edit"/></a></li>
                <li><a href="/${URI_CLASS}/remove/${miscReportItem.oid}"><fmt:message key="remove"/></a></li>
            </ul>

        </main>


    </div>
</div>
<%@ include file="../board/ajax.period-index.jsp" %>
<%@ include file="../board/ajax.period-js.jsp" %>

<%@ include file="../includes/footer.jsp" %>