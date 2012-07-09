package iBook.web;

import java.util.List;
import iBook.domain.*;
import iBook.dao.statefull.BookWishList;
import iBook.utils.Utils;

/**
 * Page responsible for wish list page/checkout.
 */
public class CardPage extends Form {
    /**
     * Parameter which specifies that the new book is added.
     */
	public static final String PARAM_ADDED = "addedBook";

	@Override
	public void submit() throws Exception {
		if(session.getAttribute(LOGGED_IN_USER) != null) {
			BookWishList wishList = Utils.getInstance().getBookWishList(session);
			wishList.checkout();
		}
        response.sendRedirect(INDEX_URL + "?success=true");
	}

    /**
     * Returns the books in the user's wish list.
     * @return          the list of books from wish list.
     */
	public List<WishBook> getWishList() {
		BookWishList wishList = Utils.getInstance().getBookWishList(session);
		
		return wishList.getWishList();
	}

}
