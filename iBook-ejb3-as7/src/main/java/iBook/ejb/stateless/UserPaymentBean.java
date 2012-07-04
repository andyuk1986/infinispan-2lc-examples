package iBook.ejb.stateless;


import iBook.domain.UserPayments;
import iBook.ejb.stateless.remote.UserPaymentRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Stateless UserPayment bean.
 */
@Stateless
public class UserPaymentBean implements UserPaymentRemote {
    @PersistenceContext
    private EntityManager manager;

    /**
     * Saves the user's payment to DB.
     * @param payments      the user's payment object.
     */
    @Override
	public void saveUserPayments(UserPayments payments) {
		manager.persist(payments);
	}
}
