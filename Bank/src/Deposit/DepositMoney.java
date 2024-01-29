package Deposit;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import Login.Frame;

@SuppressWarnings("serial")
public class DepositMoney extends Frame{
	
	Connection con;
	
	JLabel explanation, amount, calendar;
	JButton deposit, backButton;
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
	
	public DepositMoney(int id) {
		//Its all about user interface.
		iniFrame("Deposit Money", 800, 800);
		
		add(explanation = iniLabel("Explanation", 325,100,125,30, "Times New Roman", 25));
		add(amount = iniLabel("Amount", 50,280,125,30, "Times New Roman", 25));
		add(calendar = iniLabel("History", 550,280,200,30, "Times New Roman", 25));
		add(amountText = iniText(50,325,200,30, "Times New Roman", 25));
		add(deposit = iniButton("Deposit", 325,600,150,40, "Times New Roman", 25));
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
		
		//It ensures enter only number.
		amountText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) 
			{	            
	            if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_PERIOD) 
	            {
	            	amountText.setEditable(true);
	            } 
	            else 
	            {
	            	amountText.setEditable(false);
	            }
	         }
	      });
		
		//The program will be add these datas, update the "recentMoney" and then ask to user that go home page or not.
		deposit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connect();
				
				try {
					staSelectUsers = con.prepareStatement("Select totalMoney from userinfos where userID=?");
					staSelectUsers.setInt(1, id);
					rsUsers = staSelectUsers.executeQuery();
					
					if(rsUsers.next() == true) {
						float recentMoney = rsUsers.getFloat(1);
						float amount = Float.parseFloat(amountText.getText());
						
						recentMoney += amount;
						
						staInsertPast = con.prepareStatement("insert into userpastexp(userID,explanation,amount,history,revExp,recentMoney) values (?,?,?,?,?,?)");
						
						staInsertPast.setInt(1, id);
						staInsertPast.setString(2, explanationText.getText().toString());
						staInsertPast.setFloat(3, amount);
						staInsertPast.setString(4, calendarText.getDate().toString());
						staInsertPast.setString(5, "+");
						staInsertPast.setFloat(6, recentMoney);
						
						staInsertPast.executeUpdate();
						
						staUpdateUsers = con.prepareStatement("update userinfos set totalMoney=? where userID=?");
						
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
