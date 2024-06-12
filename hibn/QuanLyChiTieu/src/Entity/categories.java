package Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class categories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int category_id;
	private String name;
	private String type;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private users user;

	@OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
	private List<budgets> listBudgets;

	@OneToMany(mappedBy = "categories", cascade = CascadeType.ALL)
	private List<transactions> listTransactions;

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public users getUser() {
		return user;
	}

	public void setUser(users user) {
		this.user = user;
	}

	public List<budgets> getListBudgets() {
		return listBudgets;
	}

	public void setListBudgets(List<budgets> listBudgets) {
		this.listBudgets = listBudgets;
	}

	public List<transactions> getListTransactions() {
		return listTransactions;
	}

	public void setListTransactions(List<transactions> listTransactions) {
		this.listTransactions = listTransactions;
	}

	public categories(String name, String type, users user) {
		super();
		this.name = name;
		this.type = type;
		this.user = user;
	}

	public categories() {
		super();
		// TODO Auto-generated constructor stub
	}

}
