package iBook.dao;

import iBook.domain.WishBook;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: anna.manukyan
 * Date: 6/12/12
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WishBookDao {

    public void saveWishBook(WishBook books);

    public List<WishBook> getWishList();
}
