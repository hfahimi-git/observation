<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <div class="row">
                <div class="col-lg-8"><%@ include file="include.jsp" %></div>
                <div class="col-lg-4 d-flex"><%@ include file="period.include.jsp" %></div>
            </div>

                <nav>
                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="nav-commissions-tab" data-toggle="tab" href="#nav-commissions" role="tab" aria-controls="nav-commissions" aria-selected="true"><fmt:message key="board-commissions" /></a>
                        <a class="nav-item nav-link" id="nav-experts-tab" data-toggle="tab" href="#nav-experts" role="tab" aria-controls="nav-experts" aria-selected="false"><fmt:message key="board-experts" /></a>
                        <a class="nav-item nav-link" id="nav-observers-tab" data-toggle="tab" href="#nav-observers" role="tab" aria-controls="nav-observers" aria-selected="false"><fmt:message key="board-observers" /></a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-commissions" role="tabpanel" aria-labelledby="nav-commissions-tab">
                        <%@ include file="commission.include.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-experts" role="tabpanel" aria-labelledby="nav-experts-tab">
                        <%@ include file="expert.include.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-observers" role="tabpanel" aria-labelledby="nav-observers-tab">
                        <%@ include file="observer.include.jsp" %>
                    </div>
                </div>


        </main>


    </div>
</div>

<%@ include file="../includes/footer.jsp" %>