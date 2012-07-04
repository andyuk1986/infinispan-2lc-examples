package iBook.ejb.stateless;

import iBook.domain.Author;
import iBook.domain.Book;
import iBook.domain.Category;
import iBook.ejb.stateless.remote.BookRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Stateless Book bean.
 */
@Stateless
public class BookBean implements BookRemote {
	public static final int COVER_BOOK_COUNT = 4;

    @PersistenceContext
    private EntityManager manager;

    @Override
	public Book getBookById(int id) {
        return manager.find(Book.class, id);
	}

    @Override
	public List<Book> getBooksByAuthorId(Author author) {
		Query query = manager.createNamedQuery("listByAuthor");
		query.setParameter("author", author);

		List<Book> books = query.getResultList();
		return books;
	}

    @Override
	public List<Book> getBooksByCategory(Category category) {
        Query query = manager.createNamedQuery("listByCategory");
		query.setParameter("category", category);

		List<Book> books = query.getResultList();
		return books;
	}

    @Override
	public List<Book> getAllBooks() {
		return getBooksByQuery("listAllBook", 0);
	}

    @Override
	public List<Book> getBestSellers() {
		return getBooksByQuery("listBestSellers", 0);
	}

    @Override
	public List<Book> getCoverBooks() {
		return getBooksByQuery("listRandomBooks", COVER_BOOK_COUNT);
	}

    @Override
	public List<Book> getBooksByCriterias(Map<String, String> criteria) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        for(Map.Entry<String, String> cr : criteria.entrySet()) {
            builder.like(builder.literal(cr.getKey()), builder.literal(cr.getValue()));
        }

        CriteriaQuery<Object> criteriaQuery = builder.createQuery();
        Root<Book> from = criteriaQuery.from(Book.class);
        CriteriaQuery<Object> select = criteriaQuery.select(from);
        TypedQuery<Object> typedQuery = manager.createQuery(select).setHint("org.hibernate.cacheable", true);
        List resultList = typedQuery.getResultList();

        return (List<Book>) resultList;
	}
	
	private List<Book> getBooksByQuery(final String namedQueryName, final int limit) {
		Query query = manager.createNamedQuery(namedQueryName);
		if(limit > 0) {
			query.setMaxResults(limit);
		}

		List<Book> books = query.getResultList();
		return books;
	}

}
