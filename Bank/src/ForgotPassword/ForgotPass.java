package ForgotPassword;

import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Login.Frame;
import Login.Home;
import Register.MainFrame;

@SuppressWarnings("serial")
public class ForgotPass extends Frame{


	int counter_digit, counter_string, counter_symbol;
	Connection con;
	JLabel usernameLabel, usernameWarn1, usernameWarn2, passwordLabel, warning_user, warning_pass, forgotPass, register;
	JLabel label_warn_1, label_warn_2, label_warn_3;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton goOnButton;

	MainFrame mainFrame;
	PreparedStatement statementSelect, statementUpdate;
	ResultSet rs;
	
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
	
	public ForgotPass()
	{
		//Its all about user interface.
		Connect();
		iniFrame("Forgot Password", 800, 400);
		
		add(usernameLabel = iniLabel("Please enter the username", 280, 100, 240, 25, "Times New Roman", 15));
		add(passwordLabel = iniLabel("Please enter the new password", 280, 100, 240, 25, "Times New Roman", 15));
		add(usernameField = iniText(280, 150, 240, 25, "Times New Roman", 15));
		add(passwordField = iniPass(280, 150, 240, 25, "Times New Roman", 15));

		passwordLabel.setVisible(false);
		passwordField.setVisible(false);

		add(label_warn_1 = iniLabel("* Your password have to 5-15 characters.", 275, 200, 250, 25, "Times New Roman", 15));
		add(label_warn_2 = iniLabel("* Your password have to contains one letter, one number at least.", 200, 225, 400, 25, "Times New Roman", 15));
		add(label_warn_3 = iniLabel("If you want to symbol in your password you can use only down line (_), point(.) and straight line(-).", 100, 250, 600, 25, "Times New Roman", 15));
		add(usernameWarn1 = iniLabel("This area wouldn't empty.", 320, 185, 160, 25, "Times New Roman", 15));
		add(usernameWarn2 = iniLabel("There is no such username. Please try again.", 265, 185, 280, 25, "Times New Roman", 15));
		
		label_warn_1.setVisible(false);
		label_warn_2.setVisible(false);
		label_warn_3.setVisible(false);
		usernameWarn1.setVisible(false);
		usernameWarn2.setVisible(false);
		
		usernameWarn1.setForeground(Color.red);
		usernameWarn2.setForeground(Color.red);
		
		add(goOnButton = iniButton("Continue", 340, 300, 120, 25, "Times New Roman", 15));	
		
		goOnButton.addActionListener(new ActionListener() 
		{
			//Two processes, one class. 
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//Process 1: Enter the username
				//User enter the his/her username. If the program doesn't found his/her username it write the error message below this field.
				if(usernameField.isVisible() == true)
				{
					if(usernameField.getText().equals(""))
					{
						usernameWarn1.setVisible(true);
					}
					
					//Process 2: Enter the password
					//The program asks the user to enter his/her password.
					//Password Rules
					else 
					{
						usernameWarn1.setVisible(false);
						String user = usernameField.getText();
						
						try {
							
							statementSelect = con.prepareStatement("select username,password from users where username = ?");
							statementSelect.setString(1, user);
							
							rs = statementSelect.executeQuery();
							
							
							if(rs.next() == true)
							{
								usernameWarn2.setVisible(false);
								passwordLabel.setVisible(true);
								passwordField.setVisible(true);
								usernameLabel.setVisible(false);
								usernameField.setVisible(false);

								label_warn_1.setVisible(true);
								label_warn_2.setVisible(true);
								label_warn_3.setVisible(true);
								

								passwordField.addKeyListener(new KeyAdapter() 
								{
									
									public void keyReleased(KeyEvent e)
									{
										//Password Rule - 1: If user enters the number of characters less than 5 & more than 15 it write the error message below this field.
										if(passwordField.getText().length() >= 5 && passwordField.getText().length() <= 15)
										{
											label_warn_1.setVisible(false);
										}
										else
										{
											label_warn_1.setVisible(true);
											label_warn_1.setForeground(Color.red);
										}
										
										for(int i=0;i<passwordField.getText().length();i++)
										{
											//Password Rule - 2: If user doesn't enter to the password any of number & big letter & small letter it write the error message below this field.
											if(Character.isDigit(passwordField.getText().charAt(i)))
											{
												counter_digit++;
											}
											else if(Character.isAlphabetic(passwordField.getText().charAt(i)))
											{
												counter_string++;
											}

											//Password Rule - 3: If user enters to the password other than point, underline or straight line it write the error message below this field.
											else if(passwordField.getText().charAt(i) != '.' || passwordField.getText().charAt(i) != '_' || passwordField.getText().charAt(i) != '-')
											{
												counter_symbol++;
											}
										}

										
										if(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE)
										{
											counter_digit = 0;
											counter_string = 0;
											counter_symbol = 0;

											for(int i=0;i<passwordField.getText().length();i++)
											{
												if(Character.isDigit(passwordField.getText().charAt(i)))
												{
													counter_digit++;
												}
												else if(Character.isAlphabetic(passwordField.getText().charAt(i)))
												{
													counter_string++;
												}
												//Password Rule - 3: If user enters to the password other than point, underline or straight line it write the error message below this field.
												else if(passwordField.getText().charAt(i) != '.' || passwordField.getText().charAt(i) != '_' || passwordField.getText().charAt(i) != '-')
												{
													counter_symbol++;
												}
											}
										}
										
										if(counter_string != 0 && counter_digit != 0)
										{
											label_warn_2.setVisible(false);
										}
										else
										{
											label_warn_2.setVisible(true);
											label_warn_2.setForeground(Color.red);
										}
										
										if(counter_symbol == 0)
										{
											label_warn_3.setVisible(false);
										}
										
										else
										{
											label_warn_3.setVisible(true);
											label_warn_3.setForeground(Color.red);
										}
									}
									
								});

							}
							
							else 
							{
								usernameWarn2.setVisible(true);
							}
						} 
						catch (SQLException e1) 
						{
							usernameWarn2.setVisible(true);
						}
						
					}
				}
				
				//Process 3: Update Password
				//We connect table on the data base to update his/her password. And then update that.
				else 
				{
					if(label_warn_1.isVisible() == false && label_warn_2.isVisible() == false && label_warn_3.isVisible() == false)
					{
						try {
							String username = rs.getString(1);
							
							statementUpdate = con.prepareStatement("update users set password=? where username=?");
							
							statementUpdate.setString(1, getMd5(passwordField.getText().toString()));
							statementUpdate.setString(2, username);
							
							statementUpdate.executeUpdate();
							
							JOptionPane.showMessageDialog(null, "Your password has been changed successfully.");
							
							new Home();
							closeFrame();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
				
		});
	}
}
