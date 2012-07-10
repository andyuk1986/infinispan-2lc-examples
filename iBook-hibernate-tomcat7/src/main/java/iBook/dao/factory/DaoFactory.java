package iBook.dao.factory;

import iBook.dao.*;
import iBook.dao.impl.hibernate.*;

/**
 * Factory class for getting access to all DAOs.
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

    /**
     * Returns single instance of this class.
     * @return      single intance of this class.
     */
    public static DaoFactory getInstance() {
        if(instance == null) {
            instance = new DaoFactory();
        }

        return instance;
    }

    /**
     * Returns the instance of book dao,
     * @return      the instance of book dao.
     */
    public BookDao getBookDao() {
        if(bookDao == null) {
            bookDao = new BookDaoImpl();
        }

        return bookDao;
    }

    /**
     * Returns the instance of category dao.
     * @return      the instance of category dao.
     */
    public CategoryDao getCategoryDao() {
        if(categoryDao == null) {
            categoryDao = new CategoryDaoImpl();
        }

        return categoryDao;
    }

    /**
     * Returns the instance of user dao.
     * @return      the instance of user dao.
     */
    public UserDao getUserDao() {
        if(userDao == null) {
            userDao = new UserDaoImpl();
        }

        return userDao;
    }

    /**
     * Returns the instance of user payment dao instance.
     * @return      the instance of user payment dao.
     */
    public UserPaymentDao getUserPaymentDao() {
        if(userPaymentDao == null) {
            userPaymentDao = new UserPaymentDaoImpl();
        }

        return userPaymentDao;
    }

    /**
     * Returns the instance of wishbook dao instance.
     * @return      the instance of wish book instance.
     */
    public WishBookDao getWishBookDao() {
        if(wishBookDao == null) {
            wishBookDao = new WishBookDaoImpl();
        }

        return wishBookDao;
    }
}
