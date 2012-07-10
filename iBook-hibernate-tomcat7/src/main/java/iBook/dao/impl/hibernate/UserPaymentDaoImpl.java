package iBook.dao.impl.hibernate;


import iBook.dao.UserPaymentDao;
import iBook.domain.UserPayments;
import iBook.utils.Utils;
import org.hibernate.Session;

import javax.transaction.UserTransaction;

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
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

		session.persist(payments);

        Utils.getInstance().commitTransaction(tr);
	}
}
