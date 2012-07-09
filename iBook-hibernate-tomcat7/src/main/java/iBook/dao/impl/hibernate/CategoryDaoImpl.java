package iBook.dao.impl.hibernate;

import iBook.dao.CategoryDao;
import iBook.domain.Category;
import iBook.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * CategoryDao hibernate implementation..
 */
public class CategoryDaoImpl implements CategoryDao {

    @Override
	public Category getCategoryById(int id) {
        Session session = Utils.getInstance().openTransaction();

		Object category = session.get(Category.class, id);

        Utils.getInstance().commitTransaction(session);

		return (category != null ? (Category) category : null);
	}

    @Override
	public List<Category> getAllCategories() {
        Session session = Utils.getInstance().openTransaction();

        Query query = session.getNamedQuery("listAllCategories");
        List categories = query.list();

        Utils.getInstance().commitTransaction(session);

		return categories;
	}

}
