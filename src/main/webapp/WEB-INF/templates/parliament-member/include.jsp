<div class="card mb-3">
    <div class="row no-gutters">
        <div class="mw-100">
            <img src="/uploads/${pmItem.filename}" class="img-thumbnail mw-100"
                 alt="${pmItem.name} ${pmItem.family}" title="${pmItem.name} ${pmItem.family}"/>
        </div>
        <div>
            <div class="card-body">
                <label>#: ${pmItem.oid}</label>
                <a href="${CONTEXT}/parliament-member/edit/${pmItem.oid}">
                <h5 class="card-title">
                    ${pmItem.name} ${pmItem.family}
                </h5>
                </a>
                <p class="card-text">
                    <fmt:message key="father_name"/>: ${pmItem.fatherName}<br />
                    <fmt:message key="birth_year"/>: ${cf:digit2persian(pmItem.birthYear)}
                </p>
<%--                <p class="card-text"><small class="text-muted"></small></p>--%>
            </div>
        </div>
    </div>
</div>
