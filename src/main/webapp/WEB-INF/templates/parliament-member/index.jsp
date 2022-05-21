<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="parliament-members"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>

        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form action="${CONTEXT}/parliament-member" class="form">
                <div class="row form-group">
                    <label class="col-form-label"><fmt:message key="keyword"/>: </label>
                    <input type="text" class="col-2 form-control" name="keyword" value="${param.keyword}"
                           placeholder="<fmt:message key="keyword"/>">
                    <div class="col-2"><input type="submit" class="btn btn-primary" value="<fmt:message key="search"/>"/></div>
                </div>
            </form>

            <c:set var="paging" value="${pmItems}"/>
            <%@ include file="../includes/paging.jsp" %>

            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>
                            <fmt:message key="family" />
                            <fmt:message key="comma" />
                            <fmt:message key="name" />
                        </th>
                        <th><fmt:message key="father_name" /></th>
                        <th><fmt:message key="image" /></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${pmItems.records}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>${rec.family}
                                <fmt:message key="comma" />
                                ${rec.name}
                            </td>
                            <td>${rec.fatherName}</td>
                            <td><img src="/uploads/${rec.filename}" class="img-thumbnail mw50"
                                     alt="${rec.name} ${rec.family}" title="${rec.name} ${rec.family}"/></td>
                            <td>
<%--                                <a href="${CONTEXT}/parliament-member/show/${pm.oid}"><i class="fas fa-info-square ml-2"></i></a>--%>
                                <a href="${CONTEXT}/parliament-member/edit/${rec.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/parliament-member/remove/${rec.oid}"><i class="fas fa-minus-square m-1"></i></a>
                                <a href="${CONTEXT}/parliament-member-period?pm_oid=${rec.oid}"><i class="far fa-hourglass m-1"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/parliament-member/add"><i class="fas fa-plus-square"></i> <fmt:message key="${URI_CLASS}.add" /></a>
            </div>
        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>