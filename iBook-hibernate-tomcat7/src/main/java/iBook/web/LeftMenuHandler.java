package iBook.web;

import java.util.List;

import iBook.dao.CategoryDao;
import iBook.dao.factory.DaoFactory;
import iBook.domain.Book;
import iBook.domain.Category;
import iBook.dao.BookDao;
import iBook.utils.Utils;

import javax.naming.NamingException;

/**
 * Responsible class for LeftMenu page.
 */
public class LeftMenuHandler extends Page {

    /**
     * Returns the list of all categories.
     * @return      the list of all categories.
     */
    public List<Category> getAllAvailableCategories() {
        List<Category> categoryList = null;

        CategoryDao categ = DaoFactory.getInstance().getCategoryDao();
        if(categ != null) {
            categoryList = categ.getAllCategories();
        }
        return categoryList;
    }

    /**
     * Returns the list of bestsellers.
     * @return      the list of bestsellers.
     */
    public List<Book> getBestsellers() {
        List<Book> bookList = null;
        BookDao bookInterface = DaoFactory.getInstance().getBookDao();
        if(bookInterface != null) {
            bookList = bookInterface.getBestSellers();
        }
        return bookList;
    }
}
