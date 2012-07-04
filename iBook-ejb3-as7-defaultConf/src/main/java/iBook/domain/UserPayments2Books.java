package iBook.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;


/**
 * Entity representing userPayments2books table.
 */
@Entity
@Table(name = "userPayments2books")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserPayments2Books implements Serializable {
	private int id;
	private UserPayments transactionId;
	private Book book;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transaction_id")
	public UserPayments getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(UserPayments transactionId) {
		this.transactionId = transactionId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "book_id", nullable = false)
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
}
