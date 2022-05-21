<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="lookups"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>

        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form action="${CONTEXT}/lookup" class="form">
                <div class="row form-group">
                    <label class="col-form-label"><fmt:message key="keyword"/>: </label>
                    <input type="text" class="col-2 form-control" name="keyword" value="${param.keyword}"
                           placeholder="<fmt:message key="keyword"/>">

                    <label class="col-form-label"><fmt:message key="group_key"/>: </label>

                    <select class="col-3 form-control custom-select" name="group_key">
                        <option value="">-- <fmt:message key="do_select"/> --</option>
                        <c:forEach var="r" items="${groupKeyItems}">
                            <option value="${r}"
                                    <c:if test="${r eq param.group_key}">selected</c:if>>${r}</option>
                        </c:forEach>
                    </select>

                    <div class="col-2"><input type="submit" class="btn btn-primary"
                                              value="<fmt:message key="search"/>"/></div>
                </div>
            </form>

            <c:set var="paging" value="${lookupItems}"/>
            <%@ include file="../includes/paging.jsp" %>

            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="group_key"/></th>
                        <th><fmt:message key="key"/></th>
                        <th><fmt:message key="value"/></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${lookupItems.records}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>${rec.groupKey}</td>
                            <td>${rec.key}</td>
                            <td>${rec.value}</td>
                            <td>
                                <a href="${CONTEXT}/lookup/edit/${rec.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/lookup/remove/${rec.oid}"><i class="fas fa-minus-square m-1"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/lookup/add"><i class="fas fa-plus-square"></i> <fmt:message
                        key="${URI_CLASS}.add"/></a>
            </div>
        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>