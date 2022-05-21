<div class="card mb-3">
    <div class="row no-gutters">
        <div>
            <div class="card-body label-as-block">
                <label>#: ${sessionItem.oid}</label>
                <label><strong><a href="${CONTEXT}/session/edit/${sessionItem.oid}">
                    ${sessionItem.fkBoardPeriodOidTitle}
                </a></strong></label>
                <label><fmt:message key="no"/>: </strong>${sessionItem.no}</label>
                <label><fmt:message key="date"/>: </strong>${sessionItem.date}</label>
            </div>
        </div>
    </div>
</div>
