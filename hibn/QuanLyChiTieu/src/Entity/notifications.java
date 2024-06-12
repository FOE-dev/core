package Entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notification_id;
	private String message;
	private Timestamp date;
	private String status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private users user;

	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}

	public notifications(String message, Timestamp date, String status, users user) {
		super();
		this.message = message;
		this.date = date;
		this.status = status;
		this.user = user;
	}

	public notifications() {
		super();
		// TODO Auto-generated constructor stub
	}
}
