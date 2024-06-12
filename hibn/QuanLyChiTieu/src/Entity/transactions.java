package Entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class transactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transaction_id;
	private double amount;
	private Date date;
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private users user;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private categories categories;

	public categories getCategories() {
		return categories;
	}

	public void setCategories(categories categories) {
		this.categories = categories;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}

	public transactions(double amount, Date date, String description, users user, categories categories) {
		super();
		this.amount = amount;
		this.date = date;
		this.description = description;
		this.user = user;
		this.categories = categories;
	}

	public transactions() {
		super();
		// TODO Auto-generated constructor stub
	}

}
