<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="parliament-member-periods"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>

        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <%@ include file="include.jsp" %>
            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="parliament-member-period" /></th>
                        <th><fmt:message key="realm" /></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${pmPeriodItems}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>${rec.luPeriodNoTitle}</td>
                            <td>${rec.luProvinceTitle} (
                                <c:forEach var="c" items="${rec.citiesComplex}" varStatus="l">
                                    ${c.entrySet().iterator().next().getValue()}
                                    <c:if test="${!l.last}"><fmt:message key="comma"/></c:if>
                                </c:forEach>
                                )
                            </td>
                            <td>
                                <a href="${CONTEXT}/parliament-member-period/edit/${rec.oid}?pm_oid=${pmItem.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/parliament-member-period/remove/${rec.oid}?pm_oid=${pmItem.oid}"><i class="fas fa-minus-square m-1"></i></a>
                                <a href="${CONTEXT}/parliament-member-commission?pm_oid=${pmItem.oid}&pmp_oid=${rec.oid}"><i class="fas fa-users m-1"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/parliament-member-period/add?pm_oid=${pmItem.oid}"><i class="fas fa-plus-square"></i> <fmt:message key="${URI_CLASS}.add" /></a>
            </div>
        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>