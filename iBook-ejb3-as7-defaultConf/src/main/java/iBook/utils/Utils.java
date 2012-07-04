package iBook.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import iBook.domain.Book;
import iBook.domain.User;
import iBook.ejb.statefull.BookWishList;
import iBook.ejb.stateless.remote.BookRemote;
import iBook.ejb.stateless.remote.CategoryRemote;
import iBook.ejb.stateless.remote.UserPaymentRemote;
import iBook.ejb.stateless.remote.UserRemote;
import iBook.web.Page;

import java.util.*;

/**
 * Utility class for working with books and wishlist.
 */
public final class Utils {
    private static Utils instance = null;
    private InitialContext ctx = null;

    private Utils() {}

    /**
     * Returns the instance of this class.
     * @return
     */
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
                wishList = (BookWishList) getInitialContext().lookup("java:global/iBook/BookWishListBean!iBook.ejb.statefull.BookWishList");
                wishList.init((User)session.getAttribute(Page.LOGGED_IN_USER));

                session.setAttribute("wishList", wishList);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return wishList;
    }

    public void removeUserWishList(iBook.domain.User user) {
        //getBookWishList(new Ht).finishWishList(user);
    }

    /**
     * Returns the book by ID.
     * @param bookId        the book id.
     * @return              the book according to given ID.
     */
    public Book getBookById(int bookId) {
        Book book = null;
        try {
            BookRemote bookRemote = getBookDao();
            book = bookRemote.getBookById(bookId);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return book;
    }

    /**
     * Returns the book db dao.
     * @return      the book dao.
     * @throws NamingException      if the dao is not properly deployed.
     */
    public BookRemote getBookDao() throws NamingException {
        InitialContext ctx = new InitialContext();
        BookRemote bookRemote = (BookRemote) ctx.lookup("java:module/BookBean");

        return bookRemote;
    }

    /**
     * Returns the category db dao.
     * @return      the category dao.
     * @throws NamingException      if the dao is not properly deployed.
     */
    public CategoryRemote getCategoryDao() throws NamingException {
        InitialContext ctx = new InitialContext();
        CategoryRemote categoryRemote = (CategoryRemote) ctx.lookup("java:module/CategoryBean");

        return categoryRemote;
    }

    /**
     * Returns the category db dao.
     * @return      the category dao.
     * @throws NamingException      if the dao is not properly deployed.
     */
    public UserRemote getUserDao() throws NamingException {
        InitialContext ctx = new InitialContext();
        UserRemote userRemote = (UserRemote) ctx.lookup("java:module/UserBean");

        return userRemote;
    }

    /**
     * Returns the user payment db dao.
     * @return      the user payment dao.
     * @throws NamingException      if the dao is not properly deployed.
     */
    public UserPaymentRemote getUserPaymentDao() throws NamingException {
        InitialContext ctx = new InitialContext();
        UserPaymentRemote userPaymentRemote = (UserPaymentRemote) ctx.lookup("java:module/UserPaymentBean");

        return userPaymentRemote;
    }
}
