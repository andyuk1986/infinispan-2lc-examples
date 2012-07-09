<jsp:useBean id="registrationPage" class="iBook.web.RegistrationPage" />
<%@ page import="iBook.web.RegistrationPage"%>
<%@ page import="java.util.List"%>
<%
	registrationPage.init(request, response, session);
	if (registrationPage.isFormSubmitted()) {
		try {
			registrationPage.submit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
%>
<jsp:include page="templates/header.jsp" />

<div id="templatemo_content">

	<jsp:include page="templates/l_menu.jsp" />

	<div id="templatemo_content_right">

		<h1>Register</h1>
		<%
			if (request.getAttribute(RegistrationPage.ERROR_MSGS) != null) {
				List<String> errorMsgs = (List<String>) request
						.getAttribute(RegistrationPage.ERROR_MSGS);
				if (!errorMsgs.isEmpty()) {
		%>
		<h2>
			<%
				for (String error : errorMsgs) {
			%>
			<p><%=error%></p>
			<%
				}
			%>
		</h2>
		<%
			}
			}
		%>
		<form method="POST" name="regForm">
			<p>
				<label for="username" style="padding-right: 20px;">UserName:
					*</label><input type="text" id="username"
					name="<%=RegistrationPage.PARAM_USER_NAME%>"
					value="<%=registrationPage
					.getParameter(RegistrationPage.PARAM_USER_NAME) != null ? registrationPage
					.getParameter(RegistrationPage.PARAM_USER_NAME) : ""%>" style="margin-left:40px;" />
			</p>
			<p>
				<label for="passsword" style="padding-right: 20px;">Password:
					*</label><input type="password" id="password"
					name="<%=RegistrationPage.PARAM_PASSWORD%>" value="" style="margin-left:40px;"/>
			</p>
			<p>
				<label for="conf_password" style="padding-right: 20px;">Confirm
					Password: *</label><input type="password" id="conf_password"
					name="<%=RegistrationPage.PARAM_CONFIRM_PASSWORD%>" value="" />
			</p>
			<p>
				<label for="email" style="padding-right: 20px;">Email: *</label><input
					type="text" id="email" name="<%=RegistrationPage.PARAM_EMAIL%>" style="margin-left:66px;"
					value="<%= registrationPage.getParameter(RegistrationPage.PARAM_EMAIL) != null ? registrationPage.getParameter(RegistrationPage.PARAM_EMAIL) : ""%>"
				/>
			</p>
			<input type="hidden" name="<%=RegistrationPage.PARAM_IS_SUBMITTED%>"
				value="true" />

			<div class="buy_now_button" style="float: right; padding-left: 10px;">
				<a href="javascript: document.regForm.submit()">Register</a>
			</div>
		</form>

		<div class="cleaner_with_height">&nbsp;</div>

		<a href="index.jsp"><img src="images/templatemo_ads.jpg"
			alt="css template ad" /></a>

	</div>
	<!-- end of content right -->

	<div class="cleaner_with_height">&nbsp;</div>
</div>
<!-- end of content -->

<jsp:include page="templates/footer.jsp" />