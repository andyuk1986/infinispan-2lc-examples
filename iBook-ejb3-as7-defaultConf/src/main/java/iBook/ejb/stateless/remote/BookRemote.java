package iBook.ejb.stateless.remote;

import iBook.domain.Author;
import iBook.domain.Book;
import iBook.domain.Category;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

/**
 * Remote interface for book.
 */
@Remote
public interface BookRemote {
    /**
     * Returns the book by ID.
     * @param id        the book id.
     * @return          the got book from DB.
     */
    public iBook.domain.Book getBookById(final int id);

    /**
     * Returns the book by Author.
     * @param author        the author to search.
     * @return              the found list of books.
     */
    public List<Book> getBooksByAuthorId(final Author author);

    /**
     * Returns the books by Category.
     * @param category      the category to search.
     * @return              the found list of book.
     */
    public List<iBook.domain.Book> getBooksByCategory(final Category category);

    /**
     * Returns all books.
     * @return      the list of all books.
     */
    public List<iBook.domain.Book> getAllBooks();

    /**
     * Returns the bestseller books.
     * @return      the list of bestseller books.
     */
    public List<iBook.domain.Book> getBestSellers();

    /**
     * Returns the books to show on cover page.
     * @return      the list of books to show on cover page.
     */
    public List<iBook.domain.Book> getCoverBooks();

    /**
     * Returns the book list according to criteria map.
     * @param criteria      criterias map.
     * @return              the list of books.
     */
    public List<iBook.domain.Book> getBooksByCriterias(Map<String, String> criteria);
}
