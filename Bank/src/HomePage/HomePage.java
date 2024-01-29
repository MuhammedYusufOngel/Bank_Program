package HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;

import Deposit.DepositMoney;
import Expenses.PastExpenses;
import Login.Frame;
import Login.Home;
import Register.MainFrame;
import Withdraw.WithdrawMoney;

@SuppressWarnings("serial")
public class HomePage extends Frame{

	Connection con;
	JLabel nameLabel, balanceLabel, balance;
	JButton expenses, deposit, withdraw, send, exit;
	
	int ID;

	MainFrame mainFrame;
	PreparedStatement statement;
	ResultSet rs;
	
	//Its all about connect to our local host. We write firstly url and then username and password. And then we connect.
	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "password123");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public HomePage(int id)
	{
		//Its all about user interface.
		Connect();
		iniFrame("Home Page", 800, 800);
		
		//After connect data base we select name and totalMoney and the program write label.
		try {
			statement = con.prepareStatement("select name,totalMoney from userinfos where userID = ?");
			statement.setInt(1, id);
			
			rs = statement.executeQuery();
			
			ID = id;
			
			if(rs.next() == true)
			{
				String name = rs.getString(1);
				String totalMoney = rs.getString(2);
				
				add(iniLabel("Welcome " + name, 0, 10, 800, 50, "Segoe Print", 50));
				add(iniLabel("Your Balance", 325, 200, 150, 20, "Times New Roman", 25));
				add(iniLabel(totalMoney + " TL", 325, 300, 150, 20, "Times New Roman", 25));
				add(expenses = iniButton("My Past Expenses", 250, 400, 300, 40, "Times New Roman", 25));
				add(deposit = iniButton("Deposit Money", 250, 450, 300, 40, "Times New Roman", 25));
				add(withdraw = iniButton("Withdraw Money", 250, 500, 300, 40, "Times New Roman", 25));
				add(exit = iniButton("Exit", 250, 550, 300, 40, "Times New Roman", 25));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//It goes to Expense Class
		expenses.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PastExpenses(id);
				closeFrame();
			}
		});

		//It goes to DepositMoney Class
		deposit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new DepositMoney(id);
				closeFrame();
			}
		});

		//It goes to WithdrawMoney Class
		withdraw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new WithdrawMoney(id);
				closeFrame();
			}
		});
		
		//It exits
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Home();
				closeFrame();
			}
		});
	}
	
	
}
