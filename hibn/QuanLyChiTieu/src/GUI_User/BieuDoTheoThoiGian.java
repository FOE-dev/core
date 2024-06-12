package GUI_User;

import java.util.ArrayList;
import java.util.List;

import com.toedter.calendar.JCalendar;

import DAO.BudgetsDAO;
import DAO.CategoriesDAO;
import DAO.NotificationDAO;
import DAO.TransactionsDAO;
import DAO.UsersDao;
import Entity.categories;
import Entity.transactions;
import Entity.users;

public class BieuDoTheoThoiGian {

	private UsersDao userDao = new UsersDao();
	private BudgetsDAO budgetsDao = new BudgetsDAO();
	private CategoriesDAO categoriesDao = new CategoriesDAO();
	private NotificationDAO notificationDao = new NotificationDAO();
	private TransactionsDAO transactionDao = new TransactionsDAO();

	public double totalAmountInTransactions_Income(users user) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());
		List<transactions> listTotalIncome = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalIncome.addAll(newListTran);
		}

		for (transactions transactions : listTotalIncome) {
			total += transactions.getAmount();
		}
		return total;
	}

	public double totalAmountInTransactions_Expense(users user) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());
		List<transactions> listTotalExpense = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalExpense.addAll(newListTran);
		}

		for (transactions transactions : listTotalExpense) {
			total += transactions.getAmount();
		}
		return total;
	}

	public double totalAmountInTransactions_Income_Day(users user, JCalendar calendarBieuDo) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());
		List<transactions> listTotalIncome = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalIncome.addAll(newListTran);
		}

		for (transactions transactions : listTotalIncome) {
			if (transactions.getDate().getDate() == calendarBieuDo.getDayChooser().getDay()
					&& transactions.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
					&& transactions.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {

				total += transactions.getAmount();
			}
		}
		return total;
	}

	public double totalAmountInTransactions_Expense_Day(users user, JCalendar calendarBieuDo) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());
		List<transactions> listTotalExpense = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalExpense.addAll(newListTran);
		}

		for (transactions transactions : listTotalExpense) {

			if (transactions.getDate().getDate() == calendarBieuDo.getDayChooser().getDay()
					&& transactions.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
					&& transactions.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {

				total += transactions.getAmount();
			}
		}

		return total;
	}

	public double totalAmountInTransactions_Income_Month(users user, JCalendar calendarBieuDo) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());
		List<transactions> listTotalIncome = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalIncome.addAll(newListTran);
		}

		for (transactions transactions : listTotalIncome) {
			if (transactions.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
					&& transactions.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {

				total += transactions.getAmount();
			}
		}
		return total;
	}

	public double totalAmountInTransactions_Expense_Month(users user, JCalendar calendarBieuDo) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());
		List<transactions> listTotalExpense = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalExpense.addAll(newListTran);
		}

		for (transactions transactions : listTotalExpense) {

			if (transactions.getDate().getMonth() == calendarBieuDo.getMonthChooser().getMonth()
					&& transactions.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {

				total += transactions.getAmount();
			}
		}

		return total;
	}

	public double totalAmountInTransactions_Income_Year(users user, JCalendar calendarBieuDo) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_income(user.getUser_id());
		List<transactions> listTotalIncome = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalIncome.addAll(newListTran);
		}

		for (transactions transactions : listTotalIncome) {
			if (transactions.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {

				total += transactions.getAmount();
			}
		}
		return total;
	}

	public double totalAmountInTransactions_Expense_Year(users user, JCalendar calendarBieuDo) {
		double total = 0;
		List<categories> listTransactionIncome = categoriesDao.getAllCategoriesByUserID_expense(user.getUser_id());
		List<transactions> listTotalExpense = new ArrayList<transactions>();

		for (categories category : listTransactionIncome) {
			List<transactions> newListTran = transactionDao.getAllTransactions_byUser_id(user.getUser_id(),
					category.getCategory_id());
			listTotalExpense.addAll(newListTran);
		}

		for (transactions transactions : listTotalExpense) {

			if (transactions.getDate().getYear() == (calendarBieuDo.getYearChooser().getYear() - 1900)) {
				total += transactions.getAmount();
			}
		}

		return total;
	}
}
