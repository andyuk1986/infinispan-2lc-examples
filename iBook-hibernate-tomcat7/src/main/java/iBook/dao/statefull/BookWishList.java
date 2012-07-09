package iBook.dao.statefull;

import iBook.domain.Book;
import iBook.domain.User;
import iBook.domain.WishBook;

import java.util.List;

import javax.ejb.Remove;

/**
 * Wish list statefull bean, which processes user's book wish list.
 */
public interface BookWishList {

    public void init(User user);

    /**
     * Adds the given book to the user's wish list. If the book already is in the wishlist, then it informs the FE.
     * @param book      the book to add to wish list.
     * @return          <code>true</code>, if the book is added, otherwise <code>false</code>.
     */
	public boolean addToWishList(final Book book);

    /**
     * Returns the user's wish list.
     * @return      the wish list of the user.
     */
	public List<WishBook> getWishList();

    /**
     * Check out of the wish list, the simulated payment is done and the order is processed. The according data
     * is written to DB.
     */
    public void checkout();

    /**
     * Removes the wishlist of the user on logout.
     */
    @Remove public void finishWishList();
}
