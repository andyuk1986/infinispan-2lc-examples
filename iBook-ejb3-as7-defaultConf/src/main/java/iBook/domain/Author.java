package iBook.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity representing authors table.
 */
@Entity
@Cacheable
@Table(name = "authors")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Author implements Serializable {
	private int id;
	private String authorName;
	private Set<Authors2Books> authors2Books = new HashSet<Authors2Books>();
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "author_name")
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    public Set<Authors2Books> getAuthors2Books() {
		return authors2Books;
	}
	
	public void setAuthors2Books(Set<Authors2Books> authors2Books) {
		this.authors2Books = authors2Books;
	}
}
