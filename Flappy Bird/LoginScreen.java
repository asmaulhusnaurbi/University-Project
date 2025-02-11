import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame implements ActionListener 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton eyeButton;
    private ImageIcon eyeOpenIcon;
    private ImageIcon eyeClosedIcon;
    private boolean isPasswordVisible = false;

   
    private ImageIcon backgroundImage = new ImageIcon("flappybirdbg.png");

    public LoginScreen() 
	{
        super("Login");
        this.setSize(360, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

       
        Color myColor = new Color(210, 230, 255);
        Font myFont = new Font("Cambria", Font.PLAIN, 18);

        
        eyeOpenIcon = new ImageIcon("eye_open.png");
        eyeClosedIcon = new ImageIcon("eye_closed.png");

        
        JPanel panel = new JPanel() 
		{
            @Override
            protected void paintComponent(Graphics g) 
			{
                super.paintComponent(g);
               
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        panel.setBackground(myColor);
        this.add(panel);

     
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(myFont);
        usernameLabel.setBounds(80, 115, 160, 30);
        panel.add(usernameLabel);

       
        usernameField = new JTextField();
        usernameField.setBounds(170, 115, 120, 30);
        panel.add(usernameField);
		

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(myFont);
        passwordLabel.setBounds(80, 165, 160, 30);
        panel.add(passwordLabel);

       
        passwordField = new JPasswordField();
        passwordField.setBounds(170, 165, 120, 30);
        passwordField.setEchoChar('*');
        panel.add(passwordField);

        eyeButton = new JButton(eyeClosedIcon);
        eyeButton.setBounds(295, 165, 30, 30);
        eyeButton.setBackground(myColor);
        eyeButton.setBorderPainted(false);
        eyeButton.setFocusPainted(false);
        eyeButton.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) 
			{
                togglePasswordVisibility();
            }
        });
        panel.add(eyeButton);

       
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 225, 80, 30);
        loginButton.addActionListener(this);
        panel.add(loginButton);
    }

    private void togglePasswordVisibility() 
	{
        
        if (isPasswordVisible) 
		{
            passwordField.setEchoChar('*');
            eyeButton.setIcon(eyeClosedIcon);
        } 
		else 
		{
            passwordField.setEchoChar((char) 0);
            eyeButton.setIcon(eyeOpenIcon);
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
	{
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

       
        if (username.equals("user") && password.equals("password")) 
		{
            JOptionPane.showMessageDialog(this, "Login successful! Starting game...");

           
            StartGame gameFrame = new StartGame();
            gameFrame.setVisible(true);

         
            this.setVisible(false);
        } 
		else 
		{
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
}
