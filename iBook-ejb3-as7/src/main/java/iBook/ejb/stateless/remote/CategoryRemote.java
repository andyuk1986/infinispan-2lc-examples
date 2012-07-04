package iBook.ejb.stateless.remote;

import javax.ejb.Remote;
import java.util.List;

/**
 * Remote interface of Category stateless bean.
 */
@Remote
public interface CategoryRemote {
    /**
     * Returns the category by ID.
     * @param id        the id of category to return.
     * @return          the found category, null if no category found.
     */
    public iBook.domain.Category getCategoryById(final int id);

    /**
     * List of all categories.
     * @return      the list of all categories.
     */
    public List<iBook.domain.Category> getAllCategories();

}
