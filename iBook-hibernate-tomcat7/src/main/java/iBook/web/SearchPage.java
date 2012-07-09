package iBook.web;

import iBook.dao.CategoryDao;
import iBook.dao.factory.DaoFactory;
import iBook.domain.Book;
import iBook.domain.Category;

import java.util.List;

/**
 * Bean respoonsible for Book Search.
 */
public class SearchPage extends Form {
    /**
     * Parameter specifying title.
     */
    public static final String PARAM_TITLE = "title";
    /**
     * Parameter specifying author.
     */
    public static final String PARAM_AUTHOR = "author";
    /**
     * Parameter specifying category.
     */
    public static final String PARAM_CATEGORY = "category";
    /**
     * Parameter specifying bestsellers.
     */
    public static final String PARAM_BESTSELLER = "bestSeller";
    /**
     * Parameter specifying that no category is selected.
     */
    public static final String NO_CATEGORY = "no_cat";

    private List<Book> searchedBookList = null;

    @Override
    public void submit() throws Exception {
        String title = getParameter(PARAM_TITLE);
        String author = getParameter(PARAM_AUTHOR);
        String category = getParameter(PARAM_CATEGORY);
        String bestSeller = getParameter(PARAM_BESTSELLER);

        if((title == null || title.isEmpty()) && (author == null || author.isEmpty()) && (category.equals(NO_CATEGORY)) &&
                (bestSeller == null)) {
            searchedBookList = DaoFactory.getInstance().getBookDao().getAllBooks();
        } else {

        }

    }

    /**
     * Returns all categories.
     * @return      all categories list.
     */
    public List<Category> getCategories() {
        List<Category> categories = null;
        CategoryDao category = DaoFactory.getInstance().getCategoryDao();
        if(category != null) {
            categories = category.getAllCategories();
        }

        return categories;
    }

}
