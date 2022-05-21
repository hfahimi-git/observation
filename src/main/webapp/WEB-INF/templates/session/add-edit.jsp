<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="sessionItem" cssClass="col-lg-10 float-right">
                <%@ include file="../includes/errors.jsp" %>
                <div class="form-row mb-3">
                    <div class="col-md-9">
                        <label for="fkBoardPeriodOid"><fmt:message key="fk_board_period_oid"/></label>
                        <div class="row">
                            <div class="col-md-2">
                                <form:input type="text" path="fkBoardPeriodOid" id="fkBoardPeriodOid" readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid"/>
                                <form:errors path="fkBoardPeriodOid" cssClass="invalid-feedback"/>
                            </div>
                            <div class="col-md-7 pl-0">
                                <form:input type="text" path="fkBoardPeriodOidTitle" id="fkBoardPeriodOidTitle"
                                            readonly="true"
                                            cssClass="form-control" cssErrorClass="form-control is-invalid"/>
                            </div>
                            <div class="col-md-1">
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-codeholder="fkBoardPeriodOid" data-titleholder="fkBoardPeriodOidTitle"
                                        data-target="#bpModalScrollable">
                                    <fmt:message key="select"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-row mb-3">

                <div class="col-md-2">
                        <label for="no"><fmt:message key="session_no"/></label>
                        <form:input path="no" id="no" cssClass="form-control"
                                    cssErrorClass="form-control is-invalid"/>
                        <form:errors path="no" cssClass="invalid-feedback"/>
                    </div>
                    <div class="col-md-2">
                        <label for="date"><fmt:message key="date"/></label>
                        <form:input path="date" id="date" cssClass="form-control date-picker-input"
                                    cssErrorClass="form-control date-picker-input is-invalid"/>
                        <form:errors path="date" cssClass="invalid-feedback"/>
                    </div>
                </div>

                <button class="btn btn-primary" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="submit" /></button>
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />" onclick="window.location='/${URI_CLASS}'" />

            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}"><fmt:message key="list"/></a></li>
                <c:if test="${URI_METHOD.equals('edit')}">
                    <li><a href="/${URI_CLASS}/edit/${sessionItem.oid}"><fmt:message key="edit"/></a></li>
<%--                    <li><a href="/${URI_CLASS}/detail/${sessionItem.oid}"><fmt:message key="detail"/></a></li>--%>
                    <li><a href="/${URI_CLASS}/remove/${sessionItem.oid}"><fmt:message key="remove"/></a></li>
                    <%--                <li><a href="${CONTEXT}/session?board_oid=${sessionItem.oid}"><fmt:message key="board-periods" /></i></a></li>--%>
                </c:if>
            </ul>

        </main>


    </div>
</div>
<%@ include file="../board/ajax.period-index.jsp" %>
<%@ include file="../board/ajax.period-js.jsp" %>

<%@ include file="../includes/footer.jsp" %>