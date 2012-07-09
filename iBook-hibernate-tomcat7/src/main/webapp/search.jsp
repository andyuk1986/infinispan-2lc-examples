<jsp:useBean id="searchPage" class="iBook.web.SearchPage" />
<%@ page import="iBook.domain.Book"%>
<%@ page import="iBook.web.SearchPage"%>
<%@ page import="java.util.*"%>
<%@ page import="iBook.domain.*"%>
<%
	searchPage.init(request, response, session);
	if (searchPage.isFormSubmitted()) {
		searchPage.submit();
	}
%>
<jsp:include page="templates/header.jsp" />

<div id="templatemo_content">

	<jsp:include page="templates/l_menu.jsp" />

	<div id="templatemo_content_right">
		<h1>Search for Books</h1>
		<form method="POST">
			<div>
				<label for="<%=SearchPage.PARAM_TITLE%>">Title: (could be a
					pattern)</label> <input type="text" name="<%=SearchPage.PARAM_TITLE%>"
					value="" style="margin-left: 44px; margin-bottom: 10px;">
			</div>
			<div>
				<label for="<%=SearchPage.PARAM_AUTHOR%>">Author: (could be
					a pattern)</label> <input type="text" name="<%=SearchPage.PARAM_AUTHOR%>"
					value="" style="margin-left: 30px; margin-bottom: 10px;">
			</div>
			<div>
				<label for="<%=SearchPage.PARAM_CATEGORY%>">Category:</label> <select
					name="<%=SearchPage.PARAM_CATEGORY%>"
					style="margin-left: 117px; margin-bottom: 10px;">
					<option value="<%= SearchPage.NO_CATEGORY%>">Select Category</option>
					<%
						List<Category> categories = searchPage.getCategories();
						for (Category category : categories) {
					%>
					<option value="<%=category.getId()%>"><%=category.getCategoryName()%></option>
					<%
						}
					%>
				</select>
			</div>
			<div>
				<label for="<%=SearchPage.PARAM_BESTSELLER%>">Bestseller: </label> <input
					type="checkbox" name="<%=SearchPage.PARAM_BESTSELLER%>" value=""
					style="margin-left: 114px; margin-bottom: 10px;">
			</div>
			<input type="hidden" name="<%=SearchPage.PARAM_IS_SUBMITTED%>"
				value="true" /> <input type="submit" value="Search" />
		</form>
	</div>
	<!-- end of content right -->

	<div class="cleaner_with_height">&nbsp;</div>
</div>

<jsp:include page="templates/footer.jsp" />