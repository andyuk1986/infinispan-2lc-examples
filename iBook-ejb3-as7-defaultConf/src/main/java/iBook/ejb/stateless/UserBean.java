package iBook.ejb.stateless;

import iBook.domain.User;
import iBook.ejb.stateless.remote.UserRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * User stateless bean for DB interaction.
 * @author anna.manukyan
 *
 */
@Stateless
public class UserBean implements UserRemote {
    @PersistenceContext
    private EntityManager manager;

    @Override
	public void save(User userObj) {
        userObj = manager.merge(userObj);
	}

    @Override
	public User getUserById(int id) {
		User user = manager.find(User.class, id);

        return user;
	}

    @Override
	public User getUserByCredentials(String userName, String password) {
        Query query = manager.createNamedQuery("listByCredentials");
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        List users = query.getResultList();

        return (users != null && !users.isEmpty()) ? (User) users.get(0) : null;
	}

    @Override
	public User getUserByUserName(String userName) {
        Query query = manager.createNamedQuery("listByUserName");
        query.setParameter("userName", userName);

		List users = query.getResultList();
		return (users != null && !users.isEmpty()) ? (User) users.get(0) : null;
	}
}
