<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="parliament-member-commission"/></c:set>
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

            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="year_no" /></th>
                        <th><fmt:message key="lu_commission" /></th>
                        <th><fmt:message key="lu_commission_role" /></th>
                        <th width="64"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${pmCommissionItems}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>${rec.luYearNoTitle}</td>
                            <td>${rec.luCommissionTitle}</td>
                            <td>${rec.luCommissionRoleTitle}</td>
                            <td>
                                <a href="${CONTEXT}/parliament-member-commission/edit/${rec.oid}?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/parliament-member-commission/remove/${rec.oid}?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}"><i class="fas fa-minus-square m-1"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/parliament-member-commission/add?pm_oid=${pmItem.oid}&pmp_oid=${pmpItem.oid}"><i class="fas fa-plus-square"></i> <fmt:message key="${URI_CLASS}.add" /></a>
            </div>
        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>