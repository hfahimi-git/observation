<%@ include file="../includes/init.jsp" %>
<c:set var="pageName" scope="page"><fmt:message key="${URI_CLASS}.${URI_METHOD}"/></c:set>
<%@ include file="../includes/header.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../includes/sidebar.jsp" %>
        <main role="main" class="col-md-10 col-lg-10 px-4 rtl">
            <h2>${pageName}</h2>

            <div class="row">
                <div class="col-lg-12"><%@ include file="include.jsp" %></div>
            </div>
                <nav>
                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                        <a class="nav-item nav-link active" id="nav-invitation-tab" data-toggle="tab" href="#nav-invitation"
                           role="tab" aria-controls="nav-invitation" aria-selected="true"><fmt:message key="session-invitation" /></a>
                        <a class="nav-item nav-link" id="nav-experts-tab" data-toggle="tab" href="#nav-agenda" role="tab"
                           aria-controls="nav-agenda" aria-selected="false"><fmt:message key="session-agenda" /></a>
                        <a class="nav-item nav-link" id="nav-bjr-tab" data-toggle="tab" href="#nav-bjr" role="tab"
                           aria-controls="nav-bjr" aria-selected="false"><fmt:message key="board-justification-report" /></a>
                        <a class="nav-item nav-link" id="nav-djr-tab" data-toggle="tab" href="#nav-djr" role="tab"
                           aria-controls="nav-djr" aria-selected="false"><fmt:message key="deputy-justification-report" /></a>
                        <a class="nav-item nav-link" id="nav-observer-tab" data-toggle="tab" href="#nav-observer" role="tab"
                           aria-controls="nav-observer" aria-selected="false"><fmt:message key="observer-report" /></a>
                        <a class="nav-item nav-link" id="nav-board-tab" data-toggle="tab" href="#nav-board" role="tab"
                           aria-controls="nav-board" aria-selected="false"><fmt:message key="board-report" /></a>
                        <a class="nav-item nav-link" id="nav-commission-tab" data-toggle="tab" href="#nav-commission" role="tab"
                           aria-controls="nav-commission" aria-selected="false"><fmt:message key="commission-report" /></a>
                        <a class="nav-item nav-link" id="nav-deputy-analytical-tab" data-toggle="tab" href="#nav-deputy-analytical" role="tab"
                           aria-controls="nav-deputy-analytical" aria-selected="false"><fmt:message key="deputy-analytical" /></a>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div class="tab-pane fade show active" id="nav-invitation" role="tabpanel" aria-labelledby="nav-invitation-tab">
                        <%@ include file="invitation.include.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-agenda" role="tabpanel" aria-labelledby="nav-agenda-tab">
                        <%@ include file="agenda.include.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-bjr" role="tabpanel" aria-labelledby="nav-bjr-tab">
                        <%@ include file="board-justification-report.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-djr" role="tabpanel" aria-labelledby="nav-djr-tab">
                        <%@ include file="deputy-justification-report.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-observer" role="tabpanel" aria-labelledby="nav-observer-tab">
                        <%@ include file="observer-report.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-board" role="tabpanel" aria-labelledby="nav-board-tab">
                        <%@ include file="board-report.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-commission" role="tabpanel" aria-labelledby="nav-commission-tab">
                        <%@ include file="commission-report.jsp" %>
                    </div>
                    <div class="tab-pane fade" id="nav-deputy-analytical" role="tabpanel" aria-labelledby="nav-deputy-analytical-tab">
                        <%@ include file="deputy-analytical-report.jsp" %>
                    </div>
                </div>

        </main>


    </div>
</div>

<%@ include file="../includes/footer.jsp" %>