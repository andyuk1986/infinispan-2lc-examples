<jsp:useBean id="loginPage" class="iBook.web.LoginPage" />
<%@ page import="iBook.domain.Book"%>
<%@ page import="iBook.web.BookPage"%>
<%@ page import="iBook.web.BuyPage"%>
<%@ page import="java.util.List"%>
<%@ page import="iBook.domain.Authors2Books" %>
<%@ page import="java.util.Set" %>

<%
	loginPage.init(request, response, session);
	if (loginPage.isFormSubmitted()) {
		loginPage.submit();
	}
%>
<jsp:include page="templates/header.jsp" />

<div id="templatemo_content">

	<jsp:include page="templates/l_menu.jsp" />

	<div id="templatemo_content_right">
        <%
        if(loginPage.getParameter("success") != null) {
            %>
          <h1>Congratulations! Your order is processed properly!</h1>
        <%
        }
        %>
		<%
			List<Book> coverBooks = loginPage.getCoverBooks();
			for (int i = 1; i <= coverBooks.size(); i++) {
				Book book = coverBooks.get(i - 1);
                Set<Authors2Books> authors2BooksSet = book.getAuthor();
                StringBuffer authors = new StringBuffer();
                for(Authors2Books authors2Books : authors2BooksSet) {
                    authors.append(authors2Books.getAuthor().getAuthorName()).append(",");
                }
                authors.deleteCharAt(authors.length() - 1);
		%>
		<div class="templatemo_product_box">
			<h1><%=((book.getTitle() != null && book.getTitle().length() > 15) ? book
						.getTitle().substring(0, 15) : book.getTitle())%>...
				<span>(by <%=authors.toString()%>)
				</span>
			</h1>
			<img src="images/books/<%=book.getPhotoUrl()%>"
				alt="<%=book.getTitle()%>" width="100px" height="150px" />
			<div class="product_info">
				<p><%=book.getDescription() != null
						&& book.getDescription().length() > 50 ? book
						.getDescription().substring(0, 50) : book
						.getDescription()%>
					...
				</p>
				<h3>
					$<%=book.getPrice()%></h3>
				<div class="buy_now_button">
					<a href="buy.jsp?<%= BuyPage.PARAM_BOOK_ID%>=<%=book.getId()%>">Add To Card</a>
				</div>
				<div class="detail_button">
					<a href="book.jsp?<%= BookPage.PARAM_BOOK_ID%>=<%=book.getId()%>">Detail</a>
				</div>
			</div>
			<div class="cleaner">&nbsp;</div>
		</div>
		<%
			if (i != 0 && i % 2 == 0) {
		%>
		<div class="cleaner_with_height">&nbsp;</div>
		<%
			} else {
		%>
		<div class="cleaner_with_width">&nbsp;</div>
		<%
			}
			}
		%>
		<a href="subpage.jsp"><img src="images/templatemo_ads.jpg" alt="ads" style="padding-top: 10px;"/></a>
	</div>
	<!-- end of content right -->

	<div class="cleaner_with_height">&nbsp;</div>
</div>
<!-- end of content -->
<jsp:include page="templates/footer.jsp" />