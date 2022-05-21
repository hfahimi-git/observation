<%--<%@ page import="ir.parliran.App" %>--%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="ir.parliran.Application" %>
<%@ page import="java.nio.charset.StandardCharsets" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Observation System">
    <meta name="author" content="aGZhaGltaS5jb20=">
    <meta name="version" content="<%=Application.VERSION%>">
    <link rel="icon" href="${CONTEXT}/favicon.ico">
    <title>${pageName} - <fmt:message key="system_title"/></title>
    <link rel="stylesheet" href="${CONTEXT}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${CONTEXT}/assets/css/bootstrap-rtl.min.css">
    <link rel="stylesheet" href="${CONTEXT}/assets/css/sahel-fonts.css">
    <link rel="stylesheet" href="${CONTEXT}/assets/fontawesome-free-5.12.0-web/css/all.min.css">
    <link rel="stylesheet" href="${CONTEXT}/assets/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="${CONTEXT}/assets/css/main.css?<%=URLEncoder.encode(Application.VERSION, StandardCharsets.UTF_8)%>">

    <script>const CONTEXT = "${CONTEXT}";</script>
    <script src="${CONTEXT}/assets/js/jquery-3.4.1.min.js"></script>
    <script>window.jQuery || document.write('<script src="${CONTEXT}/assets/js/jquery-3.4.1.min.js"><\/script>')</script>
    <script src="${CONTEXT}/assets/js/bootstrap.bundle.min.js"></script>
    <script src="${CONTEXT}/assets/js/sorttable.js"></script>
    <script src="${CONTEXT}/assets/js/jquery.sha256.min.js"></script>
    <script src="${CONTEXT}/assets/fontawesome-free-5.12.0-web/js/all.min.js"></script>
    <script src="${CONTEXT}/assets/js/bootstrap-datepicker.min.js"></script>
    <script src="${CONTEXT}/assets/js/bootstrap-datepicker.fa.min.js"></script>
</head>
<body>

<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow rtl">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="${CONTEXT}/file"><fmt:message key="system_title"/></a>
    <%--input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search"--%>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <c:choose>
                <c:when test="${ONLINE_USER.getPrincipal() ne 'anonymousUser'}">
                    <a class="nav-link" href="${CONTEXT}/logout"><fmt:message
                            key="logout"/> (${ONLINE_USER.getName()})</a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="#"><fmt:message key="login"/></a>
                </c:otherwise>
            </c:choose>
        </li>
    </ul>
</nav>