<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="sessions"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>

        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form action="${CONTEXT}/session" class="form">
                <div class="row form-group">
                    <label class="col-form-label"><fmt:message key="keyword"/>: </label>
                    <input type="text" class="col-2 form-control" name="keyword" value="${param.keyword}"
                           placeholder="<fmt:message key="keyword"/>">

                    <label class="col-form-label"><fmt:message key="lu_period_no"/>: </label>
                    <select class="col-1 form-control custom-select" name="lu_period_no">
                        <option value="">-- <fmt:message key="lu_period_no"/> --</option>
                        <c:forEach var="r" items="${periodItems}">
                            <c:if test="${r.key gt 24}">
                                <option value="${r.key}"
                                        <c:if test="${r.key eq param.lu_period_no}">selected</c:if>>${r.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>

                    <label class="col-form-label"><fmt:message key="session_no"/>: </label>
                    <input type="text" class="col-1 form-control" name="no" value="${param.no}"
                           placeholder="<fmt:message key="session_no"/>">

                    <label class="col-form-label"><fmt:message key="date"/>: </label>
                    <input type="text" class="col-2 form-control date-picker-input" name="date" value="${param.date}"
                           placeholder="<fmt:message key="date"/>">

                    <div class="col-2"><input type="submit" class="btn btn-primary"
                                              value="<fmt:message key="search"/>"/></div>
                </div>
            </form>

            <c:set var="paging" value="${sessionItems}"/>
            <%@ include file="../includes/paging.jsp" %>

            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="board"/></th>
                        <th><fmt:message key="session_no"/></th>
                        <th><fmt:message key="date"/></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${sessionItems.records}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td><a href="${CONTEXT}/board-period?board_oid=${rec.fkBoardPeriodOid}">${rec.fkBoardPeriodOidTitle}</a></td>
                            <td>${rec.no}</td>
                            <td>${rec.date.jalaliDate}</td>
                            <td>
                                <a href="${CONTEXT}/${URI_CLASS}/edit/${rec.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/${URI_CLASS}/remove/${rec.oid}"><i class="fas fa-minus-square m-1"></i></a>
                                <a href="${CONTEXT}/${URI_CLASS}/detail/${rec.oid}"><i class="fas fa-sitemap m-1"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/${URI_CLASS}/add"><i class="fas fa-plus-square"></i> <fmt:message
                        key="${URI_CLASS}.add"/></a>
            </div>
        </main>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>