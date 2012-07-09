package iBook.web;

import iBook.dao.CategoryDao;
import iBook.dao.factory.DaoFactory;
import iBook.domain.*;
import iBook.utils.Utils;

import java.util.*;

/**
 * Bean responsible for Books review page.
 */
public class BookPage extends Page {
    /**
     * Parameter specifying book ID.
     */
	public static final String PARAM_BOOK_ID = "id";
    /**
     * Parameter specifying category ID.
     */
	public static final String PARAM_CATEGORY_ID = "cid";

    /**
     * Returns the list of books by specified category. The category is taken from request.
     * @return      the list of books by category.
     */
	public List<Book> getBooksByCategory() {
        List<Book> bookList = null;
        CategoryDao categoryInt = DaoFactory.getInstance().getCategoryDao();
        Category category = categoryInt.getCategoryById(getIntParameter(PARAM_CATEGORY_ID));
        if(category != null) {
            bookList = DaoFactory.getInstance().getBookDao().getBooksByCategory(category);
        }

		return bookList;		
	}

}
