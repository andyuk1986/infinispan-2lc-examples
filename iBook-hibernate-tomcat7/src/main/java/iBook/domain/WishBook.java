package iBook.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 */
@Entity
@Table(name = "wishbooks")
@Cacheable
@NamedQueries(
        {
                @NamedQuery(
                        name = "listWishList",
                        query = "from WishBook as wb",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true")
                        }
                )
        })
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class WishBook implements Serializable {
    private int id;
    private User user;
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
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
