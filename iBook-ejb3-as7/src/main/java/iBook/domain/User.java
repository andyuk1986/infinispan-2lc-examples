package iBook.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

/**
 * Domain class Users representing users table.
 * @author anna.manukyan
 *
 */
@Entity
@Cacheable
@NamedQueries(
        {
                @NamedQuery(
                        name = "listByUserName",
                        query = "from User as u where u.userName=:userName",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true")
                        }
                ),
                @NamedQuery(
                        name = "listByCredentials",
                        query = "from User as u where u.userName=:userName and u.password=:password",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true")
                        }
                )
        }
)
@Table(name="users")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class User implements Serializable {
	private int id;
	private String userName;
	private String password;
	private String email;
	private Date createDate;
    private Set<WishBook> userWishList;
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "email_address")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public Set<WishBook> getUserWishList() {
        return userWishList;
    }

    public void setUserWishList(Set<WishBook> userWishList) {
        this.userWishList = userWishList;
    }
}
