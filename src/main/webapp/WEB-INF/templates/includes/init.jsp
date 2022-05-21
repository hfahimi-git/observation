<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cf"  uri="http://ir.parliran/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="CONTEXT" value="${pageContext.request.contextPath}" scope="request" />
<%--<c:set var="RECORDS_PER_PAGE" value="<%=App.config.recordsPerPage()%>" />--%>
<%--<c:set var="FILES_ROOT_FOLDER" value="<%=App.config.rootFolder()%>" />--%>
<%--<c:set var="DS" value="<%=App.config.ds()%>" />--%>
<c:url value="" var="REDIRECT">
    <c:param name="redirect" value="${pageContext.request.requestURL}"/>
</c:url>
<c:set var="REDIRECT" value="${REDIRECT.substring(1)}" />
<c:set var="URI" value="${pageContext.request.getAttribute('javax.servlet.forward.request_uri')}"/>

<c:set var="URI_SPLIT" value="${fn:split(URI, '/')}" />
<c:set var="URI_CLASS" value="${URI_SPLIT[0]}" />
<c:set var="URI_METHOD" value="${URI_SPLIT[1]}" />
<fmt:setLocale value="fa" />
<fmt:setBundle basename="labels_fa" />
