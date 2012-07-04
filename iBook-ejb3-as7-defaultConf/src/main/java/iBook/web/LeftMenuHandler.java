package iBook.web;

import java.util.List;

import iBook.domain.Book;
import iBook.domain.Category;
import iBook.ejb.stateless.remote.BookRemote;
import iBook.ejb.stateless.remote.CategoryRemote;
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

        try {
            CategoryRemote categ = Utils.getInstance().getCategoryDao();
            if(categ != null) {
                categoryList = categ.getAllCategories();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return categoryList;
	}

    /**
     * Returns the list of bestsellers.
     * @return      the list of bestsellers.
     */
	public List<Book> getBestsellers() {
        List<Book> bookList = null;
        try {
            BookRemote bookInterface = Utils.getInstance().getBookDao();
            if(bookInterface != null) {
                bookList = bookInterface.getBestSellers();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bookList;
	}
}
