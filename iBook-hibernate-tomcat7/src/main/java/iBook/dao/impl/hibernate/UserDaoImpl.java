package iBook.dao.impl.hibernate;

import iBook.dao.UserDao;
import iBook.domain.User;
import iBook.utils.Utils;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.transaction.UserTransaction;
import java.util.List;

/**
 * User dao bean for DB interaction.
 * @author anna.manukyan
 *
 */
public class UserDaoImpl implements UserDao {
    @Override
	public void save(User userObj) {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

        userObj = (User) session.merge(userObj);

        Utils.getInstance().commitTransaction(tr);
	}

    @Override
	public User getUserById(int id) {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

		Object user = session.get(User.class, id);

        Utils.getInstance().commitTransaction(tr);

        return (user != null ? (User) user : null);
	}

    @Override
	public User getUserByCredentials(String userName, String password) {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

        Query query = session.getNamedQuery("listByCredentials");
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        List users = query.list();

        Utils.getInstance().commitTransaction(tr);

        return (users != null && !users.isEmpty()) ? (User) users.get(0) : null;
	}

    @Override
	public User getUserByUserName(String userName) {
        UserTransaction tr = Utils.getInstance().openTransaction();
        Session session = Utils.getInstance().getSession();

        Query query = session.getNamedQuery("listByUserName");
        query.setParameter("userName", userName);

		List users = query.list();

        Utils.getInstance().commitTransaction(tr);

		return (users != null && !users.isEmpty()) ? (User) users.get(0) : null;
	}
}
