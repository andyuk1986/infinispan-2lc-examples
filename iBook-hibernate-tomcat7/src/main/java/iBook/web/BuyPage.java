package iBook.web;

import java.io.IOException;

import iBook.domain.Book;
import iBook.dao.statefull.BookWishList;
import iBook.utils.Utils;

/**
 * Bean responsible for adding book to user's wish list.
 */
public class BuyPage extends Page {
    /**
     * Parameter specifying that error occured.
     */
	public static final String PARAM_ERROR_MSG = "error";
    /**
     * Parameter specifying book ID.
     */
	public static final String PARAM_BOOK_ID = "bookId";
    /**
     * Parameter specifying that the book is added to wish list.
     */
	public static final String PARAM_CONFIRM = "conf";

    /**
     * The URL of the Checkout page.
     */
	public static final String BOOK_ADDED_URL = "card.jsp";

    /**
     * Checks whether the user is logged in, i.e. checks whether there is a specific object in the session.
     * @return      <code>true</code>, if the user is logged in.
     */
	public boolean isUserLoggedIn() {
		return session.getAttribute(LOGGED_IN_USER) != null;
	}

    /**
     * Adds the chosen book to wish list.
     * @throws IOException      if something went wrong during redirection.
     */
	public void addToWishList() throws IOException {
		Book book = Utils.getInstance().getBookById(getIntParameter(PARAM_BOOK_ID));
		if(book != null) {
			BookWishList bookWishList = Utils.getInstance().getBookWishList(session);
		    boolean isAdded = bookWishList.addToWishList(book);
            response.sendRedirect(BOOK_ADDED_URL + "?" + CardPage.PARAM_ADDED + "=" + isAdded);
		}
	}

}
