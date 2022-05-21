<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="misc-reports"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>

        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form action="${CONTEXT}/misc-report" class="form">
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

                    <label class="col-form-label"><fmt:message key="board"/>: </label>
                    <select class="col-3 form-control custom-select" name="fk_board_oid">
                        <option value="">-- <fmt:message key="board"/> --</option>
                        <c:forEach var="r" items="${boardItems}">
                            <option value="${r.oid}"
                                    <c:if test="${r.oid eq param.fk_board_oid}">selected</c:if>>${r.title}</option>
                        </c:forEach>
                    </select>

                </div>

                <div class="row form-group">
                    <label class="col-form-label"><fmt:message key="lu_type"/>: </label>
                    <select class="col-2 form-control custom-select" name="lu_type">
                        <option value="">-- <fmt:message key="lu_type"/> --</option>
                        <c:forEach var="r" items="${typeItems}">
                            <option value="${r.key}"
                                    <c:if test="${r.key eq param.lu_type}">selected</c:if>>${r.value}</option>
                        </c:forEach>
                    </select>


                    <label class="col-form-label"><fmt:message key="date"/>: </label>
                    <input type="text" class="col-2 form-control date-picker-input" name="from_date" value="${param.from_date}"
                           placeholder="<fmt:message key="from"/>">

                    <input type="text" class="ml-3 col-2 form-control date-picker-input" name="to_date" value="${param.to_date}"
                           placeholder="<fmt:message key="to"/>">

                    <div class="col-2 ml-3"><input type="submit" class="btn btn-primary"
                                              value="<fmt:message key="search"/>"/></div>
                </div>
            </form>

            <c:set var="paging" value="${miscReportItems}"/>
            <%@ include file="../includes/paging.jsp" %>

            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="board"/></th>
                        <th><fmt:message key="title"/></th>
                        <th><fmt:message key="letter_no"/></th>
                        <th><fmt:message key="letter_date"/></th>
                        <th width="60"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${miscReportItems.records}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>
                                <a href="${CONTEXT}/board-period?board_oid=${rec.fkBoardPeriodOid}"
                                   target="_blank">${rec.fkBoardPeriodOidTitle}</a>
                            </td>
                            <td>${rec.title}</td>
                            <td>${rec.letterNo}</td>
                            <td>${rec.letterDate.jalaliDate}</td>
                            <td>
                                <a href="${CONTEXT}/${URI_CLASS}/edit/${rec.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/${URI_CLASS}/remove/${rec.oid}"><i class="fas fa-minus-square m-1"></i></a>
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