<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="users"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>

        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form action="${CONTEXT}/user" class="form">
                <div class="row form-group">
                    <label class="col-form-label"><fmt:message key="keyword"/>: </label>
                    <input type="text" class="col-2 form-control" name="keyword" value="${param.keyword}"
                           placeholder="<fmt:message key="keyword"/>">

                    <label class="col-form-label"><fmt:message key="lu_status"/>: </label>
                    <select class="col-2 form-control custom-select" name="lu_status">
                        <option value="">-- <fmt:message key="do_select"/> --</option>
                        <c:forEach var="r" items="${userStatusItems}">
                            <option value="${r.key}"
                                    <c:if test="${r.key eq param.lu_status}">selected</c:if>>${r.value}</option>
                        </c:forEach>
                    </select>

                    <div class="col-2"><input type="submit" class="btn btn-primary"
                                              value="<fmt:message key="search"/>"/></div>
                </div>
            </form>

            <c:set var="paging" value="${userItems}"/>
            <%@ include file="../includes/paging.jsp" %>

            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="lu_status"/></th>
                        <th><fmt:message key="username"/></th>
                        <th><fmt:message key="fk_contact_oid"/></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${userItems.records}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>
                                <c:if test="${rec.luStatus eq 'enable'}"><i class="cg fa fa-check-circle" aria-hidden="true"></i></c:if>
                                <c:if test="${rec.luStatus ne 'enable'}"><i class="cr fa fa-times-circle" aria-hidden="true"></i></c:if>
                            </td>
                            <td>${rec.username}</td>
                            <td>${rec.fkContactOidTitle}</td>
                            <td>
                                <a href="${CONTEXT}/user/edit/${rec.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/user/remove/${rec.oid}"><i
                                        class="fas fa-minus-square m-1"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/user/add"><i class="fas fa-plus-square"></i> <fmt:message
                        key="${URI_CLASS}.add"/></a>
            </div>
        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>