<spring:eval expression="@environment.getProperty('paging.page-size')" var="pageSize" />
<c:if test="${not empty paging.rowsCount}">

    <c:set var="pagingPrevious" value="${(paging.pageNo-1 > 0? paging.pageNo-1: 0)}" />
    <c:set var="pagingFirst" value="${(paging.pageNo-4 <= 0? 1: paging.pageNo-4)}" />
    <c:set var="pagingEnd" value="${(paging.pageNo+4 >= paging.pageCount? paging.pageCount: paging.pageNo+4)}" />
    <c:set var="pagingNext" value="${(paging.pageNo+1 <= paging.pageCount? paging.pageNo+1: 0)}" />

    <div class="row">
        <div class="col-md-2"><small class="mt-2 dib"><fmt:message key="record_count"/> : ${paging.rowsCount}</small></div>
        <div class="col-md-9">
            <nav aria-label="Page navigation example">
                <ul class="pagination pagination-sm">
                    <c:if test="${pagingFirst ne pagingEnd or paging.rowsCount lt pageSize}">
                        <c:choose>
                            <c:when test="${pagingPrevious ge 1}">
                                <li class="page-item"><a class="page-link" href="?page=${paging.pageNo-1}"><fmt:message key="previous"/></a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"><fmt:message key="previous"/></a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:forEach begin="${pagingFirst}" end="${pagingEnd}" varStatus="loop">
                            <li
                                <c:choose>
                                    <c:when  test="${loop.index eq paging.pageNo}">
                                        class="page-item active" aria-current="page">
                                        <a class="page-link" href="#">${loop.index} <span class="sr-only">(current)</span></a>
                                    </c:when>
                                    <c:otherwise>
                                        class="page-item">
                                        <a class="page-link" href="?page=${loop.index}<c:if test="${not empty qsWithoutPageNo}">&${qsWithoutPageNo}</c:if>">${loop.index}</a>
                                    </c:otherwise>
                                </c:choose>

                            </li>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${pagingNext gt 0}">
                                <li class="page-item"><a class="page-link" href="?page=${pagingNext}<c:if test="${not empty qsWithoutPageNo}">&${qsWithoutPageNo}</c:if>"><fmt:message key="next"/></a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"><fmt:message key="next"/></a></li>
                            </c:otherwise>
                        </c:choose>

                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</c:if>