<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <%@ include file="include.jsp" %>
            <form:form action="?pm_oid=${pmItem.oid}" method="post" modelAttribute="pmPeriodItem"
                       cssClass="col-lg-10 float-right">
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="lu_inter_period"><fmt:message key="lu_inter_period"/></label>
                        <form:select path="luInterPeriod" id="lu_inter_period" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid"
                                     items="${yesNoItems}" itemLabel="value" itemValue="key" disabled="true" />
                        <form:errors path="luInterPeriod" cssClass="invalid-feedback"/>

                    </div>
                    <div class="col-md-2">
                        <label for="lu_period_no"><fmt:message key="lu_period_no"/></label>
                        <form:select path="luPeriodNo" id="lu_period_no" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" disabled="true">
                            <c:forEach var="rec" items="${periodItems}">
                                <c:if test="${rec.key gt 24}">
                                <option value="${rec.key}"
                                        <c:if test="${rec.key eq pmPeriodItem.luPeriodNo}">selected</c:if>>${rec.value}</option>
                                </c:if>
                            </c:forEach>

                        </form:select>
                        <form:errors path="luPeriodNo" cssClass="invalid-feedback"/>

                    </div>
                    <div class="col-md-3">
                        <label for="lu_province"><fmt:message key="lu_province"/></label>
                        <form:select path="luProvince" id="lu_province" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" disabled="true" >
                            <option value=""> -- <fmt:message key="do_select" /> -- </option>
                            <c:forEach var="rec" items="${provinceCityStructure}">
                                <option value="${rec.key}"
                                        <c:if test="${rec.key eq pmPeriodItem.luProvince}">selected</c:if>>${rec.value}</option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="luProvince" cssClass="invalid-feedback"/>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <script>
                    $(document).ready(function () {
                        let provinceData = {
                            <c:forEach var="rec" items="${provinceCityStructure}">
                            "${rec.key}":[<c:forEach var="r" items="${rec.children}">{"k": "${r.key}", "v":"${r.value}"},</c:forEach>],
                            </c:forEach>
                        };
                        let selectedCities = [<c:forEach var="rec" items="${pmPeriodItem.cities}">"${rec}",</c:forEach>];
                        $('#lu_province').on('change', function () {
                            let province = $(this).val();
                            if(!(province in provinceData))
                                return;
                            $('#cityHolder').empty();
                            let i = 0;
                            provinceData[province].forEach(function (item) {
                                $('#cityHolder').append(
                                    '<div class="custom-control custom-checkbox col-md-2 dib">' +
                                    '<input class="custom-control-input" type="checkbox" id="' + (item.k + i) +
                                    '" name="cities" value="' + item.k + '" ' +
                                    (selectedCities.indexOf(item.k) >= 0? 'checked': '')  + ' disabled="disabled">' +
                                    '<label class="custom-control-label" for="' + (item.k + i) + '">' +
                                    item.v + '</label>' +
                                    '</div>');
                                i++;

                            })
                        });
                        $('#lu_province').trigger('change');
                    });
                </script>
                <div class="form-row align-items-center mb-3">
                    <label for="cities"><fmt:message key="cities_of_realm" /></label><br />
                    <div class="col-md-11" id="cityHolder">

                    </div>
                    <form:errors path="cities" cssClass="invalid-feedback"/>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-11">
                        <div class="form-group">
                            <label for="languages"><fmt:message key="languages" /></label><br />
                            <c:forEach var="rec" items="${languageItems}">
                                <div class="custom-control custom-checkbox col-md-2 dib">
                                    <c:set var="selectedlang" value="" />
                                    <c:if test="${pmPeriodItem.languages.contains(rec.key)}"><c:set var="selectedlang" value="checked" /></c:if>
                                    <input type="checkbox" name="languages" id="lbl${rec.key}"
                                                   class="custom-control-input"
                                                   ${selectedlang} disabled="disabled"
                                                   value="${rec.key}" />
                                    <label class="custom-control-label" for="lbl${rec.key}">${rec.value}</label>
                                </div>
                            </c:forEach>
                            <form:errors path="languages" cssClass="invalid-feedback"/>
                        </div>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="vote_count"><fmt:message key="vote_count"/></label>
                        <form:input path="voteCount" id="vote_count" cssClass="form-control"
                                    cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="voteCount" cssClass="invalid-feedback"/>
                    </div>

                    <div class="col-md-2">
                        <label for="vote_percent"><fmt:message key="vote_percent"/></label>
                        <form:input type="text" path="votePercent" id="vote_percent" required="required"
                                    cssClass="form-control" cssErrorClass="form-control is-invalid" disabled="true" />
                        <form:errors path="votePercent" cssClass="invalid-feedback"/>
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
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />"
                       onclick="window.location='/${URI_CLASS}?pm_oid=${pmItem.oid}'" />

                <input type="hidden" name="fkParliamentMemberOid" value="${pmItem.oid}">
            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}?pm_oid=${pmItem.oid}"><fmt:message key="list" /></a></li>
                <li><a href="/${URI_CLASS}/edit/${pmPeriodItem.oid}?pm_oid=${pmItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/parliament-member-commission?pm_oid=${pmItem.oid}&pmp_oid=${pmPeriodItem.oid}"><fmt:message key="parliament-member-commissions" /></a></li>
            </ul>

        </main>


    </div>
</div>

<%@ include file="../includes/footer.jsp" %>