<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <div class="row">
                <div class="col-lg-6"><%@ include file="include.jsp" %></div>
                <div class="col-lg-6 d-flex"><%@ include file="period.include.jsp" %></div>
            </div>
            <form:form action="?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}" method="post" modelAttribute="pmCommissionItem"
                       cssClass="col-lg-10 float-right">
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-3">
                        <label for="lu_commission"><fmt:message key="lu_commission"/></label>
                        <form:select path="luCommission" id="lu_commission" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${commissionItems}" itemLabel="value" itemValue="key" disabled="true" />
                        <form:errors path="luCommission" cssClass="invalid-feedback"/>

                    </div>
                    <div class="col-md-2">
                        <label for="lu_year_no"><fmt:message key="lu_year_no"/></label>
                        <form:select path="luYearNo" id="lu_year_no" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${yearNoItems}" itemLabel="value" itemValue="key" disabled="true" />
                        <form:errors path="luYearNo" cssClass="invalid-feedback"/>

                    </div>
                    <div class="col-md-2">
                        <label for="lu_commission_role"><fmt:message key="lu_commission_role"/></label>
                        <form:select path="luCommissionRole" id="lu_commission_role" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${commissionRoleItems}" itemLabel="value" itemValue="key" disabled="true" />
                        <form:errors path="luCommissionRole" cssClass="invalid-feedback"/>

                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <button class="btn btn-danger" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="submit" /></button>
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />"
                       onclick="window.location='/${URI_CLASS}?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}'" />

                <input type="hidden" name="fkParliamentMemberOid" value="${pmItem.oid}">
                <input type="hidden" name="luPeriodNo" value="${pmpItem.luPeriodNo}">
            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}"><fmt:message key="list" /></a></li>
                <li><a href="/${URI_CLASS}/edit/${pmCommissionItem.oid}?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${pmCommissionItem.oid}?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}"><fmt:message key="remove" /></a></li>
            </ul>

        </main>


    </div>
</div>

<%@ include file="../includes/footer.jsp" %>