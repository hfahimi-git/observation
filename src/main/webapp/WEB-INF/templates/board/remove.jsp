<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="boardItem"
                       cssClass="col-lg-10 float-right">
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="lu_board_type"><fmt:message key="lu_board_type"/></label>
                        <form:select path="luBoardType" id="lu_board_type" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" disabled="true"
                                     items="${boardTypeItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luBoardType" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-9">
                        <label for="title"><fmt:message key="title"/></label>
                        <form:input type="text" path="title" id="title"  disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="title" cssClass="invalid-feedback"/>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <label for="fkChairmanOid"><fmt:message key="fk_chairman_oid"/></label>
                        <div class="row">
                            <div class="col-md-2">
                                <form:input type="text" path="fkChairmanOid" id="fkChairmanOid" readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                <form:errors path="fkChairmanOid" cssClass="invalid-feedback"/>
                            </div>
                            <div class="col-md-9 pl-0">
                                <input type="text" class="form-control" value="${boardItem.fkChairmanOidTitle}"
                                       id="fkChairmanOidTitle" readonly="readonly"/>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-codeholder="fkChairmanOid" data-titleholder="fkChairmanOidTitle"
                                        data-target="#contactModalScrollable">
                                    <fmt:message key="select" />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11" data-type="company">
                        <label for="fkSecretaryOid"><fmt:message key="fk_secretary_oid"/></label>
                        <div class="row">
                            <div class="col-md-2">
                                <form:input type="text" path="fkSecretaryOid" id="fkSecretaryOid" readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                <form:errors path="fkChairmanOid" cssClass="invalid-feedback"/>
                            </div>
                            <div class="col-md-9 pl-0">
                                <input type="text" class="form-control" value="${boardItem.fkSecretaryOidTitle}"
                                       id="fkSecretaryOidTitle" readonly="readonly"/>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-codeholder="fkSecretaryOid" data-titleholder="fkSecretaryOidTitle"
                                        data-target="#contactModalScrollable">
                                    <fmt:message key="select" />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="title"><fmt:message key="title"/></label>
                        <form:input type="text" path="observationCount" id="observation_count" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="observationCount" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-3">
                        <label for="lu_session_period"><fmt:message key="lu_session_period"/></label>
                        <form:select path="luSessionPeriod" id="lu_session_period" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" disabled="true"
                                     items="${sessionPeriodItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luSessionPeriod" cssClass="invalid-feedback"/>
                    </div>

                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-3">
                        <label for="phone"><fmt:message key="phone"/></label>
                        <form:input type="text" path="phone" id="phone" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="phone" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-3">
                        <label for="fax"><fmt:message key="fax"/></label>
                        <form:input type="text" path="fax" id="fax" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="fax" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-4">
                        <label for="email"><fmt:message key="email"/></label>
                        <form:input path="email" id="email" cssClass="form-control" disabled="true"
                                    cssErrorClass="form-control is-invalid" />
                        <form:errors path="email" cssClass="invalid-feedback"/>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <label for="related_law"><fmt:message key="related_law" /></label>
                            <form:textarea class="form-control" id="related_law" path="relatedLaw" rows="3" />
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
                <li><a href="/${URI_CLASS}/edit/${boardItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${boardItem.oid}"><fmt:message key="remove" /></a></li>
            </ul>

        </main>


    </div>
</div>
<%@ include file="../contact/ajax.contact-index.jsp" %>
<%@ include file="../contact/ajax.contact-js.jsp" %>

<%@ include file="../includes/footer.jsp" %>