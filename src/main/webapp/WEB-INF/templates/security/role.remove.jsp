<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <form:form action="" method="post" modelAttribute="roleItem" cssClass="col-lg-10 float-right">
<%--------------------------------------------------------------------------------------------------------------------%>
                <div class="form-row mb-3">
                    <div class="col-md-3">
                        <label for="title"><fmt:message key="title"/></label>
                        <input type="text" name="title" value="${roleItem.title}" id="title" class="form-control"
                               readonly="readonly" />
                    </div>

                </div>
<%--------------------------------------------------------------------------------------------------------------------%>
                <button class="btn btn-danger" type="submit"
                        onclick="return confirm('<fmt:message key="are_you_sure" />')"><fmt:message key="remove" /></button>
                <input type="button" class="btn btn-secondary" value="<fmt:message key="cancel" />" onclick="window.location='/${URI_CLASS}'" />


            </form:form>

            <ul class="col-lg-2 col-md-2 internal-menu internal-menu-default float-right">
                <li><a href="/${URI_CLASS}"><fmt:message key="list" /></a></li>
                <li><a href="/${URI_CLASS}/edit/${roleItem.oid}"><fmt:message key="edit" /></a></li>
                <li><a href="/${URI_CLASS}/remove/${roleItem.oid}"><fmt:message key="remove" /></a></li>
            </ul>

        </main>


    </div>
</div>

<%@ include file="../includes/footer.jsp" %>