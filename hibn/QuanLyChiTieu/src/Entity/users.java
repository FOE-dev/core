package Entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;
	private String username;
	private String email;
	private String password_hash;
	private Timestamp created_at;
	private Timestamp last_login;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<budgets> listBudgets;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<categories> listCategories;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<notifications> ListNotifications;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<transactions> listTransactions;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getLast_login() {
		return last_login;
	}

	public void setLast_login(Timestamp last_login) {
		this.last_login = last_login;
	}

	public List<budgets> getListBudgets() {
		return listBudgets;
	}

	public void setListBudgets(List<budgets> listBudgets) {
		this.listBudgets = listBudgets;
	}

	public List<categories> getListCategories() {
		return listCategories;
	}

	public void setListCategories(List<categories> listCategories) {
		this.listCategories = listCategories;
	}

	public List<notifications> getListNotifications() {
		return ListNotifications;
	}

	public void setListNotifications(List<notifications> listNotifications) {
		ListNotifications = listNotifications;
	}

	public List<transactions> getListTransactions() {
		return listTransactions;
	}

	public void setListTransactions(List<transactions> listTransactions) {
		this.listTransactions = listTransactions;
	}
}
