package iBook.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.*;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CascadeType;

/**
 * Entity representing user_payments table.
 */
@Entity
@Table(name = "user_payments")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserPayments implements Serializable {
	private int transaction_id;
	private User user;
	private Set<UserPayments2Books> userPayments2Books = new HashSet<UserPayments2Books>();
	private double paid;
    private Date createDate;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "transaction_id")
	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @Cascade({CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="transaction_id")
	public Set<UserPayments2Books> getUserPayments2Books() {
		return userPayments2Books;
	}

	public void setUserPayments2Books(Set<UserPayments2Books> userPayments2Books) {
		this.userPayments2Books = userPayments2Books;
	}

	@Column(name="paid")
	public double getPaid() {
		return paid;
	}
	
	public void setPaid(double paid) {
		this.paid = paid;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
