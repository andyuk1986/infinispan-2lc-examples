package iBook.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import iBook.dao.CategoryDao;
import iBook.dao.UserDao;
import iBook.dao.UserPaymentDao;
import iBook.dao.factory.DaoFactory;
import iBook.domain.Book;
import iBook.domain.User;
import iBook.dao.statefull.BookWishList;
import iBook.dao.BookDao;
import iBook.web.Page;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class for working with books and wishlist.
 */
public final class Utils {
    private static Utils instance = null;
    private InitialContext ctx = null;
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
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

    private InitialContext getInitialContext() throws NamingException {
        if(ctx == null) {
            ctx = new InitialContext();
        }
        return ctx;
    }

    /**
     * Returns the wishlist object.
     * @return      the wishlist statefull session bean.
     */
    public BookWishList getBookWishList(HttpSession session) {
        BookWishList wishList = (BookWishList) session.getAttribute("wishList");
        if (wishList== null) {
            try {
                wishList = (BookWishList) getInitialContext().lookup("java:global/iBook/BookWishListBean!iBook.dao.statefull.BookWishList");
                wishList.init((User)session.getAttribute(Page.LOGGED_IN_USER));

                session.setAttribute("wishList", wishList);
            } catch (NamingException e) {
                e.printStackTrace();
            }
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

    public Session openTransaction() {
        Session session = getSession();
        session.beginTransaction();

        return session;
    }

    public void commitTransaction(final Session session) {
        session.getTransaction().commit();
    }
}
