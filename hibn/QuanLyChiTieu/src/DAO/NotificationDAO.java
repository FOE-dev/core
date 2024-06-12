package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import Entity.notifications;

public class NotificationDAO {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("QuanLyChiTieu");
	private EntityManager em = emf.createEntityManager();

	public List<notifications> getAllNotifications() {
		TypedQuery<notifications> query = em.createQuery("select p from notifications p", notifications.class);
		return query.getResultList();
	}

	public void addNotifications(notifications notification) {
		em.getTransaction().begin();
		em.persist(notification);
		em.getTransaction().commit();
	}

	public void deleteNotifications(int id) {
		em.getTransaction().begin();
		notifications notification = em.find(notifications.class, id);
		if (notification != null)
			em.remove(notification);
		em.getTransaction().commit();
	}

	public void updateNotification(notifications notification) {
		em.getTransaction().begin();
		em.merge(notification);
		em.getTransaction().commit();
	}

	public List<notifications> getAllNotificationsByUserID(int userid) {
		TypedQuery<notifications> query = em.createQuery("select p from notifications p where p.user.user_id = :userid",
				notifications.class);
		query.setParameter("userid", userid);
		return query.getResultList();
	}
}
