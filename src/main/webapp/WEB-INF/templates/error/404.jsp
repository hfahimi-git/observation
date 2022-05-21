<%@ include file="../includes/init.jsp" %>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid rtl">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <h2><fmt:message key="request_not_found"/> </h2>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong><fmt:message key="error"/>! </strong><fmt:message key="request_file_not_found"/>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                </button>
            </div>


        </main>

    </div>
</div>
<%@ include file="../includes/footer.jsp" %>