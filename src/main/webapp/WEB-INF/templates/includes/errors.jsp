<c:set var="globalError"><form:errors path="*"/></c:set>
<c:if test="${not empty globalError}">
    <div class="alert alert-warning alert-dismissible fade show" role="alert">
        <strong><fmt:message key="error"/></strong>
        ${globalError}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
</c:if>