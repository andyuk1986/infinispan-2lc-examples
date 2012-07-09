package iBook.dao.impl.hibernate;


import iBook.dao.UserPaymentDao;
import iBook.domain.UserPayments;
import iBook.utils.Utils;
import org.hibernate.Session;

/**
 * UserPayment Dao Hibernate implementation.
 */
public class UserPaymentDaoImpl implements UserPaymentDao {

    /**
     * Saves the user's payment to DB.
     * @param payments      the user's payment object.
     */
    @Override
	public void saveUserPayments(UserPayments payments) {
        Session session = Utils.getInstance().openTransaction();

		session.persist(payments);

        Utils.getInstance().commitTransaction(session);
	}
}
