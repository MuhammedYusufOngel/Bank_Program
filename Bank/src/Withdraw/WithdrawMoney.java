package Withdraw;

import Login.Frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import HomePage.HomePage;

@SuppressWarnings("serial")
public class WithdrawMoney extends Frame {
	
	
	Connection con;

	JLabel explanationLbl, amountLbl, calendarLbl;
	JButton withdrawButton, backButton;
	JTextArea explanationText;
	JTextField amountText;
	JDateChooser calendarText;
	
	PreparedStatement staSelectUsers, staInsertPast, staUpdateUsers;
	ResultSet rsUsers, rsPast;
		

	//Its all about connect to our local host. We write firstly url and then username and password. And then we connect.
	public void Connect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/mysql", "root", "");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public WithdrawMoney(int id) 
	{
		//Its all about user interface.
		iniFrame("Withdraw Money", 800, 800);

		add(explanationLbl = iniLabel("Explanation", 325,100,125,30, "Times New Roman", 25));
		add(amountLbl = iniLabel("Amount", 50,280,125,30, "Times New Roman", 25));
		add(calendarLbl = iniLabel("History", 550,280,200,30, "Times New Roman", 25));
		add(amountText = iniText(50,325,200,30, "Times New Roman", 25));
		add(withdrawButton = iniButton("Withdraw", 325,600,150,40, "Times New Roman", 25));
		add(backButton = iniButton("Back", 325,650,150,40, "Times New Roman", 25));
		
		explanationText = new JTextArea();
		explanationText.setBounds(185,140,400,120);
		explanationText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		explanationText.setLineWrap(true);
		explanationText.setWrapStyleWord(true);
		add(explanationText);

		calendarText = new JDateChooser();
		calendarText.setBounds(550,325,200,30);
		add(calendarText);
		
		//It goes back.
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new HomePage(id);
				closeFrame();
			}
		});
		
		//Firstly the program checks that withdraw money or not. Then will be add these datas, update the "recentMoney" and then ask to user that go home page or not.
		withdrawButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connect();
				
				try {
					staSelectUsers = con.prepareStatement("Select totalMoney from users where id=?");
					staSelectUsers.setInt(1, id);
					rsUsers = staSelectUsers.executeQuery();
					
					if(rsUsers.next() == true) {
						float recentMoney = rsUsers.getFloat(1);
						float amount = Float.parseFloat(amountText.getText());
						
						//Firstly the program checks that withdraw money or not.
						if(recentMoney < amount)
						{
							JOptionPane.showMessageDialog(null, "Your balance isn't enough. Please try again. \n Your balance: " + recentMoney, "ERROR", JOptionPane.ERROR_MESSAGE);
							amountText.setText("");
						}
						else {
							recentMoney -= amount;
							staInsertPast = con.prepareStatement("insert into past(id,explanation,amount,history,revExp,recentMoney) values (?,?,?,?,?,?)");
							
							staInsertPast.setInt(1, id);
							staInsertPast.setString(2, explanationText.getText().toString());
							staInsertPast.setFloat(3, amount);
							staInsertPast.setString(4, calendarText.getDate().toString());
							staInsertPast.setString(5, "-");
							staInsertPast.setFloat(6, recentMoney);
							
							staInsertPast.executeUpdate();
							
							staUpdateUsers = con.prepareStatement("update users set totalMoney=? where id=?");
							
							staUpdateUsers.setFloat(1, recentMoney);
							staUpdateUsers.setInt(2, id);
							
							staUpdateUsers.executeUpdate();
							
							int response = JOptionPane.showConfirmDialog(null, "Your process has been successfully. Do you want to go home page?","OK",JOptionPane.YES_NO_OPTION);
							
							if(response == JOptionPane.YES_OPTION) {
								new HomePage(id);
								closeFrame();
							}
							else {
								explanationText.setText("");
								amountText.setText("");
							}
						}
					}
					else {
						JOptionPane.showConfirmDialog(null, "Something wrong. You will be redirected to the home page.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
}
