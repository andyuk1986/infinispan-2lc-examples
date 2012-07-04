package iBook.ejb.stateless.remote;

import iBook.domain.User;
import iBook.domain.WishBook;

import javax.ejb.Remote;
import java.util.List;

/**
 * Remote interface for WishBook bean. All DB actions related to wishbooks table will be performed here.
 */
@Remote
public interface WishBookRemote {
    /**
     * Saves the given wish book list to database.
     * @param books     the wish book list.
     */
    public void saveWishBook(WishBook books);

    /**
     * Returns the specified wish list.
     * @return      the wish list.
     */
    public List<WishBook> getWishList();

    /**
     * Deletes the wish list of the specified user.
     * @param user      the wish list to specify.
     */
    public void deleteWishList(final User user);
}
