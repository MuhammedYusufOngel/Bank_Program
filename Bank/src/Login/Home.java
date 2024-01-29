package Login;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import ForgotPassword.ForgotPass;
import HomePage.HomePage;
import Register.MainFrame;

@SuppressWarnings("serial")
public class Home extends Frame{
	
	Connection con;
	JLabel username, password, warning_user, warning_pass, forgotPass, register;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton loginButton;

	MainFrame mainFrame;
	PreparedStatement statement;
	ResultSet rs;
	
	//

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
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Home()
	{
		//Its all about user interface. 
		iniFrame("Bank Program", 800, 800);
		
		add(username = iniLabel("Username", 370, 275, 70, 25, "Times New Roman", 15));
		add(usernameField = iniText(280, 300, 240, 25, "Times New Roman", 15));
		add(warning_user = iniLabel("", 325, 325, 150, 25, "Times New Roman", 15));
		
		add(password = iniLabel("Password", 370, 350, 70, 25, "Times New Roman", 15));
		add(passwordField = iniPass(280, 375, 240, 25, "Times New Roman", 15));
		add(warning_pass = iniLabel("", 325, 400, 150, 25, "Times New Roman", 15));
		
		add(loginButton = iniButton("Login",340, 450, 120, 25, "Times New Roman", 15));
		
		add(forgotPass = iniLabel("Forgot Password", 352, 500, 100, 25, "Times New Roman", 15));
		add(register = iniLabel("Register", 375, 525, 50, 25, "Times New Roman", 15));
		
		//Technically I did a link. If user clicked the "forgotPass" my app redirected the "ForgotPassword".
		forgotPass.setForeground(Color.blue.darker());
		forgotPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		forgotPass.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new ForgotPass();
				closeFrame();
			}
		});
		
		//Technically I did links. If user clicked the "register" my app redirected the "Register".
		register.setForeground(Color.blue.darker());
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		register.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				mainFrame = new MainFrame();
				closeFrame();
			}
		});
		
		loginButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//Firstly program checks whether these fields are empty or not.
				if(usernameField.getText().toString().equals(""))
				{
					warning_user.setText("This area wouldn't empty");
					warning_user.setForeground(Color.red);
				}
				if(passwordField.getText().toString().equals(""))
				{
					warning_pass.setText("This area wouldn't empty");
					warning_pass.setForeground(Color.red);
				}
				
				//If these fields are not empty then it checks whether these fields are true or not.
				else {
					try {
						Connect();
						String username = usernameField.getText();
						String pass = passwordField.getText();

						//We connected the database. Now we select the table (users).
						statement = con.prepareStatement("select userID,name,username,password from userinfos where username = ?");
						statement.setString(1, username);
						rs = statement.executeQuery();
						
						//If we continue the program is doing actual controls. If any of field is wrong it give the error.
						if(rs.next() == true)
						{
							int id = rs.getInt(1);
							String name = rs.getString(2);
							String usernameCorrect = rs.getString(3);
							String passwordCorrect = rs.getString(4);
							
							if(username.equals(usernameCorrect) && getMd5(pass).equals(passwordCorrect))
							{
								JOptionPane.showMessageDialog(null, "Welcome " + name);
								new HomePage(id);
								closeFrame();
								
								statement.close();
							}
							else {
								JOptionPane.showMessageDialog(null, "Your username or password is incorrect.", "ERROR", JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Your username or password is incorrect.", "ERROR", JOptionPane.ERROR_MESSAGE);
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Something wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				usernameField.setText("");
				passwordField.setText("");
			}
		});
	}
	
	
}
