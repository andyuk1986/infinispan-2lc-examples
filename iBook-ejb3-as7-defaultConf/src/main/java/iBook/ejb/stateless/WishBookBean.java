package iBook.ejb.stateless;

import iBook.domain.Book;
import iBook.domain.User;
import iBook.domain.WishBook;
import iBook.ejb.stateless.remote.WishBookRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The bean implementation for the WishBookRemote interface. Wishbook related db actions are performed here.
 */
@Stateless
public class WishBookBean implements WishBookRemote {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void saveWishBook(WishBook book) {
        manager.merge(book);
    }

    @Override
    public List<WishBook> getWishList() {
        Query query = manager.createNamedQuery("listWishList");

        List<WishBook> books = query.getResultList();
        return books;
    }

    @Override
    public void deleteWishList(User user) {
        Query query = manager.createNamedQuery("deleteWishList");
        query.setParameter("user", user);
        query.executeUpdate();
    }

}
