<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <%@ include file="include.jsp" %>
            <form:form action="?board_oid=${boardItem.oid}" method="post" modelAttribute="boardPeriodItem"
                       cssClass="col-lg-10 float-right">

                <div class="form-row mb-3">
                    <div class="col-md">
                        <label>#: ${boardPeriodItem.oid}</label>
                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-2">
                        <label for="lu_period_no"><fmt:message key="lu_period_no"/></label>
                        <form:select path="luPeriodNo" id="lu_period_no" cssClass="form-control custom-select"
                                     cssErrorClass="form-control custom-select is-invalid" disabled="true">
                            <c:forEach var="rec" items="${periodItems}">
                                <c:if test="${rec.key gt 24}">
                                <option value="${rec.key}"
                                        <c:if test="${rec.key eq boardPeriodItem.luPeriodNo}">selected</c:if>>${rec.value}</option>
                                </c:if>
                            </c:forEach>

                        </form:select>
                        <form:errors path="luPeriodNo" cssClass="invalid-feedback"/>

                    </div>
                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <button class="btn btn-danger" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="submit" /></button>
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />" onclick="window.location='/${URI_CLASS}?board_oid=${boardItem.oid}'" />

                <input type="hidden" name="fkBoardOid" value="${boardItem.oid}">
            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="${CONTEXT}/${URI_CLASS}?board_oid=${boardItem.oid}"><fmt:message key="list" /></a></li>
                <li><a href="${CONTEXT}/${URI_CLASS}/edit/${boardPeriodItem.oid}?board_oid=${boardItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="${CONTEXT}/${URI_CLASS}/remove/${boardPeriodItem.oid}?board_oid=${boardItem.oid}"><fmt:message key="remove" /></a></li>
                <li><a href="${CONTEXT}/${URI_CLASS}/setting/${boardPeriodItem.oid}?board_oid=${boardItem.oid}"><fmt:message key="setting" /></a></li>
            </ul>

        </main>


    </div>
</div>

<%@ include file="../includes/footer.jsp" %>