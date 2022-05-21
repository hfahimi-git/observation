<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="contacts"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>

        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form action="${CONTEXT}/contact" class="form">
                <div class="row form-group">
                    <label class="col-form-label"><fmt:message key="keyword"/>: </label>
                    <input type="text" class="col-2 form-control" name="keyword" value="${param.keyword}"
                           placeholder="<fmt:message key="keyword"/>">

                    <label class="col-form-label"><fmt:message key="lu_contact_type"/>: </label>
                    <select class="col-2 form-control custom-select" name="lu_contact_type">
                        <option value="">-- <fmt:message key="do_select"/> --</option>
                        <c:forEach var="r" items="${contactTypeItems}">
                            <option value="${r.key}"
                                    <c:if test="${r.key eq param.lu_contact_type}">selected</c:if>>${r.value}</option>
                        </c:forEach>
                    </select>

                    <div class="col-2"><input type="submit" class="btn btn-primary"
                                              value="<fmt:message key="search"/>"/></div>
                </div>
            </form>

            <c:set var="paging" value="${contactItems}"/>
            <%@ include file="../includes/paging.jsp" %>

            <div class="table-responsive">
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="lu_contact_type"/></th>
                        <th><fmt:message key="name"/></th>
                        <th><fmt:message key="image"/></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rec" items="${contactItems.records}">
                        <tr>
                            <td>${rec.oid}</td>
                            <td>${rec.luContactTypeTitle}</td>
                            <td>${rec.luTitleTitle} ${rec.name} ${rec.family}</td>
                            <td>
                                <c:if test="${not empty rec.filename}">
                                    <img src="/uploads/${rec.filename}" class="img-thumbnail mw50"
                                         alt="${rec.name} ${rec.family}" title="${rec.name} ${rec.family}"/>
                                </c:if>
                            </td>
                            <td>
                                    <%--                                <a href="${CONTEXT}/contact/show/${pm.oid}"><i class="fas fa-info-square ml-2"></i></a>--%>
                                <a href="${CONTEXT}/contact/edit/${rec.oid}"><i class="fas fa-pen-square m-1"></i></a>
                                <a href="${CONTEXT}/contact/remove/${rec.oid}"><i
                                        class="fas fa-minus-square m-1"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-12 p-0">
                <a href="${CONTEXT}/contact/add"><i class="fas fa-plus-square"></i> <fmt:message
                        key="${URI_CLASS}.add"/></a>
            </div>
        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>