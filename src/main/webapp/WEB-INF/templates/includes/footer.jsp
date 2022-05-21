<%@ page import="java.time.LocalDate" %>
<script src="${CONTEXT}/assets/js/main.js<%--?<%=URLEncoder.encode(App.VERSION, "UTF-8")%>--%>"></script>
<%--
    <c:if test="${ONLINE_USER.role eq 'MANAGER'}">
    <script src="${CONTEXT}/assets/js/manager.js?<%=URLEncoder.encode(App.VERSION, "UTF-8")%>"></script>
    </c:if>
--%>
<div class="text-center">
    <small style="color: #ccc">&copy; <%=LocalDate.now().getYear()%> Planning & IT Center <%--(<%=App.VERSION%>)--%></small>
</div>
</body>
</html>
