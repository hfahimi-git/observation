<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="login"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <main role="main" class="col-md-12 col-lg-12 px-4">
            <h2 class="rtl text-center">${pageName}</h2>

            <form:form action="" method="post" modelAttribute="loginItem">
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                    <div class="form-row mb-3 justify-content-center ">
                        <div class="col-md-4 rtl">
                            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                <strong><fmt:message key="error_in_login"/> : </strong>
                                <br/>
                                <i>${SPRING_SECURITY_LAST_EXCEPTION.message}</i>
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </c:if>

                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3 justify-content-center ">
                    <div class="col-md-2 rtl">
                        <label for="username"><fmt:message key="username"/> (<fmt:message key="national_code"/>)</label>
                        <input type="text" name="username" value="${loginItem.username}" id="username"
                               class="ltr form-control"/>
                        <form:errors path="username" cssClass="invalid-feedback"/>
                    </div>
                </div>

                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3 justify-content-center">
                    <div class="col-md-2 rtl">
                        <label for="password"><fmt:message key="password"/></label>
                        <form:input type="password" path="password" id="password" cssClass="ltr form-control"
                                    cssErrorClass="ltr form-control is-invalid"/>
                        <form:errors path="password" cssClass="invalid-feedback"/>
                    </div>
                </div>

                <%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3 justify-content-center">
                    <button class="btn btn-primary" type="submit"><fmt:message key="login"/></button>
                </div>

            </form:form>


        </main>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>