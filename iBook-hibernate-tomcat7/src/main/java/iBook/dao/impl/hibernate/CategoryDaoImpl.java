package iBook.dao.impl.hibernate;

import iBook.dao.CategoryDao;
import iBook.domain.Category;
import iBook.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.transaction.UserTransaction;
import java.util.List;

/**
 * CategoryDao hibernate implementation..
 */
public class CategoryDaoImpl implements CategoryDao {

    @Override
	public Category getCategoryById(int id) {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

		Object category = session.get(Category.class, id);

        Utils.getInstance().commitTransaction(tr);

		return (category != null ? (Category) category : null);
	}

    @Override
	public List<Category> getAllCategories() {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

        Query query = session.getNamedQuery("listAllCategories");
        List categories = query.list();

        Utils.getInstance().commitTransaction(tr);

		return categories;
	}

}
