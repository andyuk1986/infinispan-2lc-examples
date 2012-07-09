package iBook.dao.factory;

import iBook.dao.*;
import iBook.dao.impl.hibernate.*;

/**
 * Created with IntelliJ IDEA.
 * User: anna.manukyan
 * Date: 6/20/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public final class DaoFactory {
    private static DaoFactory instance;
    private BookDao bookDao;
    private CategoryDao categoryDao;
    private UserDao userDao;
    private UserPaymentDao userPaymentDao;
    private WishBookDao wishBookDao;

    private DaoFactory(){

    }

    public static DaoFactory getInstance() {
        if(instance == null) {
            instance = new DaoFactory();
        }

        return instance;
    }

    public BookDao getBookDao() {
        if(bookDao == null) {
            bookDao = new BookDaoImpl();
        }

        return bookDao;
    }

    public CategoryDao getCategoryDao() {
        if(categoryDao == null) {
            categoryDao = new CategoryDaoImpl();
        }

        return categoryDao;
    }

    public UserDao getUserDao() {
        if(userDao == null) {
            userDao = new UserDaoImpl();
        }

        return userDao;
    }

    public UserPaymentDao getUserPaymentDao() {
        if(userPaymentDao == null) {
            userPaymentDao = new UserPaymentDaoImpl();
        }

        return userPaymentDao;
    }

    public WishBookDao getWishBookDao() {
        if(wishBookDao == null) {
            wishBookDao = new WishBookDaoImpl();
        }

        return wishBookDao;
    }
}
