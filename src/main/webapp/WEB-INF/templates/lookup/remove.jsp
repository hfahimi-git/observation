<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="lookupItem" cssClass="col-lg-10 float-right">
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <fmt:message key="do_select" var="doSelectText"/>
                        <label for="group_key"><fmt:message key="group_key"/></label>
                        <form:select path="groupKey" id="group_key" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" disabled="true">
                            <form:option value="" label="-- ${doSelectText} --" />
                            <form:options items="${groupKeyItems}" />
                        </form:select>
                        <form:errors path="groupKey" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-9">
                        <label for="group_key_title"><fmt:message key="group_key"/></label>
                        <form:input type="text" path="groupKeyTitle" id="group_key_title" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="groupKey" cssClass="invalid-feedback"/>
                    </div>

                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="key"><fmt:message key="key"/></label>
                        <form:input type="text" path="key" id="key" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="key" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-3">
                        <label for="value"><fmt:message key="value"/></label>
                        <form:input type="text" path="value" id="value" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="value" cssClass="invalid-feedback"/>
                    </div>

                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-3">
                        <label for="extra"><fmt:message key="extra"/></label>
                        <form:input type="text" path="extra" id="extra" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="extra" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-3">
                        <label for="orderby"><fmt:message key="orderby"/></label>
                        <form:input type="text" path="orderby" id="orderby" disabled="true"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="orderby" cssClass="invalid-feedback"/>
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
                <li><a href="/${URI_CLASS}/edit/${lookupItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${lookupItem.oid}"><fmt:message key="remove" /></a></li>
            </ul>

        </main>


    </div>
</div>
<%@ include file="../includes/footer.jsp" %>