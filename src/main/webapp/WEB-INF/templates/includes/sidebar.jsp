<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<nav class="col-md-2 d-none d-md-block bg-light sidebar rtl">
    <div class="sidebar-sticky">
<%--
        <h6 class="sidebar-heading d-flex px-3 mb-2 text-muted">
            <i class="fas fa-angle-down m-1"></i>
            <span><fmt:message key="base-information"/></span>
        </h6>
--%>
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/lookup')}">active</c:if>"
                   href="${CONTEXT}/lookup">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="lookups"/> <c:if test="${fn:startsWith(URI, '/lookup')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/user')}">active</c:if>" href="${CONTEXT}/user">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="users"/> <c:if test="${fn:startsWith(URI, '/user')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/role')}">active</c:if>" href="${CONTEXT}/role">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="roles"/> <c:if test="${fn:startsWith(URI, '/role')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/board')}">active</c:if>" href="${CONTEXT}/board">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="boards"/> <c:if test="${fn:startsWith(URI, '/board')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/contact')}">active</c:if>"
                   href="${CONTEXT}/contact">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="contacts"/> <c:if test="${fn:startsWith(URI, '/contact')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/parliament-member')}">active</c:if>"
                   href="${CONTEXT}/parliament-member">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="parliament-members"/> <c:if
                        test="${fn:startsWith(URI, '/parliament-member')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/misc-report')}">active</c:if>"
                   href="${CONTEXT}/misc-report">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="misc-report"/> <c:if
                        test="${fn:startsWith(URI, '/misc-report')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
            <%--
                        <c:if test="${ONLINE_USER.uploadPermission}">
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${fn:contains(URI, '/file/upload')}">active</c:if>"
                                   href="${CONTEXT}/file/upload">
                                    <span data-feather="upload-cloud"></span>
                                    <fmt:message key="upload"/> <c:if test="${fn:contains(URI, '/file/upload')}"><span
                                        class="sr-only">(current)</span></c:if>
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${ONLINE_USER.role eq 'MANAGER'}">
                            <li class="nav-item">
                                <a class="nav-link <c:if test="${fn:contains(URI, '/management')}">active</c:if>"
                                   href="<c:url value="/management"/>">
                                    <span data-feather="layers"></span>
                                    <fmt:message key="waiting_list"/> <c:if test="${fn:contains(URI, '/management')}"><span
                                        class="sr-only">(current)</span></c:if>

                                    <span class="badge badge-dark" id="waiting-count"></span>
                                </a>
                            </li>
                        </c:if>
            --%>
        </ul>
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link <c:if test="${fn:startsWith(URI, '/session')}">active</c:if>"
                   href="${CONTEXT}/session">
                    <i class="fas fa-angle-left m-1"></i>
                    <fmt:message key="sessions"/> <c:if test="${fn:startsWith(URI, '/session')}"><span class="sr-only">(current)</span></c:if>
                </a>
            </li>
        </ul>
    </div>
</nav>
