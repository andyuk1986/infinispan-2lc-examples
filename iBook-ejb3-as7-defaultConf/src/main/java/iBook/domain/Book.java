package iBook.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity representing books table.
 */
@Entity
@Cacheable
@NamedNativeQueries(
        {
                @NamedNativeQuery(name="listBestSellers", query="select book.* "
                        + "from books as book where book.isBestSeller=1",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true")
                        }, resultSetMapping="bookQueries"),
                @NamedNativeQuery(name="listAllBook", query="select book.* "
                        + "from books as book",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true")},
                        resultSetMapping="bookQueries"),
                @NamedNativeQuery(name="listRandomBooks", query="select book.* "
                        + "from books as book order by rand()",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true")
                        },
                        resultSetMapping="bookQueries")
        }
)
@SqlResultSetMapping(name="bookQueries", entities={
        @EntityResult(entityClass=Book.class)

}
)
@NamedQueries(
        {
                @NamedQuery(
                        name = "listByAuthor",
                        query = "from Book as b where b.author=:author",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }
                ),
                @NamedQuery(
                        name = "listByCategory",
                        query = "select b from Book as b where b.category=:category",
                        hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }
                )
        })
@Table(name = "books")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Book implements Serializable {
    private int id;
    private Category category;
    private String title;
    private String description;
    private Set<Authors2Books> author;
    private boolean bestSeller;
    private double price;
    private String photoUrl;
    private int publishDate;
    private Set<UserPayments2Books> userBooks;
    private Set<WishBook> wishListBooks;

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
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    public Set<Authors2Books> getAuthor() {
        return author;
    }

    public void setAuthor(Set<Authors2Books>  author) {
        this.author = author;
    }

    @Column(name = "isBestSeller")
    public boolean isBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(boolean isBestSeller) {
        this.bestSeller = isBestSeller;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Column(name = "publish_date")
    public int getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(int publishYear) {
        this.publishDate = publishYear;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public Set<UserPayments2Books> getUserBooks() {
        return userBooks;
    }

    public void setUserBooks(Set<UserPayments2Books> userBooks) {
        this.userBooks = userBooks;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    public Set<WishBook> getWishListBooks() {
        return wishListBooks;
    }

    public void setWishListBooks(Set<WishBook> wishListBooks) {
        this.wishListBooks = wishListBooks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj != null && obj instanceof Book) {
            Book obj1 = (Book) obj;
            if(obj1.id == this.id) {
                isEqual = true;
            }
        }

        return isEqual;
    }



}
