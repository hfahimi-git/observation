<div class="card mb-3">
    <div class="row no-gutters">
        <div class="card-body">
            <label>#: ${pmpItem.oid}</label>
            <a href="${CONTEXT}/parliament-member-period?pm_oid=${pmItem.oid}">
            <h5 class="card-title">
                <fmt:message key="lu_period_no"/>: ${pmpItem.luPeriodNoTitle}
            </h5>
            </a>
            <p class="card-text">
                <fmt:message key="election_realm"/>: ${pmpItem.luProvinceTitle} (
                <c:forEach var="c" items="${pmpItem.citiesComplex}" varStatus="l">
                    ${c.entrySet().iterator().next().getValue()}
                    <c:if test="${!l.last}"><fmt:message key="comma"/></c:if>
                </c:forEach>
                )
            </p>
        </div>
    </div>
</div>
