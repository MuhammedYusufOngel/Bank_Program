package Register;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Login.Frame;
import Login.Home;

@SuppressWarnings("serial")
public class MainFrame extends Frame{

	Connection con;

	JTextField nameField, surnameField, ageField, usernameField;
	JPasswordField passwordField, txt_pass_repeat; 
	String textString;
	JButton saveButton;
	JLabel label_warn_1, label_warn_2, label_warn_3, label_warn_4, label_warn_5;
	JLabel field_empty_1, field_empty_2,field_empty_3,field_empty_4,field_empty_5;
	PreparedStatement statement;
	
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
	
	public MainFrame() 
	{
		//Its all about user interface.
		Connect();
		iniFrame("Register", 800, 800);

		add(iniLabel("Name", 380, 25, 40, 25, "Times New Roman", 15));
		add(nameField = iniText(280, 50, 240, 25, "Times New Roman", 15));
		add(field_empty_1 = iniLabel("This area wouldn't empty.", 322, 75, 159, 25, "Times New Roman", 15));
		
		add(iniLabel("Surname", 370, 100, 60, 25, "Times New Roman", 15));
		add(surnameField = iniText(280, 125, 240, 25, "Times New Roman", 15));
		add(field_empty_2 = iniLabel("This area wouldn't empty.", 322, 150, 159, 25, "Times New Roman", 15));
		
		add(iniLabel("Age", 385, 175, 30, 25, "Times New Roman", 15));
		add(ageField = iniText(280, 200, 240, 25, "Times New Roman", 15));
		add(field_empty_3 = iniLabel("This area wouldn't empty.", 322, 225, 159, 25, "Times New Roman", 15));
	    
		add(iniLabel("Username",365, 250, 70, 25, "Times New Roman", 15));
		add(usernameField = iniText(280, 275, 240, 25, "Times New Roman", 15));
		add(field_empty_4 = iniLabel("This area wouldn't empty.", 322, 300, 159, 25, "Times New Roman", 15));		
		add(label_warn_5 = iniLabel("* Your username have to 5-15 characters.", 275, 300, 250, 25, "Times New Roman", 15));

		add(iniLabel("Your Password",350, 325, 100, 25, "Times New Roman", 15));
		add(passwordField = iniPass(280, 350, 240, 25, "Times New Roman", 15));
		add(field_empty_5 = iniLabel("This area wouldn't empty.", 322, 375, 159, 25, "Times New Roman", 15));
		
		add(label_warn_1 = iniLabel("* Your password have to 5-15 characters.", 275, 375, 250, 25, "Times New Roman", 15));
		add(label_warn_2 = iniLabel("* Your password have to contains one letter, one number at least.", 200, 400, 400, 25, "Times New Roman", 15));
		add(label_warn_3 = iniLabel("If you want to symbol in your password you can use only down line (_), point(.) and straight line(-).", 100, 425, 600, 25, "Times New Roman", 15));
		
		add(iniLabel("Your Password (Repeat)",325, 475, 160, 25, "Times New Roman", 15));
		add(txt_pass_repeat = iniPass(280, 500, 240, 25, "Times New Roman", 15));
		add(label_warn_4 = iniLabel("", 200, 525, 400, 25, "Times New Roman", 15));
		label_warn_4.setForeground(Color.RED);
		
		field_empty_1.setForeground(Color.red);
		field_empty_2.setForeground(Color.red);
		field_empty_3.setForeground(Color.red);
		field_empty_4.setForeground(Color.red);
		field_empty_5.setForeground(Color.red);

		field_empty_1.setVisible(false);
		field_empty_2.setVisible(false);
		field_empty_3.setVisible(false);
		field_empty_4.setVisible(false);
		field_empty_5.setVisible(false);
		label_warn_1.setVisible(false);
		label_warn_2.setVisible(false);
		label_warn_3.setVisible(false);
		
		label_warn_5.setVisible(false);
		
		add(saveButton = iniButton("Register",340, 575, 120, 25, "Times New Roman", 15));

		//If user enters the character other than numbers it write the error message below this field.
		ageField.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e) 
			{	            
	            if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) 
	            {
	            	ageField.setEditable(true);
	            } 
	            else 
	            {
	            	ageField.setEditable(false);
	            }
	         }
	      });
		
		//If user enters the number of characters less than 5 & more than 15 it write the error message below this field.
		usernameField.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e)
			{
				//User name Warning
				if(usernameField.getText().length() >= 5 && usernameField.getText().length() <= 15)
				{
					label_warn_5.setVisible(false);
				}
				else
				{	
					label_warn_5.setVisible(true);
					field_empty_4.setVisible(false);
					label_warn_5.setForeground(Color.red);
				}
				
			}
		});
		
		//Password Rules
		passwordField.addKeyListener(new KeyAdapter()
		{
			int counter_digit, counter_string, counter_symbol;
			@SuppressWarnings("deprecation")
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
					field_empty_5.setVisible(false);
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
		//If user doesn't enter the same password it write the error message below this field.
		txt_pass_repeat.addKeyListener(new KeyAdapter()
		{
			@SuppressWarnings("deprecation")
			public void keyReleased(KeyEvent e)
			{
				if(passwordField.getText().toString().equals(txt_pass_repeat.getText().toString()))
				{
					label_warn_4.setText("");
				}
				else
				{
					label_warn_4.setText("* The password you typed does not match the password above.");
				}
			}
			
		});
		
		saveButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			//ERROR MESSAGES
			public void actionPerformed(ActionEvent e) {
				if(nameField.getText().equals("") || surnameField.getText().equals("") || ageField.getText().equals("") || usernameField.getText().equals("") || passwordField.getText().equals(""))
				{
					if(nameField.getText().equals(""))
					{
						field_empty_1.setVisible(true);
					}
					else {
						field_empty_1.setVisible(false);
					}
					
					if(surnameField.getText().equals(""))
					{
						field_empty_2.setVisible(true);
					}
					else {
						field_empty_2.setVisible(false);
					}
					
					if(ageField.getText().equals(""))
					{
						field_empty_3.setVisible(true);
					}
					else {
						field_empty_3.setVisible(false);
					}

					if(usernameField.getText().equals(""))
					{
						field_empty_4.setVisible(true);
					}
					else {
						field_empty_4.setVisible(false);
					}

					if(passwordField.getText().equals(""))
					{
						field_empty_5.setVisible(true);
					}
					
					else {
						field_empty_5.setVisible(false);
					}
				}
				//If all warnings are gone the program write to table.
				else if(label_warn_1.isVisible() == false || label_warn_2.isVisible() == false || label_warn_3.isVisible() == false || label_warn_4.isVisible() == false || label_warn_5.isVisible() == false)
				{
					try {
						statement = con.prepareStatement("insert into users(name,surname,age,username,password,totalmoney)values(?,?,?,?,?,?)");
						
						statement.setString(1, nameField.getText());
						statement.setString(2, surnameField.getText());
						statement.setInt(3, Integer.parseInt(ageField.getText()));
						statement.setString(4, usernameField.getText());
						statement.setString(5, getMd5(passwordField.getText()));
						statement.setFloat(6, 0);
						
						statement.executeUpdate();
						JOptionPane.showMessageDialog(null, "Your account created successfully. You will be redirected to the home page.");
						new Home();
						closeFrame();
					}
					catch(SQLException e1) {
						e1.printStackTrace();					
					}
				}				
			}
		});
	}	

}
