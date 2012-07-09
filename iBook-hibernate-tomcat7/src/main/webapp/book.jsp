<jsp:useBean id="bookPage" class="iBook.web.BookPage" />
<%@ page import="iBook.domain.Book"%>
<%@ page import="iBook.web.BuyPage"%>
<%@ page import="iBook.utils.Utils" %>
<%@ page import="iBook.web.BookPage" %>
<%@ page import="iBook.domain.Authors2Books" %>
<%@ page import="java.util.Set" %>
<%
    bookPage.init(request, response, session);

    Book currentBook = Utils.getInstance().getBookById(bookPage.getIntParameter(BookPage.PARAM_BOOK_ID));
%>
<jsp:include page="templates/header.jsp" />

<div id="templatemo_content">

    <jsp:include page="templates/l_menu.jsp" />

    <div id="templatemo_content_right">
        <a href="javascript: history.back()" style="margin-left: 620px;">Back</a>
        <%
            if (currentBook != null) {
                Set<Authors2Books> authors2BooksSet = currentBook.getAuthor();
                StringBuffer authors = new StringBuffer();
                for(Authors2Books authors2Books : authors2BooksSet) {
                    authors.append(authors2Books.getAuthor().getAuthorName()).append(",");
                }
                authors.deleteCharAt(authors.length() - 1);
        %>
        <h1><%=currentBook.getTitle()%>
			<span>(by <%= authors.toString()%>)
			</span>
        </h1>
        <div class="image_panel">
            <img src="images/books/<%=currentBook.getPhotoUrl()%>"
                 alt="<%=currentBook.getTitle()%>" width="100" height="150" />
        </div>
        <%-- <h2><%= currentBook.getDescription() %></h2> --%>
        <ul>
            <li>By: <a href="#"><%=authors.toString()%></a></li>
            <li>Publication Date: <%=currentBook.getPublishDate()%></li>
            <li>Pages: 498</li>
        </ul>

        <p><%=currentBook.getDescription()%></p>

        <div class="cleaner_with_height">&nbsp;</div>
        <div class="buy_now_button" style="float: right;">
            <a href="buy.jsp?<%= BuyPage.PARAM_BOOK_ID %>=<%=currentBook.getId()%>">Add To Card</a>
        </div>
        <%
        } else {
        %>
        <div style="height: 210px;"><h1>Sorry, this book was not found in our library!</h1></div>
        <%
            }
        %>
        <a href="index.jsp"><img src="images/templatemo_ads.jpg"
                                 alt="css template ad" /></a>

    </div>
    <!-- end of content right -->

    <div class="cleaner_with_height">&nbsp;</div>
</div>

<jsp:include page="templates/footer.jsp" />