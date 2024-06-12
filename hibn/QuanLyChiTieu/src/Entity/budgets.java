package Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class budgets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int budget_id;
	private double amount;
	private Date start_date;
	private Date end_date;

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

	public int getBudget_id() {
		return budget_id;
	}

	public void setBudget_id(int budget_id) {
		this.budget_id = budget_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}

	public budgets() {
		super();
		// TODO Auto-generated constructor stub
	}

	public budgets(double amount, Date start_date, Date end_date, users user, categories categories) {
		super();
		this.amount = amount;
		this.start_date = start_date;
		this.end_date = end_date;
		this.user = user;
		this.categories = categories;
	}

}
