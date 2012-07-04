<jsp:useBean id="logoutPage" class="iBook.web.LogoutPage" />
<%
logoutPage.init(request, response, session);
logoutPage.logout();

%>