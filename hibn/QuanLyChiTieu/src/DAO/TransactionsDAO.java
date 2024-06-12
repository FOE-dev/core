package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Entity.transactions;
import Entity.users;

public class TransactionsDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuanLyChiTieu");
	private EntityManager em = emf.createEntityManager();

	public List<transactions> getAllTransactions() {
		TypedQuery<transactions> query = em.createQuery("select p from transactions p", transactions.class);
		return query.getResultList();
	}

	public void addTransactions(transactions transaction) {
		em.getTransaction().begin();
		em.persist(transaction);
		em.getTransaction().commit();
	}

	public void deleteTransactions(int id) {
		em.getTransaction().begin();
		transactions transaction = em.find(transactions.class, id);
		if (transaction != null)
			em.remove(transaction);
		em.getTransaction().commit();
	}

	public void updateTransactions(transactions transaction) {
		em.getTransaction().begin();
		em.merge(transaction);
		em.getTransaction().commit();
	}

	public List<transactions> getAllTransactions_byUser_id(int userId, int category_id) {
		TypedQuery<transactions> query = em.createQuery(
				"SELECT t FROM transactions t WHERE t.user.id = :userId AND t.categories.id = :categoryId",
				transactions.class);
		query.setParameter("userId", userId);
		query.setParameter("categoryId", category_id);
		return query.getResultList();
	}
	
}
