package iBook.dao.impl.hibernate;

import iBook.dao.WishBookDao;
import iBook.domain.WishBook;
import iBook.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * WishList Dao implementation.
 */
public class WishBookDaoImpl implements WishBookDao {

    @Override
    public void saveWishBook(WishBook book) {
        Session session = Utils.getInstance().openTransaction();

        session.merge(book);

        Utils.getInstance().commitTransaction(session);
    }

    @Override
    public List<WishBook> getWishList() {
        Session session = Utils.getInstance().openTransaction();

        Query query = session.getNamedQuery("listWishList");

        List<WishBook> books = query.list();

        Utils.getInstance().commitTransaction(session);
        return books;
    }

}
