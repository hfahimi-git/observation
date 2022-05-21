<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" enctype="multipart/form-data" modelAttribute="contactItem"
                       cssClass="col-lg-10 float-right">
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="lu_gender"><fmt:message key="lu_contact_type"/></label>
                        <form:select path="luContactType" id="lu_contact_type" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${contactTypeItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luContactType" cssClass="invalid-feedback"/>
                    </div>

                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2" data-type="person">
                        <label for="lu_title"><fmt:message key="lu_title"/></label>
                        <form:select path="luTitle" id="lu_title" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${personContactTypeItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luTitle" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-2" data-type="company">
                        <label for="lu_title"><fmt:message key="lu_title"/></label>
                        <form:select path="luTitle" id="lu_title" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${companyContactTypeItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luTitle" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-2" data-type="person">
                        <label for="lu_gender"><fmt:message key="lu_gender"/></label>
                        <form:select path="luGender" id="lu_gender" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${genderItems}" itemLabel="value" itemValue="key" />
                        <form:errors path="luGender" cssClass="invalid-feedback"/>

                    </div>
                    <div class="col-md-3">
                        <label for="name"><fmt:message key="name"/></label>
                        <form:input type="text" path="name" id="name" required="required"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="name" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-4" data-type="person">
                        <label for="family"><fmt:message key="family"/></label>
                        <form:input type="text" path="family" id="family"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="family" cssClass="invalid-feedback"/>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2" data-type="person">
                        <label for="father_name"><fmt:message key="father_name"/></label>
                        <form:input path="fatherName" id="father_name" cssClass="form-control"
                                    cssErrorClass="form-control is-invalid" />
                        <form:errors path="fatherName" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-2" data-type="person">
                        <label for="birth_date"><fmt:message key="birth_date"/></label>
                        <form:input type="text" path="birthDate" id="birth_date"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="birthDate" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-3">
                        <label for="national_code"><fmt:message key="national_code"/> / <fmt:message key="economic"/></label>
                        <form:input type="text" path="nationalCode" id="national_code"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="nationalCode" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-2">
                        <label for="phone"><fmt:message key="phone"/></label>
                        <form:input type="text" path="phone" id="phone"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="phone" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-2">
                        <label for="cell"><fmt:message key="cell"/></label>
                        <form:input type="text" path="cell" id="cell"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="cell" cssClass="invalid-feedback"/>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="email"><fmt:message key="email"/></label>
                        <form:input path="email" id="email" cssClass="form-control"
                                    cssErrorClass="form-control is-invalid" />
                        <form:errors path="email" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-2">
                        <label for="fax"><fmt:message key="fax"/></label>
                        <form:input type="text" path="fax" id="fax"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="fax" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-7">
                        <label for="url"><fmt:message key="url"/></label>
                        <form:input type="text" path="url" id="url"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" />
                        <form:errors path="url" cssClass="invalid-feedback"/>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11" data-type="company">
                        <label for="fkContactOid"><fmt:message key="fk_contact_oid"/></label>
                        <div class="row">
                            <div class="col-md-2">
                                <form:input type="text" path="fkContactOid" id="fkContactOid" readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid" />
                                <form:errors path="fkContactOid" cssClass="invalid-feedback"/>
                            </div>
                            <div class="col-md-9 pl-0">
                                <input type="text" class="form-control" value="${contactItem.fkContactOidTitle}"
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
                    <div class="col-md-11">
                        <label for="filename"><fmt:message key="filename"/></label>
                        <form:input type="file" path="file" id="filename" cssClass="form-control-file"
                                    cssErrorClass="form-control-file is-invalid" />
                        <form:errors path="file" cssClass="invalid-feedback"/>
                        <c:if test="${URI_METHOD.equals('edit') && not empty contactItem.filename }">
                            <div>
                                <img src="/uploads/${contactItem.filename}" class="img-thumbnail mw80" />
                                <a href="/contact/remove-image/${contactItem.oid}"
                                   class="btn btn-sm btn-danger vab"
                                   onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="remove" /></a>
                            </div>
                        </c:if>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <label for="description"><fmt:message key="description" /></label>
                            <form:textarea class="form-control" id="description" path="description" rows="3" />
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
                <li><a href="/${URI_CLASS}/edit/${contactItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${contactItem.oid}"><fmt:message key="remove" /></a></li>
                </c:if>
            </ul>

        </main>


    </div>
</div>
<script>
    $(document).ready(function () {
        $('#lu_contact_type').on('change', function () {
            $("div[data-type='" + $(this).val() + "']").show().find('input,select').prop('disabled', false);
            $("div[data-type][data-type!='" + $(this).val() + "']").hide().find('input,select').prop('disabled', true);
        });
        $('#lu_contact_type').trigger('change');
    });
</script>
<%@ include file="ajax.contact-index.jsp" %>
<%@ include file="ajax.contact-js.jsp" %>
<%@ include file="../includes/footer.jsp" %>