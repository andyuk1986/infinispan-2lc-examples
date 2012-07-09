package iBook.dao;

import iBook.domain.UserPayments;

/**
 * Dao for working with UserPayments.
 */
public interface UserPaymentDao {
    /**
     * Saves the user's payment with all corresponding cascadings.
     * @param payments      the user's payment object.
     */
    public void saveUserPayments(UserPayments payments);

}
