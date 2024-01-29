package Expenses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import HomePage.HomePage;
import Login.Frame;

@SuppressWarnings("serial")
public class PastExpenses extends Frame{

	Connection con;
	JButton back;
	
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
	
	@SuppressWarnings("unchecked")
	public PastExpenses(int id) 
	{
		//Its all about user interface.
		Connect();
		iniFrame("My Past Expenses", 800, 800);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25,50,750,350);
		add(scrollPane);

		add(back = iniButton("Back", 340,500,120,30, "Times New Roman", 15));
		
		//We create the table to show his/her expenses of user.
		JTable table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		Object[] column = {"Amount", "Explanation","History","Recent Money"};
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		//We select the table (past) on the data base. End then the program transfer to table that we create.
		try {
			statement = con.prepareStatement("select explanation, amount, history, revExp, recentMoney from userpastexp where userID = '" + id + "' order by userID");
								
			rs = statement.executeQuery();
				
			while(rs.next())
			{
				@SuppressWarnings("rawtypes")
				Vector v2 = new Vector();
					
				v2.add(rs.getString("RevExp") + " " + rs.getString("Amount") + " TL");
				v2.add(rs.getString("Explanation"));
				v2.add(rs.getString("History"));
				v2.add(rs.getString("RecentMoney"));
					
				model.addRow(v2);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Go to home page
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new HomePage(id);
				closeFrame();
			}
		});
	}
}
