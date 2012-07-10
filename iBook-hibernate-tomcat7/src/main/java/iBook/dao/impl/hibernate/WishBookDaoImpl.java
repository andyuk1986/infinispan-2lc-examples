package iBook.dao.impl.hibernate;

import iBook.dao.WishBookDao;
import iBook.domain.WishBook;
import iBook.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.transaction.UserTransaction;
import java.util.List;

/**
 * WishList Dao implementation.
 */
public class WishBookDaoImpl implements WishBookDao {

    @Override
    public void saveWishBook(WishBook book) {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

        session.merge(book);

        Utils.getInstance().commitTransaction(tr);
    }

    @Override
    public List<WishBook> getWishList() {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

        Query query = session.getNamedQuery("listWishList");

        List<WishBook> books = query.list();

        Utils.getInstance().commitTransaction(tr);
        return books;
    }

}
