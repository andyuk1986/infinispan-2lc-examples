<jsp:useBean id="leftMenuHandler" class="iBook.web.LeftMenuHandler" />
<%@ page import="java.util.List"%>
<%@ page import="iBook.domain.Category"%>
<%@ page import="iBook.domain.Book"%>
<%@ page import="iBook.web.BookPage"%>
<div id="templatemo_content_left">
	<div class="templatemo_content_left_section">
		<h1>Categories</h1>
		<ul>
			<%
				List<Category> categories = leftMenuHandler
						.getAllAvailableCategories();
				for (Category category : categories) {
			%>
			<li><a href="listBooks.jsp?<%= BookPage.PARAM_CATEGORY_ID %>=<%=category.getId()%>">
			<%=category.getCategoryName()%>
			</a></li>
			<%
				}
			%>
		</ul>
	</div>
	<div class="templatemo_content_left_section">
		<h1>Bestsellers</h1>
		<ul>
			<%
				List<Book> bestsellers = leftMenuHandler.getBestsellers();
				if (bestsellers != null && !bestsellers.isEmpty()) {
					for (Book book : bestsellers) {
			%>
			<li><a href="book.jsp?<%= BookPage.PARAM_BOOK_ID %>=<%=book.getId()%>"><%=book.getTitle()%></a></li>
			<%
				}
				} else {
					%>
					<li>There are no bestsellers at the moment.</li>
					<%
					
				}
			%>
		</ul>
	</div>
<!-- 
	<div class="templatemo_content_left_section">
	</div> -->
</div>
<!-- end of content left -->