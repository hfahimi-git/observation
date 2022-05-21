<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="userItem" cssClass="col-lg-10 float-right">
                <%@ include file="../includes/errors.jsp" %>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="lu_status"><fmt:message key="lu_status"/></label>
                        <form:select path="luStatus" id="lu_status" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${userStatusItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luStatus" cssClass="invalid-feedback"/>
                    </div>

                </div>

<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-8">
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
                                <button type="button" class="btn btn-primary" data-toggle="modal"
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
                        <label for="username"><fmt:message key="username"/> (<fmt:message key="national_code"/>)</label>
                        <c:choose>
                            <c:when test="${URI_METHOD.equals('edit')}">
                                <input type="text" name="username" value="${userItem.username}" id="username" class="ltr form-control"
                                            readonly="readonly" />
                            </c:when>
                            <c:otherwise>
                                <form:input path="username" id="username" cssClass="ltr form-control"
                                            cssErrorClass="ltr form-control is-invalid" required="required" />
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="username" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-3">
                        <label for="password"><fmt:message key="password"/></label>
                        <form:input type="text" path="password" id="password" cssClass="ltr form-control"
                                    cssErrorClass="ltr form-control is-invalid" />
                        <form:errors path="password" cssClass="invalid-feedback"/>
                    </div>
                </div>

<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <label><fmt:message key="roles" /></label><br />
                            <c:forEach var="rec" items="${roleItems}">
                                <div class="custom-control custom-checkbox col-md-2 dib">
                                    <c:set var="selectedRole" value="" />
                                    <c:if test="${userItem.roles.contains(rec.oid)}"><c:set var="selectedRole" value="checked" /></c:if>
                                    <input type="checkbox" name="roles" id="lbl${rec.oid}"
                                       class="custom-control-input" ${selectedRole} value="${rec.oid}" />
                                    <label class="custom-control-label" for="lbl${rec.oid}">${rec.title}</label>
                                </div>
                            </c:forEach>
                            <form:errors path="roles" cssClass="invalid-feedback"/>
                        </div>
                    </div>
                </div>

<%--------------------------------------------------------------------------------------------------------------------%>
                <button class="btn btn-primary" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="submit" /></button>
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />" onclick="window.location='/${URI_CLASS}'" />


            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}"><fmt:message key="list" /></a></li>
                <c:if test="${URI_METHOD.equals('edit')}">
                <li><a href="/${URI_CLASS}/edit/${userItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${userItem.oid}"><fmt:message key="remove" /></a></li>
                </c:if>
            </ul>

        </main>


    </div>
</div>
<%@ include file="../contact/ajax.contact-index.jsp" %>
<%@ include file="../contact/ajax.contact-js.jsp" %>
<%@ include file="../includes/footer.jsp" %>