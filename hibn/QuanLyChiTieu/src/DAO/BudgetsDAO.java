package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Entity.budgets;
import Entity.notifications;

public class BudgetsDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuanLyChiTieu");
	private EntityManager em = emf.createEntityManager();

	public List<budgets> getAllBudgets() {
		TypedQuery<budgets> query = em.createQuery("select p from budgets p", budgets.class);
		return query.getResultList();
	}

	public void addBudgets(budgets budget) {
		em.getTransaction().begin();
		em.persist(budget);
		em.getTransaction().commit();
	}

	public void deleteBudgets(int id) {
		em.getTransaction().begin();
		budgets budget = em.find(budgets.class, id);
		if (budget != null)
			em.remove(budget);
		em.getTransaction().commit();
	}

	public void updateBudgets(budgets budget) {
		em.getTransaction().begin();
		em.merge(budget);
		em.getTransaction().commit();
	}

	public List<budgets> getAllBudgetsByUserID(int userid) {
		TypedQuery<budgets> query = em.createQuery("select p from budgets p where p.user.user_id = :userid",
				budgets.class);
		query.setParameter("userid", userid);
		return query.getResultList();
	}

	public List<budgets> getAllBudgetsByUserID_CategoryId(int user_id, int category_id) {
		TypedQuery<budgets> query = em.createQuery(
				"select p from budgets p where p.user.user_id = :user_id and p.categories.category_id = :category_id",
				budgets.class);
		query.setParameter("user_id", user_id);
		query.setParameter("category_id", category_id);
		return query.getResultList();
	}
}
