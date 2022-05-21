<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="pmItem" cssClass="col-lg-10 float-right" >
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="lu_gender"><fmt:message key="lu_gender"/></label>
                        <form:select path="luGender" id="lu_gender" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${genderItems}" itemLabel="value" itemValue="key" disabled="true" />
                        <form:errors path="luGender" cssClass="invalid-feedback"/>

                    </div>
                    <div class="col-md-2">
                        <label for="lu_title"><fmt:message key="lu_title"/></label>
                        <form:select path="luTitle" id="lu_title" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                        items="${titleItems}" itemLabel="value" itemValue="key" disabled="true" />
                        <form:errors path="luTitle" cssClass="invalid-feedback"/>

                    </div>
                    <div class="col-md-3">
                        <label for="name"><fmt:message key="name"/></label>
                        <form:input type="text" path="name" id="name" required="required"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="name" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-4">
                        <label for="family"><fmt:message key="family"/></label>
                        <form:input type="text" path="family" id="family" required="required"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="family" cssClass="invalid-feedback"/>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="father_name"><fmt:message key="father_name"/></label>
                        <form:input path="fatherName" id="father_name" cssClass="form-control"
                                    cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="fatherName" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-2">
                        <label for="father_name"><fmt:message key="birth_year"/></label>
                        <form:input type="text" path="birthYear" id="birth_year" required="required"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="birthYear" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-3">
                        <label for="lu_birth_city"><fmt:message key="lu_birth_city"/></label>
                        <form:select path="luBirthCity" id="lu_birth_city" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" disabled="true">
                            <option value=""> -- <fmt:message key="do_select" /> -- </option>
                            <c:forEach var="rec" items="${provinceCityStructure}">
                                <c:forEach var="r" items="${rec.children}">
                                    <option value="${r.key}"
                                            <c:if test="${r.key eq pmItem.luBirthCity}">selected</c:if>>${rec.value} &sc; ${r.value}</option>
                                </c:forEach>
                                <option disabled>
                                    &bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;
                                    &bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;
                                    &bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;&bigstar;
                                </option>
                            </c:forEach>
                        </form:select>

                    </div>
                    <div class="col-md-4">
                        <label for="filename"><fmt:message key="filename"/></label>
                        <form:input type="file" path="file" id="filename" cssClass="form-control-file"
                                    cssErrorClass="form-control-file is-invalid" disabled="true" />
                        <form:errors path="file" cssClass="invalid-feedback"/>
                        <div>
                            <img src="/uploads/${pmItem.filename}" class="img-thumbnail mw80" />
                        </div>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <label for="description"><fmt:message key="description" /></label>
                            <form:textarea class="form-control" id="description" path="description" rows="3" disabled="true" />
                        </div>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <button class="btn btn-danger" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="submit" /></button>
                <button class="btn btn-secondary" type="button" onclick="window.location='/${URI_CLASS}'"><fmt:message key="cancel" /></button>


            </form:form>


            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}"><fmt:message key="list" /></a></li>
                <li><a href="/${URI_CLASS}/edit/${pmItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${pmItem.oid}"><fmt:message key="remove" /></a></li>
                <li><a href="/${URI_CLASS}-period?pm_oid=${pmItem.oid}"><fmt:message key="parliament-member-periods" /></a></li>
            </ul>

        </main>

    </div>
</div>

<%@ include file="../includes/footer.jsp" %>