<jsp:useBean id="cardPage" class="iBook.web.CardPage" />
<%@ page import="iBook.domain.Book"%>
<%@ page import="iBook.web.CardPage"%>
<%@ page import="java.util.List"%>
<%@ page import="iBook.domain.Authors2Books" %>
<%@ page import="java.util.Set" %>
<%@ page import="iBook.domain.WishBook" %>
<%
    cardPage.init(request, response, session);
    if (cardPage.isFormSubmitted()) {
        cardPage.submit();
    }
%>
<jsp:include page="templates/header.jsp" />

<div id="templatemo_content">

    <jsp:include page="templates/l_menu.jsp" />

    <div id="templatemo_content_right">

        <%
            if (cardPage.getParameter(CardPage.PARAM_ADDED) != null) {
                if(cardPage.getParameter(CardPage.PARAM_ADDED).equals("true")) {
        %>
        <h1>Your wish list filled in with one more book!</h1>
        <%
        } else {
        %>
        <h1>Your have already this book in your wish list!</h1>
        <%
                }
            }

            List<WishBook> wishList = cardPage.getWishList();
            if (wishList == null || wishList.isEmpty()) {
        %>
        <h1>
            Your wish list is empty. Start <a href="<%=CardPage.INDEX_URL%>">Reading!</a>
        </h1>
        <%
        } else {
        %>
        <form method="POST" name="payForm">
            <table cellpadding="10" border="0">

                <%
                    double sum = 0;
                    Book book = null;
                    for (WishBook wishBook : wishList) {
                        book = wishBook.getBook();

                        sum += book.getPrice();

                        Set<Authors2Books> authors2BooksSet = book.getAuthor();
                        StringBuffer authors = new StringBuffer();
                        for(Authors2Books authors2Books : authors2BooksSet) {
                            authors.append(authors2Books.getAuthor().getAuthorName()).append(",");
                        }
                        authors.deleteCharAt(authors.length() - 1);
                %>
                <tr>
                    <td valign="center"><img src="images/books/<%=book.getPhotoUrl()%>"
                                             height="40px"></td>
                    <td valign="center"><%=book.getTitle()%> (by <%=authors.toString()%>)</td>
                    <td valign="center"><%=book.getDescription() != null
                            && book.getDescription().length() > 50 ? book
                            .getDescription().substring(0, 50) : book
                            .getDescription()%> ...</td>
                    <td valign="center"><font size="3px" color="#CBC750" style="font-weight: bold;"><%=book.getPrice()%>$</font></td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td valign="center" colspan="3" align="right">TOTAL:</td>
                    <td valign="center"><font size="3px" color="#CBC750" style="font-weight: bold;"><%=sum%>$</font></td>
                </tr>
            </table>
            <input type="hidden" name="<%=CardPage.PARAM_IS_SUBMITTED%>"
                   value="true" />
            <div class="buy_now_button" style="float: right;">
                <a href="javascript:document.payForm.submit()">Check-Out</a>
            </div>
        </form>
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