package Login;

import java.awt.Font;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public abstract class Frame extends JFrame{
	
	//This is password hider. It hide password with Md5.
	public static String getMd5(String input)
    {
        try {
 
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	//This function create a new frame for our program.
	public void iniFrame(String title, int width, int height)
	{
		setLayout(null);
		setTitle(title);
		setSize(width, height);
		//Window will open in the center.
		setLocationRelativeTo(null);
		setVisible(true);
		//Program will be close when we click close button.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//This function close frame.
	public void closeFrame()
	{
		dispose();
	}
	
	//This function create a new label for our program. 
	public JLabel iniLabel(String string, int x, int y, int width, int height, String font, int fontHeight)
	{
		JLabel label = new JLabel(string, SwingConstants.CENTER);
		label.setBounds(x, y, width, height);
		label.setFont(new Font(font, Font.PLAIN, fontHeight));
		
		return label;
	}

	//This function create a new button for our program. 
	public JButton iniButton(String string, int x, int y, int width, int height, String font, int fontHeight)
	{
		JButton button = new JButton(string);
		button.setBounds(x, y, width, height);
		button.setFont(new Font(font, Font.PLAIN, fontHeight));
		
		return button;
	}

	//This function create a new text field for our program. 
	public JTextField iniText(int x, int y, int width, int height, String font, int fontHeight) 
	{
		JTextField text = new JTextField();
		text.setBounds(x, y, width, height);
		text.setFont(new Font(font, Font.PLAIN, fontHeight));
		
		return text;
	}

	//This function create a new password field for our program. 
	public JPasswordField iniPass(int x, int y, int width, int height, String font, int fontHeight) 
	{

		JPasswordField text = new JPasswordField();
		text.setBounds(x, y, width, height);
		text.setFont(new Font(font, Font.PLAIN, fontHeight));
		
		return text;
		
	}
}
