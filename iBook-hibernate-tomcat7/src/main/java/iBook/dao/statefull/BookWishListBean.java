package iBook.dao.statefull;

import iBook.dao.UserPaymentDao;
import iBook.dao.WishBookDao;
import iBook.dao.factory.DaoFactory;
import iBook.domain.*;

import java.io.Serializable;
import java.util.List;

/**
 * Wish list bean, which processes user's book wish list.
 */
public class BookWishListBean implements Serializable, BookWishList {
	private static final long serialVersionUID = 1486647855776730094L;

	//The wish list of the user.
	private List<WishBook> wishList;
    private User user;

    public void init(User user) {
        this.user = user;

        WishBookDao wishBookBean = DaoFactory.getInstance().getWishBookDao();
        wishList = wishBookBean.getWishList();
    }

    @Override
	public boolean addToWishList(Book book) {
        boolean isBookAdded = false;
		if(!wishList.contains(book)) {
            WishBook wishBook = new WishBook();
            wishBook.setBook(book);
            wishBook.setUser(user);

			wishList.add(wishBook);
            isBookAdded = true;
        }

        return isBookAdded;
	}

    @Override
	public List<WishBook> getWishList() {
		return wishList;
	}

    @Override
	public void checkout() {
		System.out.println("In checkout");
		
		UserPayments userPayment = new UserPayments();
		userPayment.setUser(user);
		double sum = 0;
		UserPayments2Books tmp = null;
		for(WishBook wishBook : wishList) {
			tmp = new UserPayments2Books();

            Book book = wishBook.getBook();
			tmp.setBook(book);

            sum += book.getPrice();
			userPayment.getUserPayments2Books().add(tmp);
		}
		
        userPayment.setPaid(sum);

        UserPaymentDao userPaymentDao = DaoFactory.getInstance().getUserPaymentDao();
        userPaymentDao.saveUserPayments(userPayment);
        wishList.clear();
    }

    @Override
    public void finishWishList() {
        for(WishBook book : wishList) {
            System.out.println(book.getUser() + " " + book.getUser().getId());

            WishBookDao wishBookBean = DaoFactory.getInstance().getWishBookDao();
            wishBookBean.saveWishBook(book);
        }
    }
}
