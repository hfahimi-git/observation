<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="userItem" cssClass="col-lg-10 float-right">
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="lu_status"><fmt:message key="lu_status"/></label>
                        <form:select path="luStatus" id="lu_status" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" readonly="true"
                                     items="${userStatusItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luStatus" cssClass="invalid-feedback"/>
                    </div>

                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <label for="fkContactOid"><fmt:message key="fk_contact_oid"/></label>
                        <div class="row">
                            <div class="col-md-2">
                                <form:input type="text" path="fkContactOid" id="fkContactOid" readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                <form:errors path="fkContactOid" cssClass="invalid-feedback"/>
                            </div>
                            <div class="col-md-9 pl-0">
                                <input type="text" class="form-control" value="${userItem.fkContactOidTitle}"
                                       id="fkContactOidTitle" readonly="readonly"/>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="btn btn-primary" data-toggle="modal" disabled="disabled"
                                        data-codeholder="fkContactOid" data-titleholder="fkContactOidTitle"
                                        data-target="#contactModalScrollable">
                                    <fmt:message key="select" />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-3">
                        <label for="username"><fmt:message key="username"/></label>
                        <input type="text" name="username" value="${userItem.username}" id="username" class="ltr form-control"
                               readonly="readonly" />
                    </div>

                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <button class="btn btn-danger" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="remove" /></button>
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />" onclick="window.location='/${URI_CLASS}'" />


            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}"><fmt:message key="list" /></a></li>
                <li><a href="/${URI_CLASS}/edit/${userItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${userItem.oid}"><fmt:message key="remove" /></a></li>
            </ul>

        </main>


    </div>
</div>

<%@ include file="../includes/footer.jsp" %>