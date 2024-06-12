package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Entity.users;

public class UsersDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuanLyChiTieu");
	private EntityManager em = emf.createEntityManager();

	public List<users> getAllUsers() {
		TypedQuery<users> query = em.createQuery("select p from users p", users.class);
		return query.getResultList();
	}

	public users performLogin(String email) {
		TypedQuery<users> query = em.createQuery(
				"SELECT u FROM users u WHERE u.email = :email ", users.class);
		query.setParameter("email", email);
		List<users> result = query.getResultList();
		return result.size() == 1 ? result.get(0) : null;
	}

	public boolean isEmailExists(String email) {
		TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM users u WHERE u.email = :email", Long.class);
		query.setParameter("email", email);
		return query.getSingleResult() > 0;
	}

	public void addUser(users user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	public void deleteUser(int id) {
		em.getTransaction().begin();
		users user = em.find(users.class, id);
		if (user != null)
			em.remove(user);
		em.getTransaction().commit();
	}

	public void updateUser(users user) {
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
	}

	public users getUserByEmail(String gmail) {
		TypedQuery<users> query = em.createQuery("select p from users p where p.email = :email", users.class);
		query.setParameter("email", gmail);
		List<users> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
}
