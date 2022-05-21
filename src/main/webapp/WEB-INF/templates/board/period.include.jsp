<div class="card mb-3">
    <div class="row no-gutters">
        <div>
            <div class="card-body">
                <label>#: ${boardPeriodItem.oid}</label>
                <a href="${CONTEXT}/board-period/edit/${boardPeriodItem.oid}?board_oid=${boardPeriodItem.fkBoardOid}">
                    <h5 class="card-title">
                        <fmt:message key="lu_period_no"/> ${boardPeriodItem.luPeriodNoTitle} <%--${boardItem.family}--%>
                    </h5>
                </a>
<%--
                <p class="card-text">
                    <small class="text-muted"><strong><fmt:message
                            key="fk_chairman_oid"/>: </strong>${boardItem.fkChairmanOidTitle}</small>&nbsp;
                    <small class="text-muted"><strong><fmt:message
                            key="fk_secretary_oid"/>: </strong>${boardItem.fkSecretaryOidTitle}</small>&nbsp;
                    <small class="text-muted"><strong><fmt:message key="phone"/>: </strong>${boardItem.phone}</small>&nbsp;
                    <small class="text-muted"><strong><fmt:message key="fax"/>: </strong>${boardItem.fax}</small>&nbsp;
                    <small class="text-muted"><strong><fmt:message key="email"/>: </strong>${boardItem.email}</small>&nbsp;

                </p>
--%>
                <%--                <p class="card-text"><small class="text-muted"></small></p>--%>
            </div>
        </div>
    </div>
</div>
