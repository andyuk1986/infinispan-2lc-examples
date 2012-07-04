package iBook.ejb.stateless;

import iBook.domain.Category;
import iBook.ejb.stateless.remote.CategoryRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Stateless Category bean.
 */
@Stateless
public class CategoryBean implements CategoryRemote{

    @PersistenceContext
    private EntityManager manager;

    @Override
	public Category getCategoryById(int id) {
		Category category = manager.find(Category.class, id);
		
		return category;
	}

    @Override
	public List<Category> getAllCategories() {
        Query query = manager.createNamedQuery("listAllCategories");
        List categories = query.getResultList();

		return categories;
	}

}
