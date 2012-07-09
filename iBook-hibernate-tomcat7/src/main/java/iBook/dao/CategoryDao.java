package iBook.dao;

import java.util.List;

/**
 * Remote interface of Category dao bean.
 */
public interface CategoryDao {
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
