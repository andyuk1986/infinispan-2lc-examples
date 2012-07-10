package iBook.utils;

import iBook.dao.BookDao;
import iBook.dao.factory.DaoFactory;
import iBook.dao.statefull.BookWishList;
import iBook.dao.statefull.BookWishListBean;
import iBook.domain.Book;
import iBook.domain.User;
import iBook.web.Page;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.infinispan.InfinispanRegionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.io.IOException;

/**
 * Utility class for working with books and wishlist.
 */
public final class Utils {
    private static Utils instance = null;
    private static SessionFactory sessionFactory = null;
    private EmbeddedCacheManager cacheManager;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private Utils() {}

    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public EmbeddedCacheManager getCacheManager() throws IOException {
        if(cacheManager == null) {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) getSessionFactory();
            InfinispanRegionFactory regionFactory = (InfinispanRegionFactory) sessionFactoryImpl.getSettings().getRegionFactory();
            cacheManager = regionFactory.getCacheManager();
        }

        return cacheManager;
    }

    /**
     * Returns the wishlist object.
     * @return      the wishlist statefull session bean.
     */
    public BookWishList getBookWishList(HttpSession session) {
        BookWishList wishList = null;
        try {
            User user = (User) session.getAttribute(Page.LOGGED_IN_USER);
            wishList = (BookWishList) getCacheManager().getCache("sfsb").get("wishList" + user.getId());
            if (wishList== null) {

                wishList = new BookWishListBean();
                wishList.init(user);

                Cache cache = getCacheManager().getCache("sfsb");
                TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
                tm.begin();
                cache.put("wishList" + user.getId(), wishList);
                tm.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wishList;
    }

    /**
     * Returns the book by ID.
     * @param bookId        the book id.
     * @return              the book according to given ID.
     */
    public Book getBookById(int bookId) {
        BookDao bookDao = DaoFactory.getInstance().getBookDao();

        return bookDao.getBookById(bookId);
    }

    /**
     * Returns the retrived session factory object.
     * @return the session factory object.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Returns the current Session.
     * @return current session to use.
     */
    public Session getSession() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        return session;
    }

    public UserTransaction openTransaction() {
        UserTransaction tx = null;
        try {
            tx = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
            tx.begin();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tx;
    }

    public void commitTransaction(final UserTransaction tx) {
        try {
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (SystemException e1) {
                System.out.println("Transaction rollback is failed!!!" + e1);
            }
            e.printStackTrace();
        }
    }
}
