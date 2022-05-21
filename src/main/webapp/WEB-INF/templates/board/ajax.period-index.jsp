<div class="modal fade rtl" id="bpModalScrollable" tabindex="-1" role="dialog"
     aria-labelledby="bpModalScrollableTitle" aria-hidden="true">
    <div class="modal-dialog  modal-xl modal-dialog-scrollable" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"
                    id="bpModalScrollableTitle"><fmt:message key="do_select" /></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form class="form ajax-bp-search-form" action="">
                    <div class="row form-group">

                        <label class="col-form-label"><fmt:message key="keyword"/>: </label>
                        <input type="text" class="col-2 form-control" name="keyword" id="keyword" value="${param.keyword}"
                               placeholder="<fmt:message key="keyword"/>">

                        <label class="col-form-label"><fmt:message key="lu_period_no"/>: </label>
                        <select name="lu_period_no" id="lu_period_no" class="col-2 form-control custom-select">
                            <c:forEach var="rec" items="${periodItems}">
                                <c:if test="${rec.key gt 24}">
                                    <option value="${rec.key}"
                                            <c:if test="${rec.key eq param.luPeriodNo}">selected</c:if>>${rec.value}</option>
                                </c:if>
                            </c:forEach>
                        </select>

                        <div class="col-2"><input type="submit" class="btn btn-primary" value="<fmt:message key="search"/>"/></div>
                    </div>
                </form>
                <%@ include file="../includes/ajax.paging.jsp" %>
                <table class="table table-striped table-sm table-hover table-sortable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th><fmt:message key="title" /></th>
                        <th><fmt:message key="lu_period_no" /></th>
                        <th width="90"></th>
                    </tr>
                    </thead>
                    <tbody id="ajax-bp-data-table">
                    </tbody>
                </table>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <fmt:message key="close" />
                </button>
            </div>
        </div>
    </div>
</div>
