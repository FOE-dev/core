package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Entity.categories;

public class CategoriesDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuanLyChiTieu");
	private EntityManager em = emf.createEntityManager();

	public List<categories> getAllCategories() {
		TypedQuery<categories> query = em.createQuery("select p from categories p", categories.class);
		return query.getResultList();
	}

	public void addCategories(categories category) {
		em.getTransaction().begin();
		em.persist(category);
		em.getTransaction().commit();
	}

	public void deleteCategories(int id) {
		em.getTransaction().begin();
		categories category = em.find(categories.class, id);
		if (category != null)
			em.remove(category);
		em.getTransaction().commit();
	}

	public void updateCategories(categories category) {
		em.getTransaction().begin();
		em.merge(category);
		em.getTransaction().commit();
	}

	public List<categories> getAllCategoriesByUserID_expense(int user_id) {
		TypedQuery<categories> query = em.createQuery(
				"SELECT c FROM categories c WHERE c.user.user_id = :user_id AND c.type = 'expense'", categories.class);
		query.setParameter("user_id", user_id);
		return query.getResultList();
	}

	public List<categories> getAllCategoriesByUserID_income(int user_id) {
		TypedQuery<categories> query = em.createQuery(
				"SELECT c FROM categories c WHERE c.user.user_id = :user_id AND c.type = 'income'", categories.class);
		query.setParameter("user_id", user_id);
		return query.getResultList();
	}

	public List<categories> getAllCategoriesByUserID(int user_id) {
		TypedQuery<categories> query = em.createQuery("SELECT c FROM categories c WHERE c.user.user_id = :user_id",
				categories.class);
		query.setParameter("user_id", user_id);
		return query.getResultList();
	}

}
