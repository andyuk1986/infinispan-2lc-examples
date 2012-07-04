package iBook.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity representing categories table.
 */
@Entity
@Cacheable
@NamedNativeQueries(
        @NamedNativeQuery(
                name = "listAllCategories",
                query = "select c.* from categories c order by c.category_name",
                hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true")
                },
                resultClass = Category.class)
)
@Table(name = "categories")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Category implements Serializable {
	private int id;
	private String categoryName;
	private Set<Book> books = new HashSet<Book>();
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "category_name")
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
}
