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
                        <th width="100">#</th>
                        <th><fmt:message key="lu_period_no" /></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${boardPeriodItems}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>${rec.luPeriodNoTitle}</td>
                            <td>
                                <a href="${CONTEXT}/board-period/edit/${rec.oid}?board_oid=${boardItem.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/board-period/remove/${rec.oid}?board_oid=${boardItem.oid}"><i class="fas fa-minus-square m-1"></i></a>
                                <a href="${CONTEXT}/board-period/setting/${rec.oid}?board_oid=${boardItem.oid}"><i class="fas fa-wrench m-1"></i></a>
<%--
                                <a href="${CONTEXT}/board-commission?board_oid=${boardItem.oid}"><i class="fas fa-users m-1"></i></a>
                                <a href="${CONTEXT}/board-expert?board_oid=${boardItem.oid}"><i class="fas fa-star m-1"></i></a>
                                <a href="${CONTEXT}/board-observer?board_oid=${boardItem.oid}"><i class="fas fa-eye m-1"></i></a>
--%>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/board-period/add?board_oid=${boardItem.oid}"><i class="fas fa-plus-square"></i> <fmt:message key="${URI_CLASS}.add" /></a>
            </div>
        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>